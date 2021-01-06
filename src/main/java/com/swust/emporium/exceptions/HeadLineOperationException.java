package com.swust.emporium.exceptions;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 头条信息操作异常处理类
 */
public class HeadLineOperationException extends RuntimeException{

    private static final long serialVersionUID = 8428395273790516507L;

    public HeadLineOperationException(String message) {
        super(message);
    }
}
