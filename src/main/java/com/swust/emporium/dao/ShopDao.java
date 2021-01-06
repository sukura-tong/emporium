package com.swust.emporium.dao;

import com.swust.emporium.pojo.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺管理数据库层
 */
public interface ShopDao {

    /**
     * 返回查询条件的总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     *
     * 分页查询店铺
     *    输入条件
     *       店铺名(模糊查询)、店铺状态、店铺类别、区域Id、owner
     *
     * @param shopCondition
     * @param rowIndex 第几行
     * @param pageSize 取几行
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /***
     * 新增店铺
     * @param shop
     * @return
     */
    int insertSHop(Shop shop);

    /***
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 店铺信息查询
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
