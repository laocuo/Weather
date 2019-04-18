package com.laocuo.weather.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.laocuo.weather.R;

import java.util.Random;

/**
 Copyright (C) laocuo <laocuo@163.com>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class ImagesUtil {
    private static final Random RANDOM = new Random();

    public static int getZhiShuDrawable(int pos) {
        switch (pos) {
            default:
            case 0:
                return R.drawable.xiche;
            case 1:
                return R.drawable.chuanyi;
            case 2:
                return R.drawable.shengbing;
            case 3:
                return R.drawable.yundong;
            case 4:
                return R.drawable.lvxing;
            case 5:
                return R.drawable.ziwaixian;
        }
    }

    private static int[] backgrounds = new int[] {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p7,
            R.drawable.p8,
            R.drawable.p9,
            R.drawable.p10
    };

    public static int getRandomNavigationDrawable() {
        return backgrounds[RANDOM.nextInt(10)];
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

    public static Bitmap getResizedBitmap(Bitmap b, int widthsize, int heightsize) {
        Bitmap resizedB;
        int w = b.getWidth();
        int h = b.getHeight();
        int scaleW = widthsize/w;
        int scaleH = heightsize/h;
        int scale = scaleW>scaleH?scaleH:scaleW;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        resizedB = Bitmap.createBitmap(b, 0, 0, widthsize, heightsize, matrix, true);
        return resizedB;
    }
}
