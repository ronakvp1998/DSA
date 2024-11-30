package BinaryTreeP1Demos;

// code 3 :- InOrder traversal
// 1 left subtree
// 2 root
// 3 right subtree

import BinaryTreeP1Demos.BuildTreePreorder.BinaryTree;
import BinaryTreeP1Demos.BuildTreePreorder.Node;

public class InOrderTraversals {

    public static void inOrder(Node root){

        if(root == null){
//            System.out.print(-1 + " ");
            return;
        }

        // 1 print left
        inOrder(root.left);
        // 2 print root
        System.out.print(root.data + " ");
        // 3 print right
        inOrder(root.right);

    }

    public static void main(String[] args) {
        int nodes[] = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};

        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes);
        System.out.println(root.data);

        inOrder(root);
    }
}
