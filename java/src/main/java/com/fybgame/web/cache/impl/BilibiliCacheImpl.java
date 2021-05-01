package com.fybgame.web.cache.impl;

import com.fybgame.web.cache.BilibiliCache;
import com.fybgame.web.entity.User;
import com.fybgame.web.mapper.bilibili.SenderMapper;
import com.fybgame.web.utils.RedisUtil;
import com.fybgame.web.utils.StrUtils;
import com.fybgame.web.utils.bilibili.BarrageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author:fyb
 * @Date: 2021/3/5 3:36
 * @Version:1.0
 */
@Service
public class BilibiliCacheImpl implements BilibiliCache {
    @Resource
    RedisUtil redisUtil;
    @Resource
    SenderMapper senderMapper;


    @Override
    public List<User> getUser(String crc32b, String database) {
        Object o = redisUtil.hget("bilibiliUser",crc32b+","+database);
        List<User> l = new ArrayList<>();
        if(o == null){
            User u = senderMapper.getUser(crc32b,database);
            if(u == null && database.length() == 1 && StrUtils.isBasicBilibiliDatabase(database.charAt(0))){
                String sixteen = Long.toHexString(Long.valueOf(crc32b));
                User trackUser = new User(BarrageUtils.crc32bCrack(sixteen),sixteen);
                redisUtil.hset("bilibiliUser",crc32b+","+database,trackUser);
                l.add(trackUser);
                return l;
            }
            redisUtil.hset("bilibiliUser",crc32b+","+database,u);
            if(u.getId().indexOf(",")!=-1){
                String[] s = u.getId().split(",");
                for (int i = 0; i < s.length; i++) {
                    l.add(new User(s[i],crc32b));
                }
            }
            else{
                l.add(u);
            }
            return l;
        }
        User u = (User)o;
        if(u.getId().indexOf(",")!=-1){
            String[] s = u.getId().split(",");
            for (int i = 0; i < s.length; i++) {
                l.add(new User(s[i],crc32b));
            }
        }
        else{
            l.add(u);
        }
        return l;
    }


}
