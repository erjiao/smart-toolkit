package com.bmsoft.toolkit.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * id 生成器
 * @author llk
 * @date 2019-12-11 11:56
 */
@Slf4j
public final class IdUtils {

    private static final SmallSnowflake SMALL_SNOWFLAKE = new SmallSnowflake();

    public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private IdUtils() {}

    public static long getId() {
        return SMALL_SNOWFLAKE.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(getId());
    }

    /**
     * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace("-", "");
    }

    /**
     * 格式化的毫秒时间
     */
    public static String getMillisecond() {
        return LocalDateTime.now().format(MILLISECOND);
    }

}
