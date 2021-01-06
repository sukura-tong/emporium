package com.swust.emporium.exceptions;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品类别操作异常处理类
 */
public class ProductCategoryOpertionException extends RuntimeException{

    private static final long serialVersionUID = 7523014754280747904L;

    public ProductCategoryOpertionException(String message) {
        super(message);
    }
}
