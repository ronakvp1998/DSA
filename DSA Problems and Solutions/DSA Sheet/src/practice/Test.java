package practice;

import strivers.linkedlist.ll.linkedlist1d.DeleteNodeInLinkedList;
import strivers.linkedlist.ll.mediumProblemsLL.OddEvenLinkedList;
import strivers.linkedlist.ll.mediumProblemsLL.RemoveNthLastNode;

import java.util.*;

public class Test {

    static class Node{
        Node next;
        int data;
        Node(int data,Node next){
            this.data = data;
            this.next = next;
        }
    }

    private static Node removeMiddle(Node head){
        if(head == null || head.next == null){
            return null;
        }
        Node dummy = new Node(0,head);
        Node slow = dummy;
        Node fast = dummy;
        fast = fast.next;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0;
        long windowSum=0;
        int maxFreq = 0;
        for(int right=0;right<nums.length;right++){
            windowSum += nums[right];
            while ((long) (right-left+1) * nums[right] - windowSum > k){
                windowSum -= nums[left];
                left++;
            }
            maxFreq = Math.max(maxFreq,right-left+1);
        }
        return maxFreq;
    }


    public int maxFrequencyBruteForce(int[] nums, int k) {
        Arrays.sort(nums);
        int maxFreq = 1;
        for(int i=0;i<nums.length;i++){
            int currentK = k;
            int currentFreq = 1;
            int target = nums[i];
            for(int j=i-1;j>=0;j--){
                int diff = target - nums[j];
                if(currentK >= diff){
                    currentK -= diff;
                    currentFreq++;
                }else{
                    break;
                }
            }
            maxFreq = Math.max(maxFreq,currentFreq);
        }
        return maxFreq;
    }

    public Node removeNthFromEndOptimal(Node head, int n) {
        Node dummy = new Node(0,head);
        Node fast = dummy;
        Node slow = dummy;
        for (int i=0;i<=n;i++){
            fast = fast.next;
        }
        while (fast != null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    public static Node oddEvenListOptimal(Node head) {
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }
        Node odd = head;
        Node even = head.next;
        Node evenHead = even;
        while (even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    private static int loopLength(Node head){
        if(head == null || head.next == null){
            return 0;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;

            if(slow == fast){
                int count = 1;
                slow = slow.next;
                while (fast != slow){
                    count++;
                    slow = slow.next;
                }
                return count;
            }
        }
        return 0;
    }

}
