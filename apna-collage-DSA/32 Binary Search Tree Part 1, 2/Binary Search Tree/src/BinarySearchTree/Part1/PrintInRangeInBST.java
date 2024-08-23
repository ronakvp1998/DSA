package BinarySearchTree.Part1;

// code 4 :- Print in Range BST
public class PrintInRangeInBST {

    static class Node{
        int data;
        Node left;
        Node right;

        Node(int data){
            this.data = data;
        }
    }

    public static Node insert(Node root, int val){
        if(root == null){
            root = new Node(val);
            return root;
        }

        if(root.data > val){
            // insert into left subtree
            root.left = insert(root.left,val);
        }else{
            // insert into right subtree
            root.right = insert(root.right,val);
        }

        return root;
    }

    public static void inorder(Node root){
        if(root == null){
            return;
        }
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static void printInRange(Node root, int k1, int k2){

        if(root == null){
            return;
        }

        // case 1 -> root lies between k1 and k2
        // go in both left and right subtree
        if(root.data >= k1 && root.data <= k2){
            // check in left subtree
            printInRange(root.left, k1, k2);
            System.out.print(root.data + " ");
            // check in right subtree
            printInRange(root.right, k1, k2);
        }

        // case 2 -> check in left subtree of the root
        else if (root.data < k1) {
            printInRange(root.left, k1, k2);
        }

        // case 3 -> check in right subtree of the root
        else{
            printInRange(root.right, k1, k2);
        }
    }

    public static void main(String[] args) {
        int values[] = {8,5,3,1,4,6,10,11,14};
        Node root = null;

        for(int i=0;i<values.length;i++){  
            root = insert(root,values[i]);
        }

        inorder(root);
        System.out.println();

        printInRange(root, 5, 12);

    }
}
