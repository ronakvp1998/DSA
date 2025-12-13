package com.questions.practice.binarytrees;


import java.util.*;

public class PracticeProblems {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        List<Integer> ans = new ArrayList<>();
//        System.out.println(checkBalance(root));
//        System.out.println(maxHeightItr(root));
//        System.out.println(checkBalance1(root) != -1);
//        int dia[] = new int [1];
//        diameter1(root,dia);
//        diameter2(root,dia);
//        System.out.println(Arrays.toString(dia));
//        int maxi[] =  {Integer.MIN_VALUE};
//        maxPathSum1(root,maxi);
//        System.out.println(Arrays.toString(maxi));
//        System.out.println(zigZag(root));
//        List<Integer> res = printBoundary(root);
//        System.out.println(res);
    }

    public List<List<Integer>> findVertical(TreeNode root){
        if(root == null){
            return new ArrayList<>();
        }
        TreeMap<Integer,TreeMap<Integer,PriorityQueue<Integer>>> nodes = new TreeMap<>();
        Queue<PairTree> todo = new LinkedList<>();
        todo.offer(new PairTree(root,0,0));
        while (!todo.isEmpty()){
            PairTree p = todo.poll();
            TreeNode temp = p.node;
            int x = p.vertical;
            int y = p.level;
            nodes.putIfAbsent(x,new TreeMap<>());
            nodes.get(x).putIfAbsent(y,new PriorityQueue<>());
            nodes.get(x).get(y).offer(temp.data);
            if(temp.left != null){
                todo.offer(new PairTree(temp.left,x-1,y+1));
            }
            if(temp.right != null){
                todo.offer(new PairTree(temp.right,x+1,y+1));
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        for(TreeMap<Integer,PriorityQueue<Integer>> ys : nodes.values()){
            List<Integer> col = new ArrayList<>();
            for(PriorityQueue<Integer> pq : ys.values()){
                while (!pq.isEmpty()){
                    col.add(pq.poll());
                }
            }
            ans.add(col);
        }
        return ans;
    }

    private static List<Integer> printBoundary(TreeNode node){
        List<Integer> res = new ArrayList<>();
        if(node == null){
            return res;
        }
        if(!isLeaf(node)){
            res.add(node.data);
        }
        addLeftBoundary(node,res);
        addLeaves(node,res);
        addRightBoundary(node,res);
        return res;
    }

    private static void addLeftBoundary(TreeNode node,List<Integer>res){
        TreeNode curr = node.left;
        while (curr != null){
            if(!isLeaf(curr)){
                res.add(curr.data);
            }
            if(curr.left != null){
                curr = curr.left;
            }else{
                curr = curr.right;
            }
        }
    }

    private static void addRightBoundary(TreeNode node,List<Integer> res){
        TreeNode curr = node.right;
        List<Integer> temp = new ArrayList<>();
        while (curr != null){
            if(!isLeaf(curr)){
                temp.add(curr.data);
            }
            if(curr.right != null){
                curr = curr.right;
            }else{
                curr = curr.left;
            }
        }
        for(int i=temp.size()-1;i>=0;i--){
            res.add(temp.get(i));
        }
    }

    private static void addLeaves(TreeNode node,List<Integer> res){
        if(isLeaf(node)){
            res.add(node.data);
            return;
        }
        if(node.left != null){
            addLeaves(node.left,res);
        }
        if(node.right != null){
            addLeaves(node.right,res);
        }
    }

    private static boolean isLeaf(TreeNode node){
        return node.left == null && node.right == null;
    }

    private static  List<List<Integer>> zigZag(TreeNode node){
        List<List<Integer>> res = new ArrayList<>();
        if(node == null){
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        boolean leToRi = true;
        while (!queue.isEmpty()){
            int size = queue.size();
            LinkedList<Integer> levels = new LinkedList<>();
            for(int i=0;i<size;i++){
                TreeNode node1 = queue.poll();
                if(leToRi){
                    levels.addLast(node1.data);
                }else{
                    levels.addFirst(node1.data);
                }
                if(node1.left != null){
                    queue.add(node1.left);
                }
                if(node1.right != null){
                    queue.add(node1.right);
                }

            }
            res.add(levels);
            leToRi = !leToRi;
        }
        return res;
    }

    private static boolean isIdentical(TreeNode node1, TreeNode node2){
        if(node1 == null && node2 == null){
            return true;
        }
        if(node1 == null || node2 == null){
            return false;
        }
        if(node1.data != node2.data){
            return false;
        }
        return isIdentical(node1.left,node2.left) &&
                isIdentical(node1.right , node2.right);
    }

    private static int maxPathSum1(TreeNode root, int[] maxi){
        return findMaxSum(root,maxi);
    }

    private static int findMaxSum(TreeNode node, int []maxi){
        if(node == null){
            return 0;
        }
        int leftMax = Math.max(0,findMaxSum(node.left,maxi));
        int rightMax = Math.max(0,findMaxSum(node.right,maxi));
        maxi[0] = Math.max(maxi[0],leftMax+rightMax+node.data);
        return node.data + Math.max(leftMax,rightMax);
    }

    private static int diameter2(TreeNode root,int [] diameter){
        heightDia(root,diameter);
        return diameter[0];
    }

    private static int heightDia(TreeNode root, int[] dia){
        if(root == null){
            return 0;
        }
        int lh = heightDia(root.left,dia);
        int rh = heightDia(root.right,dia);
        dia[0] = Math.max(dia[0],lh+rh);
        return 1 + Math.max(lh,rh);
    }

    private static void diameter1(TreeNode root,int [] diameter){
        if(root == null){
            return;
        }
        int lh = height(root.left);
        int rh = height(root.right);
        diameter[0] = Math.max(diameter[0], lh+rh);
        diameter1(root.left, diameter);
        diameter1(root.right, diameter);
    }

    private static int checkBalance1(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftHeight = checkBalance1(root.left);
        if(leftHeight == -1){
            return -1;
        }
        int rightHeight = checkBalance1(root.right);
        if(rightHeight == -1){
            return -1;
        }
        if(Math.abs(leftHeight - rightHeight) > 1){
            return -1;
        }
        return 1 + Math.max(leftHeight,rightHeight);
    }

    private static boolean checkBalance(TreeNode root){
        if(root == null){
            return true;
        }

        int lefth = height(root.left);
        int righth = height(root.right);

        if(Math.abs(lefth - righth) > 1){
            return false;
        }

        boolean left = checkBalance(root.left);
        boolean right = checkBalance(root.right);

        if(!left || !right){
            return false;
        }
        return true;

    }

    private static int height(TreeNode node){
        if(node == null){
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        return 1 + Math.max(left,right);
    }

    private static int maxHeightItr(TreeNode node){
        if(node == null){
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        int level = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode tempNode = queue.poll();
                if(tempNode.left != null){
                    queue.add(tempNode.left);
                }
                if(tempNode.right != null){
                    queue.add(tempNode.right);
                }
            }
            level++;
        }
        return level;
    }

    private static int maxHeightRec(TreeNode node){
        if(node == null){
            return 0;
        }
        int left = maxHeightRec(node.left);
        int right = maxHeightRec(node.right);
        return 1  + Math.max(left,right);
    }

    private static void InOrder(TreeNode root,List<Integer> ans){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()){
            if(node != null){
                stack.push(node);
                node = node.left;
            }else{
                node = stack.pop();
                ans.add(node.data);
                node = node.right;
            }
        }
    }

    private static List<List<Integer>> levelOrderIterative(TreeNode node,List<List<Integer>> ans){
        if(node == null){
            return ans;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()){
            List<Integer> temp = new ArrayList<>();
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode tempNode = queue.poll();
                temp.add(tempNode.data);
                if(tempNode.left != null){
                    queue.add(tempNode.left);
                }
                if(tempNode.right != null){
                    queue.add(tempNode.right);
                }
            }
            ans.add(temp);
        }
        return ans;
    }

    private static List<List<Integer>> levelOrderRec(TreeNode root,int level, List<List<Integer>> ans){
        traverseLevel(root,level,ans);
        return ans;

    }

    private static void traverseLevel(TreeNode root, int level, List<List<Integer>> ans){
        if(root == null){
            return;
        }
        if(level >= ans.size()){
            ans.add(new ArrayList<>());
        }
        ans.get(level).add(root.data);
        traverseLevel(root.left,level+1,ans);
        traverseLevel(root.right,level+1,ans);
    }

    private static void inOrder(TreeNode root, List<Integer> ans){
        if(root == null){
            return;
        }
        inOrder(root.left,ans);
        ans.add(root.data);
        inOrder(root.right,ans);
    }

    private static void postOrder(TreeNode root,List<Integer> ans){
        if(root == null){
            return;
        }
        postOrder(root.left,ans);
        postOrder(root.right,ans);
        ans.add(root.data);
    }

    private static void preOrder(TreeNode root,List<Integer> ans) {

        if(root == null){
            return;
        }

        ans.add(root.data);
        preOrder(root.left,ans);
        preOrder(root.right,ans);
    }
}
