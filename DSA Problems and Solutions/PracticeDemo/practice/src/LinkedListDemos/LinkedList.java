package LinkedListDemos;

public class LinkedList{
    private Node head;
    private Node tail;
    private int size;

    public LinkedList(){
        this.size = 0;
    }

    public class Node{
        public int data;
        public Node next;

        public Node(int data){
            this.data = data;
        }

        public Node(int data, Node next){
            this.data = data;
            this.next = next;
        }
    }

    public void insertFirst(int val){
        Node node = new Node(val);
        node.next = head;
        head = node;
        if(tail == null){
            tail = head;
        }

        size += 1;
    }

    public void display(){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
    }

    public void insertLast(int data){
        if(tail == null ){
            insertFirst(data);
            return;
        }
        Node node = new Node(data);
        tail.next = node;
        tail = node;
        size+=1;

    }

    public void insert(int data,int index){
        if(index > size){
            return;
        }
        if(index == 0){
            insertFirst(data);
            return;
        } else if (index == size) {
            insertLast(data);
            return;
        }

        Node temp = head;
        for(int i=1;i<index;i++){
            temp = temp.next;
        }
        Node node = new Node(data,temp.next);
        temp.next = node;
        size++;
    }

    public int deleteFirst(){
        if(size <= 0){
            return -1;
        }
        int val = head.data;
        head = head.next;
        if(head == null){
            tail = null;
        }
        size--;
        return val;
    }

    public int deleteLast(){
        if(size <= 1){
            return deleteFirst();
        }
        Node temp = head;
        while (temp.next.next != null){
            temp = temp.next;
        }
        int val = temp.data;
        tail = temp;
        tail.next = null;
        size--;
        return val;
    }


    public Node get(int index){
        Node node = head;
        for(int i=0;i<index;i++){
            node = node.next;
        }
        return node;
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
//        ll.insertFirst(3);
//        ll.insertFirst(32);
//        ll.insertFirst(2);
//        ll.insertFirst(1);
//        ll.insertFirst(6);
        ll.insertLast(44);
        ll.insert(111,6);
        ll.display();
        System.out.println();
        ll.deleteFirst();
        ll.display();
        System.out.println();
        ll.deleteLast();
        ll.display();
        System.out.println();


    }
}
