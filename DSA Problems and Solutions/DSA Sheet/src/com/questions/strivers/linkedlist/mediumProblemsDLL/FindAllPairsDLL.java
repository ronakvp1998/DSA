package com.questions.strivers.linkedlist.mediumProblemsDLL;

import java.util.ArrayList;
import java.util.List;

class Pair{
    int a;
    int b;
    Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
}

public class FindAllPairsDLL {

    public static void main(String[] args) {

    }

    public static List<Pair> findAllPairs(NodeDLL head, int sum){
        NodeDLL tail = findTail(head);
        NodeDLL left = head, right = tail;
        List<Pair> res = new ArrayList<>();
        while (left.data < right.data){
            if(left.data + right.data == sum){
                res.add(new Pair(left.data,right.data));
                left = left.next;
                right = right.prev;

            } else if (left.data + right.data < sum) {
                left = left.next;
            }else{
                right  = right.prev;
            }
        }
        return res;
    }

    public static NodeDLL findTail(NodeDLL head){
        NodeDLL temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        return temp;
    }
}
