package com.swust.emporium.util;

import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 路径工具类
 */
public class PathUtils {

    // 获取系统分隔符
    private static String seperator = System.getProperty("file.separator");
    /***
     * 根据执行环境不同获取不同的根路径
     * @return
     */
    public static String getImgBasePath(){
        String osName = System.getProperty("os.name");
        String basePath = "";
        if (osName.toLowerCase().startsWith("win")){
            basePath = "F:/project/image";
        }else {
            basePath = "/root/imgdatas/upload/images/item/";
        }

        String path = basePath.replace("/", seperator);
        return path;
    }

    /***
     * 根据不同的商品业务需求获取图片路径
     * @param shopId
     * @return
     */
    public static String getShopImgPath(long shopId){
        String imagePath = "/root/imgdatas/project/item/shop" + shopId + '/';
        String replace = imagePath.replace("/", seperator);
        return replace;
    }

    /***
     * 根据不同的商品业务需求获取详情图片路径
     * @param shopId
     * @return
     */
    public static String getProductImgPath(long shopId){
        String imagePath = "/root/imgdatas/project/item/shop/" + shopId + '/';
        String replace = imagePath.replace("/", seperator);
        return replace;
    }

    /***
     * 根据不同的商品业务需求获取缩略图片路径
     * @param shopId
     * @return
     */
    public static String getProductDescribeImgPath(long shopId){
        String imagePath = "/root/imgdatas/project/desc/shop" + shopId + '/';
        String replace = imagePath.replace("/", seperator);
        return replace;
    }

    /**
     * 根据传入路径删除相关图片
     * @param paths
     * @return
     */
    public static int deleteInpuPath(List<String> paths){

        for (String path : paths){
            File file = new File(path);
            file.delete();
        }
        return 1;
    }

    public static String replaceSeperator(String path){
        String replace = path.replace("/", seperator);
        return replace;
    }

    public static String replaceMaoHao(String path){
       if (path != null){
           if (path.contains(":")){
               String[] split = path.split(":");
               return split[1];
           }else {
               return path;
           }
       }else {
           return null;
       }
    }
}
