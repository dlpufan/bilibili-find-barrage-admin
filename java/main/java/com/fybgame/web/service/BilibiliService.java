package com.fybgame.web.service;

import com.fybgame.web.entity.resultData.ResultData;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:fyb
 * @Date: 2021/3/5 1:50
 * @Version:1.0
 */
public interface BilibiliService {
    public ResultData matchBarrageUser(Integer cid, String match, Integer av,Integer p,HttpServletRequest request,Boolean isUseHistory);
}
