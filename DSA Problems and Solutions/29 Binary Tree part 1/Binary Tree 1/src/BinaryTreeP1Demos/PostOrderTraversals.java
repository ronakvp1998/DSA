package BinaryTreeP1Demos;

// code 4 :- PostOrder traversal
// 1 left subtree
// 2 right subtree
// 3 root

import BinaryTreeP1Demos.BuildTreePreorder.BinaryTree;
import BinaryTreeP1Demos.BuildTreePreorder.Node;

public class PostOrderTraversals {

    public static void postOrder(Node root){

        if(root == null){
//            System.out.print(-1 + " ");
            return;
        }

        // 1 print left
        postOrder(root.left);
        // 2 print right
        postOrder(root.right);
        // 3 print root
        System.out.print(root.data + " ");

    }

    public static void main(String[] args) {
        int nodes[] = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};

        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes);
        System.out.println(root.data);

        postOrder(root);
    }
}
