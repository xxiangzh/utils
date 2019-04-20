package com.xzh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Sign
    private String author;

    /**
     * 合集
     */
    private List<HeJi> heJis;
}
