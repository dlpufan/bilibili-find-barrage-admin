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
import com.fybgame.web.utils.LogUtils;
import com.fybgame.web.utils.MyTime;
import com.fybgame.web.utils.ThreadUtils;
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
import java.util.zip.CRC32;

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
    public ResultData matchBarrageUser(Integer cid, String match,Integer av,Integer p, HttpServletRequest request,Boolean isUseHistory) {
        LogUtils.info("cid:"+cid+",match:"+match+",av:"+av+",p:"+p);
        if(cid == null||match == null){
            return new ResultData(HttpCode.ERROR,"缺少参数");
        }
        if("".equals(match)){
            return new ResultData(HttpCode.ERROR,"暂不支持空match");
        }
        StopWatch watch = new StopWatch();
        watch.start();
        List<BarrageSender> barrageSenders = new ArrayList<>();
        List<Barrage> barrages = isUseHistory?BarrageUtils.getAllBarrages(cid):BarrageUtils.getBarrages(cid);
        boolean isUid = false;
        String uidCrc32b = "";
        if(match.length()>=4){
            if(match.substring(0,4).toLowerCase().equals("uid:")||match.substring(0,4).toLowerCase().equals("uid：")){
                isUid = true;
                CRC32 crc32 = new CRC32();
                crc32.update(match.substring(4).getBytes());
                uidCrc32b = Long.toHexString(crc32.getValue());
            }
        }
        for (Barrage barrage : barrages) {
            if(barrage.isMatching(match)){
                ThreadUtils.threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        BigInteger tenRadix = new BigInteger(barrage.getUid(),16);
                        List<User> users = bilibiliCache.getUser(tenRadix.toString(), String.valueOf(barrage.getUid().charAt(0)));
                        BarrageSender barrageSender = new BarrageSender(barrage,users);
                        barrageSenders.add(barrageSender);
                    }
                });
            }
            else if(isUid&&barrage.getUid().equals(uidCrc32b)){
                List<User> users = new ArrayList<>();
                users.add(new User(match.substring(4),uidCrc32b));
                BarrageSender barrageSender = new BarrageSender(barrage,users);
                barrageSenders.add(barrageSender);
            }
        }
        ThreadUtils.isComplete();
        Map<String,Object> map = new HashMap<>(2);
        map.put("senderInfo",barrageSenders);
        map.put("time",watch.getTime());
        BilibiliFinder bilibiliFinder = new BilibiliFinder();
        bilibiliFinder.setAv(av);
        bilibiliFinder.setP(p);
        bilibiliFinder.setText(match);
        bilibiliFinder.setTime(MyTime.getCurrentTime());
        bilibiliFinder.setIp(IPUtils.getIpAddr(request));
        bilibiliFinder.setUseHistory(isUseHistory);
        LogUtils.info(bilibiliFinder.toString());
        senderMapper.insertBilibiliFinder(bilibiliFinder);
        return new ResultData("获取成功！",map);
    }
}
