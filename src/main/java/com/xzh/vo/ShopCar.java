package com.xzh.vo;

/**
 * @author 向振华
 * @date 2019/04/19 15:22
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 向振华
 * @date 2018/11/14 16:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCar {

    /**
     * 商品名
     */
    @Sign
    private String name;

    /**
     * 单价
     */
    private Double price;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 书
     */
    private List<Book> books;

    public static  ShopCar getInstance() {
        ShopCar shopCar = new ShopCar("橘子", 5.5, 30, null);

        Book book1 = new Book("西游记", "吴承恩", null);
        HeJi heJi1 = new HeJi("论语", 10);
        HeJi heJi2 = new HeJi("中庸", 20);
        List<HeJi> heJis1 = new ArrayList<>();
        heJis1.add(heJi1);
        heJis1.add(heJi2);
        book1.setHeJis(heJis1);

        Book book2 = new Book("三国演义", "罗贯中", null);
        HeJi heJi3 = new HeJi("左传", 30);
        HeJi heJi4 = new HeJi("春秋", 40);
        List<HeJi> heJis2 = new ArrayList<>();
        heJis2.add(heJi3);
        heJis2.add(heJi4);
        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        book2.setHeJis(heJis2);

        shopCar.setBooks(books);
        return shopCar;
    }
}
