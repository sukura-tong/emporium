package com.swust.emporium.util;

import com.swust.emporium.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 图片处理工具类
 */
public class ImageUtils {


//    private static String waterMarkPath = "F:\\emporium\\src\\main\\resources\\加藤惠.jpg";

    private static String waterMarkPath = "/root/imgdatas/upload/images/item/watermark/mw.jpg";
    private static Logger logger = LoggerFactory.getLogger(ImageIO.class);
//    private static String basePath = Thread.currentThread()
//                                    .getContextClassLoader()
//                                    .getResource("")
//                                    .getPath() + "/watermark.jpg";


    /***
     * 判断storePath是文件的路径还是目录的路径
     *  目录路径 删除该目录下面的所有文件
     *  文件路径 删除该文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
            File fileOrPath = new File(storePath);
            if (fileOrPath.exists()){
                if (fileOrPath.isDirectory()){
                    File[] files = fileOrPath.listFiles();
                    for (File file : files){
                        file.delete();
                    }
                }
            }
            fileOrPath.delete();
    }

    /**
     * 添加水印
     * @param imageHolder
     * @param targetAddress
     * @return
     */
    public static String generateThumbnail(ImageHolder imageHolder, String targetAddress){

        String fileName = imageHolder.getImageName();
        InputStream image = imageHolder.getImage();

        String realFileName = getRandomFileName();
        String extensionName = getFileExtension(fileName);
        makeDirPath(targetAddress);// 创建文件路径

        String realtiveAddress = targetAddress + realFileName + extensionName;
        File file = new File(realtiveAddress);

        try {
            Thumbnails.of(image)
                    .size(200, 200)// 输出图片大小 长、宽 像素
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterMarkPath)), 0.25f) // 添加水印 水印位置 文件路径 透明度
                    .outputQuality(0.8f)// 压缩大小
                    .toFile(file);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error => "+ e.toString());
        }
        return realtiveAddress;
    }

    /**
     * 创建目标路径所涉及到目录
     *
     * 如果目录为/home/hadoop/item/image/xx.jpg
     * 那么home hadoop item 这三个文件夹都必须要创建出来
     * @param targetAddress 目标文件所属的相对路径
     */
    public static void makeDirPath(String targetAddress) {
        String realFileParentPath = targetAddress;
        // 如果文件路径不存在 则递归创建文件路径
        File file = new File(realFileParentPath);

        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取文件的扩展名
     * @param name
     * @return
     */
    public static String getFileExtension(String name) {
        String extension = name.substring(name.lastIndexOf("."));
        return extension;

    }

    /**
     * 生成图片文件的随机名称
     * 当前的年月日小时分钟秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        Date date = new Date();
        String time = DateUtils.getDateToString(date);
        String nums = getRandomNums();
        String name = time + nums;
        return name;
    }

    /**
     * 生成随机数
     * @return
     */
    public static String getRandomNums(){
        Random random = new Random();
        int num = random.nextInt(89999) + 10000;
        return String.valueOf(num);
    }

    /***
     * 处理详情图片水印方法
     * @param imageHolder
     * @param basePath
     * @return
     */
    public static String generateNormalImg(ImageHolder imageHolder, String basePath){

        InputStream image = imageHolder.getImage();
        String imageName = imageHolder.getImageName();
        String randomFileName = getRandomFileName();
        String extensionName = getFileExtension(imageName);

        makeDirPath(basePath);

        String realtiveAddress = basePath + randomFileName + extensionName;
        File file = new File(realtiveAddress);

        try {
            Thumbnails.of(image)
                    .size(320, 640)// 输出图片大小 长、宽 像素
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterMarkPath)), 0.25f) // 添加水印 水印位置 文件路径 透明度
                    .outputQuality(0.9f)// 压缩大小
                    .toFile(file);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("error => "+ e.toString());
        }
        return realtiveAddress;
    }
}
