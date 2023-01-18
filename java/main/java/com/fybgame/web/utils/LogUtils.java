package com.fybgame.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:fyb
 * @Date: 2022/11/4 22:16
 * @Version:1.0
 */
public class LogUtils {
    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);
    public static void info(String s){
        logger.info(s);
    }
}
