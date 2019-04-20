package com.xzh.vo;

import com.alibaba.fastjson.JSON;
import com.xzh.utils.ClassUtils;

/**
 * @author 向振华
 * @date 2019/04/19 15:45
 */
public class Test {

    public static void main(String[] args) {
        ShopCar shopCar = ShopCar.getInstance();
        System.out.println(JSON.toJSONString(shopCar));

        ClassUtils.decode(shopCar);
        System.out.println(JSON.toJSONString(shopCar));
    }
}
