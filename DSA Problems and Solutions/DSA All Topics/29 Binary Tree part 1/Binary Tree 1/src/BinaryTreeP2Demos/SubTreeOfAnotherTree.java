package BinaryTreeP2Demos;

import BinaryTreeP1Demos.DiameterOfTree;

public class SubTreeOfAnotherTree {

    static class Node{
        int data;
        Node left, right;

        public Node(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static boolean isSubTree(Node root, Node subRoot){

        // base case
        if(root == null){   // tree became empty
            return false;
        }

        // 1 find the subroot node in the original tree
        if(root.data == subRoot.data){
            if(isIdentical(root,subRoot)){
                return true;
            }
        }

        // 2 check in left subtree
        Boolean leftAns = isSubTree(root.left,subRoot);     // leftSubTree -> true if found

        // 3 check in right subtree
        Boolean rightAns = isSubTree(root.right,subRoot);    // rightSubTree -> true is found

        return leftAns || rightAns;
    }

    public static boolean isIdentical(Node node, Node subRoot){
        // when both become null means identical so return true
        if(node == null && subRoot == null){
            return true;
        }

        else if(node == null || subRoot == null || node.data != subRoot.data){
            return false;
        }

        // check left subtree return false if non identical
        if(!isIdentical(node.left,subRoot.left)){
            return false;
        }

        // check right subtree return false if non identical
        if(!isIdentical(node.right,subRoot.right)){
            return false;
        }

        // if all the above condition gets satisfied then we can return true
        return true;
    }

    public static void main(String[] args) {

        /*
                     1
                  2     3
                4   5  6  7

        */

        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        /*
                    2
                  4   5
         */

        Node subRoot = new Node(2);
        subRoot.left = new Node(4);
//        subRoot.right = new Node(5);


        System.out.println(isSubTree(root,subRoot));
    }
}
