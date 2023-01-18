package com.fybgame.web.cache;

import com.fybgame.web.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:fyb
 * @Date: 2021/3/5 3:34
 * @Version:1.0
 */
public interface BilibiliCache {
    public List<User> getUser(@Param("crc32b") String crc32b, @Param("database") String database);

}
