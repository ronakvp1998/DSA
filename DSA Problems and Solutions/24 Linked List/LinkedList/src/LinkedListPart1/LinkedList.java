package LinkedListPart1;

public class LinkedList {
    public static class Node{
        public  int data;
        public Node next;
        public Node(int data){
            this.data = data;
            this.next = null;
        }
    }
    public static Node head;
    public static Node tail;
    public static int size;

    // 1 add first
    public void addFirst(int data){
        // step 1 = create new node
        Node newNode = new Node(data);
        size++;
        if(head == null){
            head = tail = newNode;
            return;
        }
        // step 2 = newNode next -> head
        newNode.next = head;
        // step 3 = updated head to point to new node
        head = newNode;
    }

    // 2 add Last
    public void addLast(int data){
        Node newNode = new Node(data);
        size++;
        if(head == null ){
            head = tail = newNode;
            return;
        }
        tail.next = newNode;
        tail = newNode;

    }

    // 3 print LL
    public static void printLL(){
        if(head == null){
            System.out.println("LL is empty");
            return;
        }
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println("null");
    }

    // 4 add to index
    public void add(int idx, int data){
        if(idx == 0){
            addFirst(data);
            return;
        }
        Node newNode = new Node(data);
        size++;
        Node temp = head;
        int i=0;
        while (i<idx-1){
            temp = temp.next;
            i++;
        }

        // i = idx-1; temp -> prev
        newNode.next = temp.next;
        temp.next = newNode;

    }

    // 5 remove first
    public int removeFirst(){
        if(size == 0){  // LL is empty
            System.out.println("LL is empty");
            return -1;
        }else if(size == 1){  // LL size = 1
            int val = head.data;
            head = tail = null;
            size--;
            return val;
        }
        int val = head.data;
        head = head.next;
        size--;
        return val;
    }

    // 6 remove last
    public int removeLast(){
        if(size == 0){
            System.out.println("LL is empty");
            return -1;
        } else if (size == 1) {
            int val = head.data;
            head = tail = null;
            size = 0;
            return val;
        }
        // prev : i = size - 2
        Node prev = head;
        for(int i=0;i<size-2;i++){
            prev = prev.next;
        }
        int val = prev.next.data; // tail data
        prev.next = null;
        tail = prev;
        size--;
        return val;
    }
    // 7 iterative search
    public int itrSearch(int key){
        Node temp = head;
        int i = 0;
        while (temp != null){
            if(temp.data == key){   // key found
                return i;
            }
            temp = temp.next;
            i++;
        }

        // key not found
        return -1;
    }

    // code 8 recursive search using static variable
    public static int index = 0 ;
    public int recSearch(int key,Node temp){
        if(temp == null){
            return -1;
        }
        if(temp.data == key){
            return index++;
        }
        index++;
        return recSearch(key,temp.next);
    }

    // code 8 recursive search without static variable -> time comp=O(n), space comp=O(n) both are linear
    public int recSearch2(int key,Node temp){
        if(temp == null){
            return -1;
        }
        if(temp.data == key){
            return 0;
        }
        int idx = recSearch2(key,temp.next);
        if(idx == -1){
            return -1;
        }
        return idx + 1;
    }

    // code 9 reverse a ll using iterative approach
    public void reverseLL(){
        Node prev = null;
        Node curr = tail = head;
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
    public void deleteNthFromEnd(int n){
        // calculate size of LL->sz
        int sz = 0;
        Node temp = head;
        while (temp != null){
            temp = temp.next;
            sz++;
        }
        if(n == sz){ // where both are last node from starting of the LL so delete head
            head = head.next; // remove first operation
            size--;
            return;
        }
        // sz-n
        int i=1;
        int iToFind = sz-n;
        Node prev = head;
        while (i < iToFind){
            prev = prev.next;
            i++;
        }
        prev.next = prev.next.next;
        size--;
        return;
    }


    public static Node getHead() {
        return head;
    }

    public static void setHead(Node head) {
        LinkedList.head = head;
    }

    public static Node getTail() {
        return tail;
    }

    public static void setTail(Node tail) {
        LinkedList.tail = tail;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        LinkedList.size = size;
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        LinkedList.index = index;
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(3);
        ll.addLast(4);
        ll.add(2,9);
//        ll.printLL(); // 1->2->9->3->4
//        System.out.println(ll.size);
        // remove first
//        System.out.println(ll.removeFirst());
//        ll.printLL();
        // remove last
//        System.out.println(ll.removeLast());
//        ll.printLL();
//        System.out.println(ll.itrSearch(3));
//        System.out.println(ll.recSearch(44,head));
//        System.out.println(ll.recSearch2(4,head));

        // reverse
//        ll.printLL();
//        ll.reverseLL();
        ll.printLL();
        ll.deleteNthFromEnd(3);
        ll.printLL();
    }

}
