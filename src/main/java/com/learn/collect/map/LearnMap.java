package com.learn.collect.map;

import java.util.HashMap;

public class LearnMap {
    public static void main(String[] args) {
        HashMap<HS,String> map = new HashMap<>();
        map.put(new HS(),"1");
        map.put(new HS(),"2");
        System.out.println(map);
        map.put(new HS(){
            @Override
            public boolean equals(Object obj) {
                return true;
            }
        },"3");
        System.out.println(map);
        map.put(new HS(){
            @Override
            public boolean equals(Object obj) {
                return true;
            }
            @Override
            public int hashCode(){
               return 2;
            }
        },"3");
        System.out.println(map);
    }

}
 class HS
{
    public int hashCode()
    {
        return 1;
    }
}
