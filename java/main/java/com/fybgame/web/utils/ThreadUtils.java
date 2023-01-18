package com.fybgame.web.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:fyb
 * @Date: 2022/11/4 21:35
 * @Version:1.0
 */
public class ThreadUtils {
    public static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1024));
    public static void isComplete(){
        while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
        }
    }
}
