package com.swust.emporium.dto;

import com.swust.emporium.enums.ProductStateEnum;
import com.swust.emporium.pojo.Product;

import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 产品操作 dto层
 */
public class ProductExecution {
    private int state;
    private String stateInfo;

    private int count;
    private Product product;
    private List<Product> productList;

    public ProductExecution(){
    }

    public ProductExecution(ProductStateEnum productStateEnum){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
    }

    public ProductExecution(ProductStateEnum productStateEnum, Product product){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }

    public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
