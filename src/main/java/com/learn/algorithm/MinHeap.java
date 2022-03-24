package com.learn.algorithm;

import sun.applet.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 最小堆
 * 20211020
 */
public class MinHeap<E> {
    Object[] queue;
    int size;

    public MinHeap(Collection<? extends E> c){
        this.queue = c.toArray();
    }
    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(20);
        arrayList.add(10);
        arrayList.add(12);
        arrayList.add(1);
        arrayList.add(7);
        arrayList.add(32);
        arrayList.add(9);

//        System.out.print( 3>>1);
//        MinHeap minHeap = new MinHeap(arrayList);
//        minHeap.heapify();
//        for(Object o:minHeap.queue){
//            System.out.print((Integer) o+" ");
//        }
    //    System.out.print(tableSizeFor(5));
        int n =2;
        n |= 6;
       // System.out.print(n);

        int i = 9 & 15;
        System.out.println(i);
        i = 9 & 31;
        System.out.println(i);
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 9999999) ? 9999999 : n + 1;
    }


    /**
     * 最小堆的建堆方法
     */
    private void heapify() {
        size = queue.length;
        //从后往前沉降非叶子节点 二叉树的size/2-1为非叶子节点
        for (int i = (size >>> 1) - 1; i >= 0; i--)
        {
            System.out.printf("SiftDown开始: %d\n",i);
            siftDown(i, (E) queue[i]);
        }
    }

    /**
     * 沉降办法
     * @param k
     * @param x
     */
    private void siftDown(int k, E x){
        siftDownComparable(k,x);
    }

    private void siftDownComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>)x;
        int half = size >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            System.out.printf("当前节点: %d,左节点：%d\n",k,queue[child]);
            //认为左右节点最小的是左节点
            Object c = queue[child];
            int right = child + 1;
            System.out.printf("当前节点: %d,右节点：%d\n",k,queue[right]);
            //判断左右节点哪个最小 再把最小的给c
            if (right < size &&
                    ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0){
                c = queue[child = right];
                System.out.printf("当前节点: %d,右节点最小：%d\n",k,queue[right]);
            }
            if (key.compareTo((E) c) <= 0)
            {
                System.out.printf("当前节点: %d,小于它的子节点跳出：%d\n",queue[k]);
                break;
            }
            queue[k] = c;
            k = child;
            System.out.printf("当前节点%d,最小子节点和父节点交换：%d\n",key,queue[k]);
        }
        queue[k] = key;
    }
}
