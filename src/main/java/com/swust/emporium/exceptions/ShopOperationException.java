package com.swust.emporium.exceptions;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺操作异常
 */
public class ShopOperationException extends RuntimeException{

    //生成随机序列化ID
    private static final long serialVersionUID = -2293712388036514485L;

    public ShopOperationException(String msg){
        super(msg);
    }

}
