package com.xzh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 向振华
 * @date 2019/04/19 15:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    /**
     * 商品名
     */
    private String title;

    /**
     * 作者
     */
    private String author;
}
