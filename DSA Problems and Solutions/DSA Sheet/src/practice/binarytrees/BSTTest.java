package com.questions.practice.binarytrees;


public class BSTTest {

    private TreeNode deleteNode(TreeNode root,int key){
        if(root == null){
            return null;
        }
        if(root.data == key){
            return helper(root);
        }
        TreeNode dummy = root;
        while (root != null){
            if(root.data > key){
                if(root.left != null && root.left.data == key){
                    root.left = helper(root.left);
                    break;
                }else{
                    root = root.left;
                }
            }else{
                if(root.right != null && root.right.data == key){
                    root.right = helper(root.right);
                    break;
                }else{
                    root = root.right;
                }
            }
        }
        return dummy;
    }

    private TreeNode helper(TreeNode node){
        if(node.left == null){
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }else{
            TreeNode rightChild = node.right;
            TreeNode lastRight = lastRight(node.left);
            lastRight.right = rightChild;
            return node.left;
        }
    }

    private TreeNode lastRight(TreeNode node){
        if(node.right == null){
            return node;
        }
        return lastRight(node.right);
    }

    private static TreeNode insertIntoBST(TreeNode root,int val){
        if(root == null){
            return new TreeNode(val);
        }
        TreeNode curr = root;
        while (true){
            if(curr.data <= val){
                if(curr.right != null){
                    curr = curr.right;
                }else{
                    curr.right = new TreeNode(val);
                    break;
                }
            }else{
                if(curr.left != null){
                    curr = curr.left;
                }else{
                    curr.left = new TreeNode(val);
                    break;
                }
            }
        }
        return root;
    }

    private static TreeNode searchBST(TreeNode node,int val){
        while (node != null && node.data != val){
            if(val < node.data){
                node = node.left;
            }else{
                node = node.right;
            }
        }
        return node;
    }

    private static int findCeil(TreeNode node,int val){
        if(node == null){
            return -1;
        }
        int ceil = -1;
        while (node != null){
            if(node.data == val){
                ceil = node.data;
                return ceil;
            }if(val < node.data){
                ceil = node.data;
                node = node.left;
            }else{
                node = node.right;
            }
        }
        return ceil;
    }
}
