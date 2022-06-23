package com.learn.collect.list;

import com.learn.vo.WatermarkEffect;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LearnList {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<WatermarkEffect> effectList = new ArrayList<WatermarkEffect>();
        WatermarkEffect effect1 = new WatermarkEffect();
        WatermarkEffect effect2 = new WatermarkEffect();
        WatermarkEffect effect3 = new WatermarkEffect();

        effect1.setIsOpen("1");
        effect1.setWatermarkName("Name");
        effect2.setIsOpen("2");
        effect2.setWatermarkName("Code");
        effect3.setIsOpen("1");
        effect3.setWatermarkName("Phone");

        effectList.add(effect1);
        effectList.add(effect2);
        effectList.add(effect3);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Date sysDate = new Date();
        System.out.println(simpleDateFormat.format(sysDate));

        effect3.getClass().getField("isOpen").get(effect3);


        Map<String,Object> map = effectList.stream().filter(a->a.getIsOpen().equals("1")).collect(Collectors.toMap(WatermarkEffect::getWatermarkName, new Function<WatermarkEffect, Object>() {
            @Override
            public Object apply(WatermarkEffect watermarkEffect) {
                return null;
            }
        }));
    }
}
