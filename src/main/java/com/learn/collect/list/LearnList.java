package com.learn.collect.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class LearnList {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1);
        arrayList.removeAll(new ArrayList<Integer>());

        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(1);
    }
}
