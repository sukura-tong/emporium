package com.swust.emporium.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 解析HttpServlet参数工具类
 */
public class HttpServletRequestUtils {
    /**
     * 解析整形
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key){
        try {
            String parameter = request.getParameter(key);
            int res = Integer.decode(parameter);
            return res;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 解析长整型
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key){
        try {
            String parameter = request.getParameter(key);
            long res = Long.valueOf(parameter);
            return res;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 解析double类型
     * @param request
     * @param key
     * @return
     */
    public static Double getDouble(HttpServletRequest request, String key){
        try {
            String parameter = request.getParameter(key);
            return Double.valueOf(parameter);
        }catch (Exception e){
            return -1d;
        }
    }

    /**
     * 解析boolean类型
     * @param request
     * @param key
     * @return
     */
    public static Boolean getBoolean(HttpServletRequest request, String key){
        try {
            String parameter = request.getParameter(key);
            return Boolean.valueOf(parameter);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 解析字符串类型
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request,String key){
        try {
            String parameter = request.getParameter(key);
            if (parameter != null){
                parameter = parameter.trim();
            }
            if ("".equals(parameter)){
                parameter = null;
            }
            return parameter;
        }catch (Exception e){
            return null;
        }
    }
}
