package com.questions.practice.dll;

class NodeDLL{
    int data ;
    NodeDLL next;
    NodeDLL prev;

    public NodeDLL(int data) {
        this.data = data;
    }
    public NodeDLL(int data, NodeDLL prev, NodeDLL next) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class DoubleLinkedList {
    public static void main(String[] args) {
        int arr[] = {3,4,2,3,1,3,4,3};
        NodeDLL head = createDLL(arr);
        printDLL(head);
//        head = deleteHead(head);
//        printDLL(head);
//        head = deleteIndex(head,5);
//        printDLL(head)
//        head = deleteLast(head);
//        printDLL(head);
//        head =deleteValue(head,3);
//        printDLL(head);
//        head = deleteLast(head);
//        printDLL(head);
//        head = deleteValues(head,3);
//        printDLL(head);
        head = reverseDLL(head);
        printDLL(head);
    }

    // reverse DLL
    public static NodeDLL reverseDLL(NodeDLL head){
        NodeDLL prev = null;
        NodeDLL curr = head;
        NodeDLL next = null;
        while (curr != null){
            next = curr.next;
            curr.next = prev;
            curr.prev = next;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    // 4 delete last
    public static NodeDLL deleteLast(NodeDLL head){
        while (head == null || head.next == null){
            return null;
        }
        NodeDLL temp = head;
        NodeDLL prev = null;
        while (temp.next.next != null){
            temp = temp.next;
        }
        temp.next.prev = null;
        temp.next = null;
        return head;
    }

    // 4 delete all value
    public static NodeDLL deleteValues(NodeDLL head, int value){
        if(head == null){
            return null;
        }
        if(head.data == value){
            head = head.next;
            head.prev = null;
        }
        NodeDLL curr = head;
        NodeDLL prev = null;
        while (curr != null){
            if(curr.data == value && curr.next == null){
                curr.prev = null;
                prev.next = null;
                return head;
            }
            if(curr.data == value && curr.next != null){
                prev.next = curr.next;
                curr.next.prev = curr.prev;
                curr.prev = null;
                curr = curr.next;
            }else{
                prev = curr;
                curr = curr.next;
            }
        }
        return head;
    }

    // 3 delete a value
    public static NodeDLL deleteValue(NodeDLL head, int value){
        if(head == null){
            return null;
        }
        NodeDLL curr = head;
        NodeDLL prev = null;
        while (curr != null){
            if(curr.data == value){
                prev.next = curr.next;
                curr.next.prev = prev.next;
                curr.next = null;
                curr.prev = null;
                break;
            }
            prev = curr;
            curr = curr.next;
        }
        return head;
    }

    // 2 delete index
    public static NodeDLL deleteIndex(NodeDLL head, int index){
        if(head == null){
            System.out.println("Empty DLL");
            return null;
        }
        if(index == 0){
            return deleteHead(head);
        }

        int count = 0;
        NodeDLL curr = head;
        NodeDLL prev = null;
        while (curr != null){
            if(count == index && curr.next == null){
                prev.next = null;
                curr.prev=null;
                break;
            }
            if(count == index && curr.next != null){
                prev.next = curr.next;
                curr.next.prev = curr.prev;
                curr.next = null;
                curr.prev = null;
                break;
            }
            count++;
            prev = curr;
            curr = curr.next;
        }
        return head;
    }

    // 1 delete head
    public static NodeDLL deleteHead(NodeDLL head){
        if(head == null || head.next == null){
            System.out.println("Empty DLL");
            return null;
        }
        NodeDLL curr = head;
        head = head.next;
        head.prev = null;
        curr.next = null;
        return head;
    }

    // printDLL
    public static void printDLL(NodeDLL head){
        if(head == null){
            System.out.println("Empty DLL");
            return;
        }
        NodeDLL temp = head;
        while (temp != null){
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println();
    }

    // create DLL
    public static NodeDLL createDLL(int arr[]){
        if(arr.length == 0){
            System.out.println("Empty DLL");
            return null;
        }
        NodeDLL head = new NodeDLL(arr[0]);
        NodeDLL curr = head;
        NodeDLL prev = null;
        for(int i=1;i< arr.length;i++){
            prev = curr;
            NodeDLL newNode = new NodeDLL(arr[i]);
            newNode.prev = prev;
            curr.next = newNode;
            curr = newNode;
        }
        return head;
    }

}
