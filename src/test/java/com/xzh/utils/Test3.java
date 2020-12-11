package com.xzh.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 向振华
 * @date 2020/10/15 16:28
 */
@Slf4j
public class Test3 {

    public static void main(String[] args) {
        ExecutorService executorService = ThreadPoolFactory.createFixedThreadPool("xzh");
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Future<String> submit = executorService.submit(() -> test(finalI));
            try {
                submit.get();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("end");
    }

    private static String test(int x) {
        int random = new Random().nextInt(9);
        if (random > 5) {
            throw new RuntimeException("错误：" + x);
        } else {
            log.info("success" + x);
            return "成功" + x;
        }
    }
}