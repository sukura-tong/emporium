package com.swust.emporium.web.superadmin;

import com.swust.emporium.pojo.Area;
import com.swust.emporium.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 超级管理员区域控制controller
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {

    // 启用日志
    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    // 设置访问路由
    @RequestMapping(value =  "/listarea",method = RequestMethod.GET)
    // 将返回对象自动转化为Json对象返回到前端
    @ResponseBody
    private Map<String, Object> listArea(){

        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> result = new HashMap<>();
        List<Area> areas = new ArrayList<>();

        try {
            areas = areaService.getAreaList();
            // 返回集合
            result.put("rows",areas);
            // 返回总数
            result.put("total",areas.size());
        }catch (Exception e){
            e.printStackTrace();
            // 追加错误信息
            result.put("success",false);
            result.put("errorMessage",e.toString());
            logger.error("error ==>" + e.toString());
        }



        long overTime = System.currentTimeMillis();
        // 占位符
        logger.debug("costTime:[{}ms]",overTime - startTime);
        logger.info("===end===");
        return result;
    }
}
