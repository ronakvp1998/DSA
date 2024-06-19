package BinaryTreeP1Demos;

// code 9 :- diameter of a Tree approach 2

public class DiameterOfTreeApporach2 {

    static class Node{
        int data;
        Node left, right;

        public Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static class Info{
        int diameter;
        int height;

        public Info(int diameter,int height){
            this.diameter = diameter;
            this.height = height;
        }
    }

    public static Info diameter(Node root){     //O(n)
        // base case
        if(root == null){
            return new Info(0,0);
        }

        // calculate lh, rh, ld, rh
        Info leftInfo = diameter(root.left);
        Info rightInfo = diameter(root.right);

        int diam = Math.max(Math.max(leftInfo.diameter, rightInfo.diameter), leftInfo.height+ rightInfo.height + 1);
        int ht = Math.max(leftInfo.height,rightInfo.height) + 1;

        return new Info(diam,ht);

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
//        root.right.right = new Node(7);

        System.out.println("Diameter: " + diameter(root).diameter);
        System.out.println("Height: " + diameter(root).height);

    }
}
