package com.swust.emporium.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    // 需要加密字段的数组
    private String[] encrptPropNames = {"jdbc.username", "jdbc.password"};

    /**
     * 获得真实的值
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryProp(propertyName)){
            // 对已经加密的字段进行解密
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }else {
            return propertyValue;
        }
    }

    /**
     * 判断是否加密字段
     * @param propertyName
     * @return
     */
    private boolean isEncryProp(String propertyName) {
        // 若等于需要加密的field，则进行加密
        for (String elem : encrptPropNames){
            if (elem.equals(propertyName)){
                return true;
            }
        }
        return false;
    }
}
