package com.cuit.boke.constant;

public interface SysConstants {

    String EMAILREGIX = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /* 搜索模式 */
    String ES_SCORE_MODE_SUM = "sum"; // 权重分求和模式
    Float  ES_MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

}
