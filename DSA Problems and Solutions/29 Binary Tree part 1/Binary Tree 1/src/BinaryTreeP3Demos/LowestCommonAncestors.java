package BinaryTreeP3Demos;

import java.util.ArrayList;

public class LowestCommonAncestors {


    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static boolean getPath(Node root, int n, ArrayList<Node> path){

        if(root == null){
            return false;
        }

        // add the node to the path arraylist
        path.add(root);

        // got the node
        if(root.data == n){
            return true;
        }

        // check inside left subtree
        boolean foundLeft = getPath(root.left, n, path);
        // check inside right subtree
        boolean foundRight = getPath(root.right, n, path);

        if(foundLeft || foundRight){
            return true;
        }

        // remove curr node from the path arraylist
        path.remove(path.size()-1);

        return false;
    }

    public static Node lca(Node root, int n1, int n2){
        ArrayList<Node> path1 = new ArrayList<>();
        ArrayList<Node> path2 = new ArrayList<>();

        // get paths for node n1 and node n2
        getPath(root, n1, path1);
        getPath(root, n2 ,path2);

        // last common ancestor
        int i = 0;
        for(;i< path1.size() && i <  path2.size() ;i++){
            if(path1.get(i) != path2.get(i)){
                break;
            }
        }

        // last equal node will be at i-1 index
        Node lcs = path1.get(i-1);

        return lcs;

    }
    public static void main(String[] args) {

        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        int n1 = 4, n2 = 5;

        System.out.println(lca(root,n1,n2).data);
    }
}
