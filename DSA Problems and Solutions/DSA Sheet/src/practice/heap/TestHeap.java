package com.questions.practice.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHeap {
    private final List<Integer> heap;

    public TestHeap(){
        heap = new ArrayList<>();
    }

    private void heapify(int index){
        int size = heap.size();
        while (index < size){
            int largest = index;
            int leftIdx = 2*index + 1;
            int rightIdx = 2*index + 2;
            if(leftIdx < size && heap.get(leftIdx) > heap.get(largest)){
                largest = leftIdx;
            }
            if(rightIdx < size && heap.get(rightIdx) > heap.get(largest)){
                largest = rightIdx;
            }
            if(largest != index){
                Collections.swap(heap,largest,index);
                index = largest;
            }else{
                break;
            }
        }
    }

    private void insert(int value){
        heap.add(value);
        int index = heap.size() - 1;
        while (index > 0){
            int parentIdx = (index - 1)/2;
            if(heap.get(parentIdx) < heap.get(index)){
                Collections.swap(heap,parentIdx,index);
                index = parentIdx;
            }else{
                break;
            }
        }
    }

    private void print(){
        System.out.println(heap);
    }
}
