package com.xzh.vo;

import com.alibaba.fastjson.JSON;
import com.xzh.utils.ClassUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 向振华
 * @date 2019/04/19 15:45
 */
public class Test {

    public static void main(String[] args) {
        ShopCar shopCar = new ShopCar("橘子", 5.5, 30, null);
        Book book1 = new Book("西游记", "吴承恩");
        Book book2 = new Book("三国演义", "罗贯中");
        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        shopCar.setBooks(books);
        ClassUtils classUtils = new ClassUtils();
        Map map = classUtils.toMap(shopCar);
        System.out.println(JSON.toJSONString(map));
        classUtils.setValue(shopCar,"price", 988D);
        System.out.println(JSON.toJSONString(shopCar));
    }
}
