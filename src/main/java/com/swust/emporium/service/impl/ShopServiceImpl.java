package com.swust.emporium.service.impl;

import com.swust.emporium.dao.ShopDao;
import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ShopExecution;
import com.swust.emporium.enums.ShopStateEnum;
import com.swust.emporium.exceptions.ShopOperationException;
import com.swust.emporium.pojo.Shop;
import com.swust.emporium.service.ShopService;
import com.swust.emporium.util.ImageUtils;
import com.swust.emporium.util.PageCalculatorUtil;
import com.swust.emporium.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    /***
     * 通过传入条件分页查询相关数据
     * @param shopCondition
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        if (shopCondition == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        int rowIndex = PageCalculatorUtil.calculateRowIndex(pageIndex, pageSize);

        List<Shop> queryShopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);

        // 处理图片信息
        for (Shop elemShop : queryShopList){
            String shopImg = elemShop.getShopImg();
            String replaceMaoHao = PathUtils.replaceMaoHao(shopImg);
            elemShop.setShopImg(replaceMaoHao);
        }

        int count = shopDao.queryShopCount(shopCondition);

        ShopExecution se = new ShopExecution();

        if (queryShopList != null){
            se.setShopList(queryShopList);
            se.setCount(count);
            se.setState(ShopStateEnum.SUCCESS.getState());
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    // 事物标签
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder imageHolder) {

        // 空值判断
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        //事物
        try {
            //初始化参数 0代表未上架
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            // add shop
            int effectNum = shopDao.insertSHop(shop);
            // 插入结果判断
            if (effectNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                // add success
                if (imageHolder.getImage() != null){
                   try {
                       //存储图片 并设置图片路径
                       addShopImg(shop, imageHolder);
                   }catch (Exception e){
                       throw new ShopOperationException("Image Error ==> " + e.getMessage());
                   }
                   // 更新店铺的图片地址
                     effectNum = shopDao.updateShop(shop);
                   if (effectNum <= 0){
                       throw new ShopOperationException("店铺图片地址更新失败");
                   }
                }
            }

        }catch (Exception e){
             throw new ShopOperationException("Shop Error ==> " + e.getMessage());
        }
        // 当存储成功时 将数据执行结果返回 状态为需要管理员审核
        ShopExecution execution = new ShopExecution(ShopStateEnum.CHECK, shop);
        return execution;
    }

    @Override
    public Shop getByShopId(long shopId) throws ShopOperationException {
        Shop shop = shopDao.queryByShopId(shopId);
        String shopImg = shop.getShopImg();
        String replaceMaoHao = PathUtils.replaceMaoHao(shopImg);
        shop.setShopImg(replaceMaoHao);

        return shop;
    }

    @Override
    @Transactional // 删改查需要追加事物标签
    public ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException {

        if (shop == null || shop.getShopId() == null){
           return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        String fileName = imageHolder.getImageName();
        InputStream shopImgInputStream = imageHolder.getImage();
        try {
            // 1 判断是否需要处理图片
            if (shopImgInputStream != null && fileName != null && !"".equals(fileName)){
                Shop query = shopDao.queryByShopId(shop.getShopId());
                // 如果图片信息不为空需要更新原始路径
                if (query.getShopImg() != null){
                    ImageUtils.deleteFileOrPath(query.getShopImg());
                }
                addShopImg(shop, imageHolder);
            }
            // 2 更新店铺信息
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.updateShop(shop);
            if (effectNum <= 0){
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            }else {
                // 当存储成功时 将数据执行结果返回 状态为需要管理员审核
                Shop newShop = shopDao.queryByShopId(shop.getShopId());
                ShopExecution execution = new ShopExecution(ShopStateEnum.SUCCESS, newShop);
                return execution;
            }
        }catch (Exception e){
            throw new ShopOperationException("error ==> 店铺信息更新失败");
        }
    }


    /**
     * 处理图像
     * @param shop
     * @param imageHolder
     */
    private void addShopImg(Shop shop, ImageHolder imageHolder) {
        // 获取shop的图片目录路径
        String imgBasePath = PathUtils.getImgBasePath();
        String destPath = imgBasePath + "shop" + shop.getShopId() + '/';

//        String destPath = PathUtils.getShopImgPath(shop.getShopId());
        // 添加水印 并返回图像存储路径
        String shopImgAddr = ImageUtils.generateThumbnail(imageHolder, destPath);
        shop.setShopImg(shopImgAddr);
    }
}
