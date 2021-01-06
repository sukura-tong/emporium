package com.swust.emporium.service;

import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ShopExecution;
import com.swust.emporium.exceptions.ShopOperationException;
import com.swust.emporium.pojo.Shop;


/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺业务逻辑层
 */
public interface ShopService {

    /**
     * 分页查询特定店铺信息
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /***
     * 添加店铺
     * @param shop
     * @param imageHolder
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder);

    /***
     * 根据shopId获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId) throws ShopOperationException;

    /***
     * 根据shopId更新店铺信息
     * @param shop
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;
}
