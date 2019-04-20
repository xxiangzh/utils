package com.xzh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 向振华
 * @date 2019/04/20 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeJi {

    @Sign
    private String collect;

    @Sign
    private Integer count;
}
