package BinaryTreeP1Demos;

// code 2 :- PreOrder traversal
// 1 root
// 2 left subtree
// 3 right subtree

import BinaryTreeP1Demos.BuildTreePreorder.*;
public class PreOrderTraversals {

    public static void preOrder(Node root){

        if(root == null){
            System.out.print(-1 + " ");
            return;
        }
        // 1 print root
        System.out.print(root.data + " ");
        // 2 print left
        preOrder(root.left);
        // 3 print right
        preOrder(root.right);

    }

    public static void main(String[] args) {
        int nodes[] = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};

        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes);
        System.out.println(root.data);

        preOrder(root);
    }
}
