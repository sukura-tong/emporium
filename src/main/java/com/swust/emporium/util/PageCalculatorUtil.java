package com.swust.emporium.util;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 前端使用的页数
 * 数据库查询使用的行数
 * 该工具类实现前端页数与后端数据库行数之间的数据转换功能
 */
public class PageCalculatorUtil {
    /***
     * 根据第几页获取特定的数据
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex, int pageSize){
        if (pageIndex > 0){
            int res = (pageIndex - 1) * pageSize;
            return res;
        }else {
            return 0;
        }
    }
}
