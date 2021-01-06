package com.swust.emporium.exceptions;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 商品信息操作异常处理类
 */
public class ProductOperationException extends RuntimeException{

    private static final long serialVersionUID = 3853543166177527881L;

    public ProductOperationException(String message) {
        super(message);
    }
}
