package com.ruoyi.common.redis.constants;

public class RedisPrefix {

    /*
    * Redis 排行榜Key格式：
    *   prefix_博客类型:dateTime
    * */
    public static final String ARTICLE_VIEW_RANK = "avr_";

    public static final String ARTICLE_VIEW = "av_";

    public static final String ARTICLE_LIKE_RANK = "alr_";

    public static final String ARTICLE_LIKE = "al_";

    public static final String ARTICLE_COLLECT_RANK = "acr_";

    public static final String ARTICLE_COLLECT = "ac_";

}
