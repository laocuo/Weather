package com.laocuo.weather.utils;

import com.laocuo.weather.R;

import java.util.Random;

/**
 * Created by hoperun on 9/30/16.
 */

public class ImagesUtil {
    private static final Random RANDOM = new Random();

    public static int getRandomNavigationDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.cheese_1;
            case 1:
                return R.drawable.cheese_2;
            case 2:
                return R.drawable.cheese_3;
            case 3:
                return R.drawable.cheese_4;
            case 4:
                return R.drawable.cheese_5;
        }
    }

    public static int getDrawableByCode(int code) {
        switch (code) {
            default: return R.drawable.code_99;
            case 0:
                return R.drawable.code_0;
            case 1:
                return R.drawable.code_1;
            case 2:
                return R.drawable.code_2;
            case 3:
                return R.drawable.code_3;
            case 4:
                return R.drawable.code_4;
            case 5:
                return R.drawable.code_5;
            case 6:
                return R.drawable.code_6;
            case 7:
                return R.drawable.code_7;
            case 8:
                return R.drawable.code_8;
            case 9:
                return R.drawable.code_9;
            case 10:
                return R.drawable.code_10;
            case 11:
                return R.drawable.code_11;
            case 12:
                return R.drawable.code_12;
            case 13:
                return R.drawable.code_13;
            case 14:
                return R.drawable.code_14;
            case 15:
                return R.drawable.code_15;
            case 16:
                return R.drawable.code_16;
            case 17:
                return R.drawable.code_17;
            case 18:
                return R.drawable.code_18;
            case 19:
                return R.drawable.code_19;
            case 20:
                return R.drawable.code_20;
            case 21:
                return R.drawable.code_21;
            case 22:
                return R.drawable.code_22;
            case 23:
                return R.drawable.code_23;
            case 24:
                return R.drawable.code_24;
            case 25:
                return R.drawable.code_25;
            case 26:
                return R.drawable.code_26;
            case 27:
                return R.drawable.code_27;
            case 28:
                return R.drawable.code_28;
            case 29:
                return R.drawable.code_29;
            case 30:
                return R.drawable.code_30;
            case 31:
                return R.drawable.code_31;
            case 32:
                return R.drawable.code_32;
            case 33:
                return R.drawable.code_33;
            case 34:
                return R.drawable.code_34;
            case 35:
                return R.drawable.code_35;
            case 36:
                return R.drawable.code_36;
            case 37:
                return R.drawable.code_37;
            case 38:
                return R.drawable.code_38;
        }
    }
}