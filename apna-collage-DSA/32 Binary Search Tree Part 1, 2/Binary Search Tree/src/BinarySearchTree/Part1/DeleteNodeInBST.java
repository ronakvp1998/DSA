package BinarySearchTree.Part1;

// code 3 :- Delete Node in BST
public class DeleteNodeInBST {

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

    public static Node delete(Node root, int val){
        if(root.data < val){
            root.right = delete(root.right,val);
        }
        else if(root.data > val){
            root.left = delete(root.left,val);
        }else{  // got the node to be deleted

            // case 1 - leaf node
            if(root.left == null && root.right == null){
                return null;
            }

            // case 2 - single child
            if(root.left == null){
                return root.right;
            }
            else if(root.right == null){
                return root.left;
            }

            // case 3 - both left and right children exists
            Node is = findInOrderSuccessor(root.right);
            // replace the data with inorder successor data
            root.data = is.data;
            // delete the inorder successor data node
            root.right = delete(root.right,is.data);

        }

        return root;
    }

    public static Node findInOrderSuccessor(Node root){
        while(root.left != null){
            root = root.left;
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

    public static void main(String[] args) {
        int values[] = {8,5,3,1,4,6,10,11,14};
        Node root = null;

        for(int i=0;i<values.length;i++){  
            root = insert(root,values[i]);
        }

        inorder(root);
        System.out.println();

        root = delete(root,1);
        System.out.println();

        inorder(root);

    }
}
