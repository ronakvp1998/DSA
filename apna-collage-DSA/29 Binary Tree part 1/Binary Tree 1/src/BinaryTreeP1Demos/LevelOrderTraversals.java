package BinaryTreeP1Demos;

// code 5 :- LevelOrder traversal

import BinaryTreeP1Demos.BuildTreePreorder.BinaryTree;
import BinaryTreeP1Demos.BuildTreePreorder.Node;

import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversals {

    public static void levelOrder(Node root){

        if(root == null){
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);

        while(!queue.isEmpty()){
            Node currNode = queue.remove();
            if(currNode == null){
                System.out.println();
                if(queue.isEmpty()){
                    break;
                }else{
                    queue.add(null);
                }
            }else{
                System.out.print(currNode.data + " ");
                if(currNode.left != null){
                    queue.add(currNode.left);
                }
                if(currNode.right != null){
                    queue.add(currNode.right);
                }
            }
        }
    }

    public static void main(String[] args) {
        int nodes[] = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};

        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes);
//        System.out.println(root.data);

        levelOrder(root);
    }
}
