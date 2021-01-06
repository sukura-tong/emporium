package com.swust.emporium.util;

import com.swust.emporium.enums.ShopStateEnum;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 基本方法测试类
 */
public class FunctionTest {
    @Test
    @Ignore
    public void fun(){
        int state = 1;

        for (ShopStateEnum stateEnum : ShopStateEnum.values()){
            System.out.println(stateEnum.getStateInfo());
        }
    }

    @Test
    @Ignore
    public void testDeletePicture(){
        String path = "F:\\emporium\\img\\1.jpg";
        List<String> list = new ArrayList<>();
        list.add(path);

        int effectNum = PathUtils.deleteInpuPath(list);
        Assert.assertEquals(1,effectNum);
    }
}
