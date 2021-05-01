package com.fybgame.web.mapper.bilibili;

import com.fybgame.web.entity.BilibiliFinder;
import com.fybgame.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * @Author:fyb
 * @Date: 2021/3/5 1:56
 * @Version:1.0
 */
@Mapper
public interface SenderMapper {

    public User getUser(@Param("crc32b") String crc32b,@Param("database") String database);

    public int insertBilibiliFinder(BilibiliFinder bilibiliFinder);
}
