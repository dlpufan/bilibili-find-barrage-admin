package com.fybgame.web.controller;

import com.fybgame.web.entity.resultData.ResultData;
import com.fybgame.web.service.BilibiliService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * @Author:fyb
 * @Date: 2021/3/5 3:14
 * @Version:1.0
 */
@RestController
@RequestMapping("/bilibili")
public class BilibiliController {
    @Resource
    BilibiliService bilibiliService;
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ResultData matchBarrageUser(Integer cid, String match,Integer av,Integer p,HttpServletRequest request,Boolean isUseHistory){
        return bilibiliService.matchBarrageUser(cid,match,av,p,request,isUseHistory);
    }
}
