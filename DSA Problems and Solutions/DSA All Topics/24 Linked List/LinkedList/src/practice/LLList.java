package practice;

public class LLList {

    public static class Node{
        public int data;
        public Node next;

        public Node(int data){
            this.data = data;
            this.next = null;
        }
    }

    public static int size;
    public static Node head;
    public static Node tail;

    // 1 add first
    public void addFirst(int data){
        Node newNode= new Node(data);
        size++;
        if(head == null){
            head = tail = newNode;
            return;
        }

        newNode.next = head;
        head = newNode;
    }

    // 2 printLL
    public void printLL(){
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data +"->");
            temp = temp.next;
        }
        System.out.println("null");
    }

    // 3 add Last
    public void addLast(int data){
        Node newNode = new Node(data);
        if(head == null){
            addFirst(data);
            return;
        }
        size++;
        tail.next = newNode;
        tail = newNode;
    }

    // 4 add at a index
    public void add(int data, int index){
        while (index == 0){
            addFirst(data);
            return;
        }
        Node newNode = new Node(data);
        size++;
        Node temp = head;
        int count = 0;
        while(count != index-1){
            temp = temp.next;
            count++;
        }
        newNode.next = temp.next;
        temp.next = newNode;
    }

    // 5 remove first
    public int removeFirst(){
        if(size == 0){
            System.out.println("LL is empty");
            return -1;
        }else if(size == 1){
            int data= head.data;
            head = tail = null;
            size--;
            return data;
        }
        int data = head.data;
        head = head.next;
        size--;
        return data;
    }

    // 6 remove last
    public int removeLast(){
        if(head == null){
            System.out.println("LL is empty");
            return -1;
        }if(size == 1){
            return removeFirst();
        }
        int data= tail.data;
        Node temp = head;
        while(temp.next != tail){
            temp = temp.next;
        }
        tail = temp;
        tail.next = null;
        size--;
        return data;
    }

    // 7 iterative search
    public int itrSearch(int data){
        Node temp = head;
        int count = 0;
        while (temp != null){
            if(data == temp.data){
                return count;
            }
            count++;
            temp = temp.next;
        }
        return -1;
    }

    // 8 recursive search
    public int recSearch(Node node,int data){
        if(node == null){
            return -1;
        }
        if(node.data == data){
            return 0;
        }

        int idx = recSearch(node.next,data);
        if(idx == -1){
            return -1;
        }
        return idx+1;
    }

    // reverse LL
    public void reverse(){
        Node curr = head;
        Node prev = null;
        Node next;
        while (curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    // code 10 find & remove Nth node from end
    public int removeNthFromEnd(int n){
        int size = 0;
        Node temp = head;
        while(temp != null){
            temp = temp.next;
            size++;
        }
        if(n == size){
            int data = head.data;
            head = head.next;
            size--;
            return data;
        }
        int idx = size-n;
        temp = head;
        int count = 0;
        while(count != idx ){
            temp = temp.next;
            count++;
        }
        int data = temp.data;
        temp.next = temp.next.next;
        return data;
    }

    public static void main(String[] args) {
        LLList llList = new LLList();
        llList.addFirst(1);
        llList.addFirst(2);
        llList.addFirst(3);
        llList.addFirst(4);
////        llList.printLL(); // 4->3->2->1->
//        System.out.println("size " +size);
//
        llList.addLast(5);
        llList.addLast(6);
        llList.addLast(7);
        llList.addLast(8);
//        System.out.println("size " +size);
//        llList.printLL(); // 4->3->2->1->5->6->7->8->

//        llList.addFirst(1);
//        printLL();
//        System.out.println("size " + size);

//        llList.addLast(1);
//        printLL();
//        System.out.println("size " + size);

//        llList.printLL();
//        System.out.println("size " + size);
//        llList.add(22,2);
//        llList.printLL();
//        System.out.println("size " + size);

//        llList.printLL();
//        System.out.println(llList.removeFirst());
//        llList.removeFirst();
//        System.out.println("size " + size);
//        llList.printLL();

//        llList.printLL();
//        System.out.println(llList.removeLast());
//        System.out.println("size " + size);
//        llList.printLL();

//        llList.printLL();
//        System.out.println(llList.itrSearch(3));

//        llList.printLL();
//        System.out.println(llList.recSearch(head,1));

        llList.printLL();
        llList.reverse();
        llList.printLL();

    }
}
