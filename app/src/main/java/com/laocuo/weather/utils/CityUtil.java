package com.laocuo.weather.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

public class CityUtil {
    public static String[] stringCitys = new String[]{
            "﻿阿里","日喀则","喀什","阿图什","和田","阿拉尔","阿克苏","伊宁","博乐","库尔勒","石河子",
            "吐鲁番","乌鲁木齐","昌吉","五家渠","塔城","克拉玛依","阿勒泰","景洪","西沙","三亚","乐东",
            "五指山","东方","昌江","白沙","儋州","保亭","陵水","万宁","琼中","屯昌","琼海","文昌","临高",
            "崇左","防城港","北海","钦州","澄迈","定安","海口","湛江","茂名","阳江","南沙","中沙","珠海",
            "普洱","临沧","德宏","保山","怒江","大理","香格里拉","丽江","山南","拉萨","林芝","那曲","昌都",
            "玉树","甘孜","红河","玉溪","楚雄","昆明","文山","百色","兴义","攀枝花","曲靖","凉山","昭通",
            "水城","安顺","贵阳","毕节","南宁","贵港","来宾","河池","柳州","玉林","云浮","肇庆","梧州",
            "桂林","贺州","都匀","凯里","遵义","铜仁","永州","怀化","邵阳","娄底","雅安","乐山","眉山",
            "宜宾","泸州","自贡","资阳","内江","成都","重庆","遂宁","南充","广安","阿坝","德阳","绵阳",
            "巴中","武都","广元","恩施","吉首","张家界","益阳","常德","宜昌","荆州","达州","汉中","安康",
            "神农架","荆门","襄阳","十堰","格尔木","果洛","海南","海西","海北","张掖","哈密","嘉峪关","酒泉",
            "合作","黄南","海东","临夏","兰州","天水","定西","白银","平凉","固原","西宁","武威","金昌",
            "中卫","吴忠","银川","阿左旗","石嘴山","宝鸡","杨凌","西安","咸阳","铜川","渭南","庆阳","商洛",
            "运城","三门峡","洛阳","临汾","延安","吕梁","榆林","朔州","乌海","临河","鄂尔多斯","包头",
            "呼和浩特","江门","佛山","中山","广州","东莞","清远","深圳","惠州","河源","韶关","汕尾","汕头",
            "揭阳","潮州","梅州","漳州","厦门","龙岩","郴州","赣州","衡阳","湘潭","株洲","萍乡","吉安",
            "宜春","新余","三明","抚州","泉州","莆田","南平","福州","宁德","温州","长沙","岳阳","潜江",
            "天门","仙桃","咸宁","黄石","孝感","武汉","鄂州","黄冈","南昌","鹰潭","上饶","景德镇","九江",
            "安庆","池州","铜陵","随州","信阳","南阳","驻马店","漯河","周口","六安","合肥","阜阳","淮南",
            "蚌埠","宿州","衢州","丽水","金华","黄山","绍兴","杭州","湖州","嘉兴","台州","宁波","舟山",
            "宣城","芜湖","马鞍山","滁州","南京","镇江","苏州","无锡","常州","南通","扬州","淮安","泰州",
            "盐城","上海","平顶山","许昌","济源","郑州","开封","晋城","焦作","新乡","长治","鹤壁","濮阳",
            "安阳","亳州","商丘","淮北","徐州","枣庄","菏泽","济宁","聊城","泰安","莱芜","晋中","太原",
            "阳泉","邯郸","邢台","忻州","石家庄","衡水","德州","济南","淄博","滨州","保定","沧州","天津",
            "宿迁","临沂","连云港","日照","青岛","潍坊","东营","烟台","威海","大连","大同","集宁","张家口",
            "廊坊","北京","承德","锡林浩特","唐山","秦皇岛","葫芦岛","朝阳","营口","锦州","盘锦","阜新",
            "鞍山","辽阳","沈阳","赤峰","通辽","丹东","本溪","抚顺","通化","白山","铁岭","辽源","四平",
            "长春","吉林","延吉","牡丹江","乌兰浩特","白城","海拉尔","松原","哈尔滨","大庆","齐齐哈尔",
            "绥化","伊春","大兴安岭","黑河","鸡西","七台河","佳木斯","鹤岗","双鸭山"
    };
    public static String[] getCityList() {
        return stringCitys;
    }

    public static ArrayList<String> stringCitysFromAssets = new ArrayList<String>();
    public static ArrayList<String> getCityListFromAssets(Context context) {
        stringCitysFromAssets.clear();
        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open("city-utf8.txt") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null) {
                stringCitysFromAssets.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringCitysFromAssets;
    }
}
