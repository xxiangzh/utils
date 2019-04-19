package com.xzh.vo;

/**
 * @author 向振华
 * @date 2019/04/19 15:22
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
