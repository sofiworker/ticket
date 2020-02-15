package com.dm.ticket.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @description 唯一id工具类
 */
public class SnowflakeUtil {

    public static Long createId(){
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        return snowflake.nextId();
    }
}
