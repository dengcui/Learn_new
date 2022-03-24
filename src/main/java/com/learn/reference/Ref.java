package com.learn.reference;

public class Ref {
    Object v;
    Ref(Object v){this.v = v;}

    @Override
    public String toString() {
        return this.v.toString();
    }
}
