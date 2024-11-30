package LinkedListDemos;

public class DoubleLL {
    private Node head;
    private Node tail;
    private int size;

    public DoubleLL(){
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    class Node{
        public int value;
        public Node next;
        public Node prev;
        public Node(int value){
            this.value = value;
        }
        public Node(int value,Node next, Node prev){
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public void insertFirst(int data){
        Node node = new  Node(data);
        if(head == null){
            head = node;
            tail = node;
        }else{
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void display(){
        Node temp = head;
        while (temp != null){
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println("End");
    }

    public void displayRev(){
        Node temp = tail;

        while (temp != null){
            System.out.print(temp.value + " ");
            temp = temp.prev;
        }
        System.out.println("end");
    }


    public void insertLast(int val){
        Node temp = new Node(val);
        if(tail == null){
            head = temp;
            tail = temp;
        }else{
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
    }

    public void insert(int data, int index){
        if(index == 0){
            insertFirst(data);
        } else if (index == size) {
            insertLast(data);
        }else{
            Node node = new Node(data);
            if(head == null){
                head = node;
                tail = node;
            }
            Node temp = head;
            for(int i=0;i<index-1;i++){
                temp = temp.next;
            }

            node.next = temp.next;
            temp.next = node;
            node.prev = temp;
            if(node.next != null){
                node.next.prev = node;
            }

        }
    }

    public void deletefirst(){
        if(head == null){
            return;}
        if(head == tail){
            head = null;
            tail = null;
            return;
        }
        Node temp = head;
        head = head.next;
        head.prev = null;
        temp.next = null;
        size--;
    }

    public void deleteLast(){
        if(head == null){
            return;}
        if(head == tail){
            head = null;
            tail = null;
            return;
        }
        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        temp.prev = null;size--;
    }

    public void deleteindex(int index){
        if(index == 0){
            deletefirst();
        } else if (index == size) {
            deleteLast();
        }else {
            Node temp = head;
            for(int i=0;i<index;i++){
                temp = temp.next;
            }
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            temp.prev = null;
            temp.next = null;
        }
    }

    public static void main(String[] args) {
        DoubleLL dll = new DoubleLL();
        dll.insertFirst(1);
        dll.insertFirst(2);
        dll.insertFirst(3);
        dll.insertFirst(4);
        dll.display();
        dll.displayRev();
        dll.insertLast(44);
        dll.display();
        dll.insert(21,0);
        dll.display();
        dll.deleteindex(2);
        dll.display();

    }
}
