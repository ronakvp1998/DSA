package BinaryTreeP1Demos;

// code 7 :- Node count of a Tree

public class NodeCountOfTree {

    static class Node{
        int data;
        Node left, right;

        public Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static int count(Node root){
        if(root == null){
            return 0;
        }

        int lh = count(root.left);
        int rh = count(root.right);

        return lh + rh +1;

    }

    public static int sum(Node root){
        if(root == null){
            return 0;
        }

        int leftSum = sum(root.left);
        int rightSum = sum(root.right);

        return leftSum + rightSum + root.data;
    }

    public static void main(String[] args) {
        /*
                      1
                  2      3
                4   5   6   7
         */

        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println( count(root));
        System.out.println(sum(root));
    }
}
