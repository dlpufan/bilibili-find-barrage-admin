package com.fybgame.web.service.impl;

import com.fybgame.web.cache.BilibiliCache;
import com.fybgame.web.entity.Barrage;
import com.fybgame.web.entity.BarrageSender;
import com.fybgame.web.entity.BilibiliFinder;
import com.fybgame.web.entity.User;
import com.fybgame.web.entity.resultData.ResultData;
import com.fybgame.web.mapper.bilibili.SenderMapper;
import com.fybgame.web.service.BilibiliService;
import com.fybgame.web.servicecode.HttpCode;
import com.fybgame.web.utils.IPUtils;
import com.fybgame.web.utils.MyTime;
import com.fybgame.web.utils.bilibili.BarrageUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:fyb
 * @Date: 2021/3/5 1:53
 * @Version:1.0
 */
@Service
public class BilibiliServiceImpl implements BilibiliService {
    @Resource
    BilibiliCache bilibiliCache;
    @Resource
    SenderMapper senderMapper;
    @Override
    public ResultData matchBarrageUser(Integer cid, String match,Integer av,Integer p, HttpServletRequest request) {
        if(cid == null||match == null){
            return new ResultData(HttpCode.ERROR,"缺少参数");
        }
        if("".equals(match)){
            return new ResultData(HttpCode.ERROR,"暂不支持空match");
        }
        StopWatch watch = new StopWatch();
        watch.start();
        List<BarrageSender> barrageSenders = new ArrayList<>();
        List<Barrage> barrages = BarrageUtils.getBarrages(cid);
        for (Barrage barrage : barrages) {
            if(barrage.isMatching(match)){
                //List<Integer> list = new ArrayList<>();
                BigInteger tenRadix = new BigInteger(barrage.getUid(),16);
                List<User> users = bilibiliCache.getUser(tenRadix.toString(), String.valueOf(barrage.getUid().charAt(0)));

//                    list.add(Integer.parseInt(user.getId()));
//                    User user1 = bilibiliCache.getUser(tenRadix.toString(), "speical");
//                    if(user1.getId()!=null){
//                        list.add(Integer.parseInt(user1.getId()));
//                        User user2 = bilibiliCache.getUser(tenRadix.toString(), "speical2");
//                        if(user2.getId()!=null){
//                            list.add(Integer.parseInt(user2.getId()));
//                            User user3 = bilibiliCache.getUser(tenRadix.toString(),"Finally");
//                            if(user3.getId()!=null){
//                                list.add(Integer.parseInt(user3.getId()));
//                            }
//                        }
//                    }

                BarrageSender barrageSender = new BarrageSender(barrage,users);
                barrageSenders.add(barrageSender);
            }
        }

        Map<String,Object> map = new HashMap<>(2);
        map.put("senderInfo",barrageSenders);
        map.put("time",watch.getTime());
        BilibiliFinder bilibiliFinder = new BilibiliFinder();
        bilibiliFinder.setAv(av);
        bilibiliFinder.setP(p);
        bilibiliFinder.setText(match);
        bilibiliFinder.setTime(MyTime.getCurrentTime());
        bilibiliFinder.setIp(IPUtils.getIpAddr(request));
        senderMapper.insertBilibiliFinder(bilibiliFinder);
        return new ResultData("获取成功！",map);
    }
}
