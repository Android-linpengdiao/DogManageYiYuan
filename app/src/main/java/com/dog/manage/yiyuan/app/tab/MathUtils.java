package com.dog.manage.yiyuan.app.tab;

class MathUtils {
    static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
