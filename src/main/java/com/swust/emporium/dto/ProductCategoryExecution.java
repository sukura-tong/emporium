package com.swust.emporium.dto;

import com.swust.emporium.enums.ProductCategoryStateEnum;
import com.swust.emporium.pojo.ProductCategory;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别操作状态信息表示
 */
public class ProductCategoryExecution {
    // 操作码
    private int state;
    // 操作信息描述
    private String stateInfo;
    // 分页查询的条件总数
    private int count;
    // 查询 店铺
    private List<ProductCategory> productCategoryList;
    // 增删改 操作的店铺
    private ProductCategory productCategory;


    public ProductCategoryExecution() {
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum,
                                    ProductCategory productCategory){
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategory = productCategory;
    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum,
                                    List<ProductCategory> productCategoryList){
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public int getCount() {
        return count;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }
}
