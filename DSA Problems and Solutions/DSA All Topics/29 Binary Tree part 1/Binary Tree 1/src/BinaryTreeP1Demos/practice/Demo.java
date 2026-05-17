package BinaryTreeP1Demos.practice;

import BinaryTreeP1Demos.HeightOfTree;

import java.util.LinkedList;
import java.util.Queue;

public class Demo {

    static class Node{
        int data;
        Node left;
        Node right;

        Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }

    }

    static  class BinaryTree{
        static int idx = -1;

        public static Node buildTree(int nodes[]){
            idx++;
            if(nodes[idx] == -1){
                return null;
            }

            Node newNode = new Node(nodes[idx]);

            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);

            return newNode;

        }

        public static void preOrder(Node root){
            if(root == null){
                return;
            }

            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);

        }

        public static void inOrder(Node root){
            if(root == null){
                return;
            }

            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }

        public static void levelOrder(Node root){
            if(root == null){
                return;
            }

            Queue<Node> q = new LinkedList<>();
            q.add(root);
            q.add(null);

            while(!q.isEmpty()){
                Node curr = q.remove();
                if(curr == null){
                    System.out.println();
                    if(q.isEmpty()){
                        break;
                    }else{
                        q.add(null);
                    }
                }else{
                    System.out.print(curr.data + " ");
                    if(curr.left != null){
                        q.add(curr.left);
                    }

                    if(curr.right != null){
                        q.add(curr.right);
                    }
                }
            }
        }
    }

    public static int height(Node root){
        if(root == null){
            return 0;
        }

        int lh = height(root.left);
        int rh = height(root.right);

        return Math.max(lh,rh) + 1;
    }

    public static int count(Node root){
        if(root == null){
            return 0;
        }

        int leftCount = count(root.left);
        int rightCount = count(root.right);

        return leftCount + rightCount + 1;

    }

    public static int diameter(Node root){
        if(root == null){
            return 0;
        }

        int leftDiam = diameter(root.left);
        int leftHt = height(root.left);
        int rightDiam = diameter(root.right);
        int rightHt = height(root.right);

        int selfDiam = leftHt + rightHt + 1;

        return Math.max(selfDiam,Math.max(leftDiam,rightDiam));

    }

    static class Info{
        int diameter ;
        int height;
        public Info(int diameter, int height){
            this.diameter = diameter;
            this.height = height;
        }
    }

    public static Info diameter2(Node root){

        if(root == null){
            return new Info(0,0);
        }

        Info leftInfo = diameter2(root.left);
        Info rightInfo = diameter2(root.right);

        int diam = Math.max(Math.max(leftInfo.diameter,rightInfo.diameter),leftInfo.height+rightInfo.height+1);
        int ht = Math.max(leftInfo.height,rightInfo.height) + 1;

        return new Info(diam,ht);
    }
    public static void main(String[] args) {
//        int nodes[] = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
//        BinaryTree d = new BinaryTree();
//        Node root = d.buildTree(nodes);
//        System.out.println(root.data);
//
//        d.preOrder(root);
//        System.out.println();
//        d.inOrder(root);
//        System.out.println("\n");
//        d.levelOrder(root);

        /*
                      1
                  2      3
                4   5   6   7
         */

        Node root1 = new Node(1);
        root1.left = new Node(2);
        root1.right = new Node(3);
        root1.left.left = new Node(4);
        root1.left.right = new Node(5);
        root1.right.left = new Node(6);
        root1.right.right = new Node(7);

        System.out.println("height " +height(root1));
        System.out.println("count " + count(root1));
    }
}

