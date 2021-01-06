package com.swust.emporium.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swust.emporium.dto.ImageHolder;
import com.swust.emporium.dto.ShopCategoryExecution;
import com.swust.emporium.dto.ShopExecution;
import com.swust.emporium.enums.ShopStateEnum;
import com.swust.emporium.pojo.*;
import com.swust.emporium.service.AreaService;
import com.swust.emporium.service.ShopCategoryService;
import com.swust.emporium.service.ShopService;
import com.swust.emporium.util.CodeUtils;
import com.swust.emporium.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 店铺管理controller层
 * 访问业务逻辑层
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {


    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /***
     * 获取初始信息
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();


        List<ShopCategory> shopCategoryLists = new ArrayList<>();
        List<Area> areaLists = new ArrayList<>();

        try {
            ShopCategoryExecution sce = shopCategoryService.getShopCategoryList(new ShopCategory());
            shopCategoryLists = sce.getShopCategoryList();
            areaLists = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryLists);
            modelMap.put("areaList", areaLists);
            modelMap.put("success", true);

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 注册店铺
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody //将返回的map自动转换为Json格式
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();

        // 校验验证码是否一致
        if (CodeUtils.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "校验码不能为空或输入错误...");
            return modelMap;
        }

        // 接收并转换响应参数 店铺信息 图片信息
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
             shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        // 接收图片信息
        CommonsMultipartFile shopImg = null;
        // 文件上传解析器
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        // 是否存在上传的文件流信息
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        // 注册店铺
        if (shop != null && shopImg != null){
//            PersonInfo owner = new PersonInfo();
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user"); // 从session域中获取店铺信息
            //  Session TODO ...
//            owner.setUserId(1L);
            shop.setOwner(owner);

            String fileName = shopImg.getOriginalFilename();
            ShopExecution se = null;

            try {
                ImageHolder imageHolder = new ImageHolder();
                imageHolder.setImage(shopImg.getInputStream());
                imageHolder.setImageName(fileName);

                se = shopService.addShop(shop, imageHolder);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

            // 判断店铺是否创建成功 同时将店铺信息更新到session中
            if (se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
                // 将店铺保存到session域内
                    // unchecked 去除警告信息
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                //是否是第一次创建店铺
                if (shopList.size() == 0 || shopList == null){
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);
                return modelMap;
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg", se.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }

//    /**
//     * 将InputStream转换成file对象
//     * 该方法已废弃
//     * @param inputStream
//     * @param file
//     */
//    private static void InputStramToFile(InputStream inputStream, File file){
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            // 读取数据的buffer缓冲区
//            byte[] buffer = new byte[1024];
//            // 从输入流读取一些字节数,并将它们存储到缓冲区buffer中。
//            while ((bytesRead = inputStream.read(buffer)) != -1){
//                    // 需要理解
//                os.write(buffer,0,bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用InputStramToFile方法流解析异常 ==> " + e.getMessage());
//        } finally {
//            if (os != null){
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    throw new RuntimeException("调用InputStramToFile方法关闭输出流异常 ==> " + e.getMessage());
//                }
//            }
//            if (inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException("调用InputStramToFile方法关闭输入流异常 ==> " + e.getMessage());
//                }
//            }
//        }
//    }

    /**
     * 基于传入的shopId查询店铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Long id = currentShop.getShopId();
        System.out.println(id);

        long shopId = HttpServletRequestUtils.getLong(request, "shopId");
        if (shopId > -1){
           try {
               Shop shop = shopService.getByShopId(shopId);
               List<Area> list = areaService.getAreaList();

               modelMap.put("shop", shop);
               modelMap.put("areaList", list);
               modelMap.put("success", true);
               return modelMap;
           }catch (Exception e){
               modelMap.put("success", false);
               modelMap.put("errMsg", e.getMessage());
               return modelMap;
           }



        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","店铺信息查询错误");
            return modelMap;
        }
    }

    /**
     * 更新店铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        // 校验码判断
        if (CodeUtils.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }
        // 格式化shop对象
        ObjectMapper mapper = new ObjectMapper();
        // 获取基于Ajax请求传递过来的店铺信息参数
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //判断是否具有图片上传信息流
        HttpServletRequest httpServletRequest;
        CommonsMultipartResolver commonsMultipartResolver = new
                CommonsMultipartResolver(request.getSession().getServletContext());
        CommonsMultipartFile shopImg = null;
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
             shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "图片信息解析错误");
            return modelMap;
        }

        ShopExecution shopExecution = null;
        // 更新信息
        if (shop != null && shop.getShopId() != null){
            if (shopImg != null){
                InputStream shopImgInputStream = null;
                try {
                    shopImgInputStream = shopImg.getInputStream();
                    String fileName = shopImg.getOriginalFilename();
                    ImageHolder imageHolder = new ImageHolder();
                    imageHolder.setImageName(fileName);
                    imageHolder.setImage(shopImgInputStream);
                    shopExecution = shopService.modifyShop(shop, imageHolder);

                } catch (IOException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                    return modelMap;
                }
            }else {
                // 上传图片信息为空 选择只更新其余信息
               try {
                   shopExecution = shopService.modifyShop(shop, null) ;
               }catch (Exception e){
                   modelMap.put("success", false);
                   modelMap.put("errMsg", e.getMessage());
                   return modelMap;
               }
            }
        }
        // 判读店铺是否更新成功
        if (shopExecution != null){
            int state = shopExecution.getState();
            String stateInfo = shopExecution.getStateInfo();
            if (state == ShopStateEnum.SUCCESS.getState()){
                modelMap.put("success", true);
                return modelMap;
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg","状态码返回异常");
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","店铺更新异常");
            return modelMap;
        }
    }

    /***
     * 店铺信息分页查询模块功能开发
     * controller
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        // TODO...这里有bug userLogin和personInfo的Id必须一致
        // session中获取登陆信息
        UserLogin userLogin = (UserLogin) request.getSession().getAttribute("userLogin");
        int uid = userLogin.getUid();
        PersonInfo user = new PersonInfo();
        user.setUserId(new Long(uid));
        request.getSession().setAttribute("user", user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        Long userId = user.getUserId();

        List<Shop> shopList = new ArrayList<>();

        ShopExecution se = null;
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("success", true);
            modelMap.put("shopList", se.getShopList());
            // 将shopList保存到session中
            request.getSession().setAttribute("shopList",se.getShopList());
            modelMap.put("user",user);
            return modelMap;
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
    }

    /***
     * 获取店铺管理相关信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtils.getLong(request, "shopId");
        if (shopId <= 0){
            // 尝试从session中获取店铺Id信息
            Object currenShopObj = request.getSession().getAttribute("currentShop");
            if (currenShopObj == null){
                // 重定向回店铺页面
                modelMap.put("redirect", true);
                // 访问Html页面
                modelMap.put("url", "/emporium/shopadmin/shoplist");
            }else {
                Shop currenShop = (Shop) currenShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currenShop.getShopId());
            }
        }else {
            Shop current = new Shop();
            current.setShopId(shopId);
            request.getSession().setAttribute("currentShop", current);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
}
