package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.*;

public class ZigZagLevel {
    public static void main(String[] args) {

        // Creating a sample binary tree for testing
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // Running the BEST approach
        List<List<Integer>> result = zigZagLevelOrder(root);
        System.out.println(result);
    }


    /* ============================================================================================
       APPROACH 5 (BEST APPROACH)
       Single Queue + Double-Ended Level List (Deque Behavior Using LinkedList)

       ✔ Uses normal BFS queue
       ✔ Uses a LinkedList for each level (acts like a deque)
       ✔ Add elements at front/back depending on direction

       WHY BEST?
       - No reversing needed
       - No use of two stacks
       - O(1) add at front / back using LinkedList
       - Most clean, efficient, and easy to understand

       TIME COMPLEXITY:
           O(N) — every node is processed once

       SPACE COMPLEXITY:
           O(N) — queue + ans list

       DRAWBACKS:
           None major. This is the optimal balance of simplicity + efficiency.
     ============================================================================================ */
    public static List<List<Integer>> zigZagLevelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> nodesQueue = new LinkedList<>();
        nodesQueue.offer(root);

        boolean leftToRight = true; // toggle direction each level

        while (!nodesQueue.isEmpty()) {

            int size = nodesQueue.size();
            // LinkedList allows addFirst() and addLast() in O(1)
            LinkedList<Integer> row = new LinkedList<>();

            for (int i = 0; i < size; i++) {

                TreeNode node = nodesQueue.poll();

                // Based on direction, add at appropriate end
                if (leftToRight) {
                    row.addLast(node.val);
                } else {
                    row.addFirst(node.val);
                }

                // Add children normally
                if (node.left != null) nodesQueue.offer(node.left);
                if (node.right != null) nodesQueue.offer(node.right);
            }

            result.add(row);
            leftToRight = !leftToRight; // flip direction
        }
        return result;
    }


    /* ============================================================================================
       APPROACH 4
       Recursive Spiral Traversal (Height + Direction)

       ✔ Recursively prints each level
       ✔ Requires computing height
       ✔ For each level: perform DFS to visit nodes in proper order

       TIME COMPLEXITY:
           O(N * H) worst case
           - Computing height = O(N)
           - Printing each level = O(N) but repeated H times

           Worst-case (skewed tree):
               H = N → O(N^2)

       SPACE COMPLEXITY:
           O(H) recursion stack

       DRAWBACKS:
           - Very slow on skewed trees
           - Not preferred in interviews
           - Lots of repeated work
     ============================================================================================ */
    public static List<List<Integer>> spiralRecursive(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        int h = height(root);
        boolean leftToRight = false;

        for (int level = 1; level <= h; level++) {
            List<Integer> currentLevel = new ArrayList<>();
            printLevel(root, level, leftToRight, currentLevel);
            ans.add(currentLevel);
            leftToRight = !leftToRight;
        }
        return ans;
    }

    private static void printLevel(TreeNode node, int level, boolean leftToRight, List<Integer> list) {
        if (node == null) return;

        if (level == 1) {
            list.add(node.val);
        } else {
            if (leftToRight) {
                printLevel(node.left, level - 1, leftToRight, list);
                printLevel(node.right, level - 1, leftToRight, list);
            } else {
                printLevel(node.right, level - 1, leftToRight, list);
                printLevel(node.left, level - 1, leftToRight, list);
            }
        }
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }


    /* ============================================================================================
       APPROACH 3
       Using Double Ended Queue (Deque)

       ✔ Poll from front or back based on direction
       ✔ Add children also at correct ends

       TIME COMPLEXITY:
           O(N)

       SPACE COMPLEXITY:
           O(N)

       DRAWBACKS:
           - Slightly more complex logic
           - Need careful handling of adding children at correct ends
     ============================================================================================ */
    public static List<List<Integer>> spiralUsingDeque(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Deque<TreeNode> dq = new LinkedList<>();
        dq.offer(root);

        boolean leftToRight = true;

        while (!dq.isEmpty()) {

            int size = dq.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {

                if (leftToRight) {
                    TreeNode node = dq.pollFirst();
                    level.add(node.val);
                    if (node.left != null) dq.offerLast(node.left);
                    if (node.right != null) dq.offerLast(node.right);
                } else {
                    TreeNode node = dq.pollLast();
                    level.add(node.val);
                    if (node.right != null) dq.offerFirst(node.right);
                    if (node.left != null) dq.offerFirst(node.left);
                }
            }

            ans.add(level);
            leftToRight = !leftToRight;
        }

        return ans;
    }


    /* ============================================================================================
       APPROACH 2
       Two Stacks Approach (Striver’s recommended alternative)

       ✔ s1 → left to right
       ✔ s2 → right to left
       ✔ No reversing required
       ✔ Classic zig-zag approach

       TIME COMPLEXITY:
           O(N)

       SPACE COMPLEXITY:
           O(N)

       DRAWBACKS:
           - Requires understanding of two-stack flip logic
           - Slightly more verbose
     ============================================================================================ */
    public static List<List<Integer>> spiralUsingTwoStacks(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Stack<TreeNode> s1 = new Stack<>(); // left → right
        Stack<TreeNode> s2 = new Stack<>(); // right → left

        s1.push(root);

        while (!s1.isEmpty() || !s2.isEmpty()) {

            List<Integer> level = new ArrayList<>();

            // Process left → right
            while (!s1.isEmpty()) {
                TreeNode node = s1.pop();
                level.add(node.val);

                if (node.left != null) s2.push(node.left);
                if (node.right != null) s2.push(node.right);
            }

            if (!level.isEmpty()) ans.add(level);

            level = new ArrayList<>();

            // Process right → left
            while (!s2.isEmpty()) {
                TreeNode node = s2.pop();
                level.add(node.val);

                if (node.right != null) s1.push(node.right);
                if (node.left != null) s1.push(node.left);
            }

            if (!level.isEmpty()) ans.add(level);
        }

        return ans;
    }


    /* ============================================================================================
       APPROACH 1
       BFS + Reverse Alternate Levels

       ✔ Normal level order traversal
       ✔ Reverse the list on alternate levels (Collections.reverse)

       TIME COMPLEXITY:
           O(N) + O(N) reversing = O(N)
       SPACE COMPLEXITY:
           O(N)

       DRAWBACKS:
           - Reversing each alternate level costs extra time
           - Not as optimal as deque-based method
           - Uses additional operations
     ============================================================================================ */
    public static List<List<Integer>> spiralLevelOrder(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        boolean reverse = false;

        while (!queue.isEmpty()) {

            int size = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (reverse) Collections.reverse(level);

            ans.add(level);
            reverse = !reverse;
        }

        return ans;
    }

}
