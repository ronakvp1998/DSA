package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.*;

/**
 * =============================================================================================
 * 🔥 PROBLEM: Zig-Zag / Spiral Level Order Traversal of a Binary Tree
 * =============================================================================================
 *
 * Given the root of a binary tree, return its zig-zag (also called spiral) level order traversal.
 *
 * Zig-Zag Traversal Pattern:
 *     • Level 0 → Left to Right
 *     • Level 1 → Right to Left
 *     • Level 2 → Left to Right
 *     • Level 3 → Right to Left
 *     … and so on.
 *
 * Example:
 *      Input Binary Tree:
 *                 1
 *               /   \
 *              2     3
 *             / \   / \
 *            4   5 6   7
 *
 *      Zig-Zag Output:
 *          [[1], [3, 2], [4, 5, 6, 7]]
 *
 * ---------------------------------------------------------------------------------------------
 * ❗ Why this problem is asked in interviews?
 * ---------------------------------------------------------------------------------------------
 * • Tests understanding of BFS traversal
 * • Tests ability to modify level order to match constraints
 * • Checks comfort with data structures: Queue, Stack, Deque, LinkedList
 * • Multiple optimal approaches → candidates must pick the cleanest one
 *
 * ---------------------------------------------------------------------------------------------
 * =============================================================================================
 */

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

    // ==========================================================================================================
    // ✅ APPROACH 1 (BEST)
    // Queue + LinkedList (Deque Behavior)
    // ==========================================================================================================

    /**
     * =============================================================================================
     * ✔ BEST APPROACH — BFS using Queue + LinkedList (Deque behavior per level)
     * =============================================================================================
     *
     * IDEA:
     * -----
     * • Perform normal BFS using a Queue.
     * • For each level, use a LinkedList to insert nodes either:
     *          → at the end  (left-to-right)
     *          → at the front (right-to-left)
     *
     * WHY LINKEDLIST?
     * ---------------
     * • addFirst() and addLast() take O(1)
     * • Perfect for zig-zag insertion
     *
     * WHY THIS IS BEST?
     * -----------------
     * • No reversing required
     * • No two-stacks complexity
     * • Very easy to follow
     * • Most optimized & recommended in interviews
     *
     * TIME COMPLEXITY:  O(N)   → Every node is processed exactly once
     * SPACE COMPLEXITY: O(N)   → Queue + output structure
     * =============================================================================================
     */
    private static List<List<Integer>> zigZagLevelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();

        // Edge case: empty tree
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        boolean leftToRight = true;  // Direction flag that flips every level

        while (!queue.isEmpty()) {

            int size = queue.size();
            // LinkedList allows O(1) push at both ends → perfect for zig-zag
            LinkedList<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < size; i++) {

                // Pop from queue (BFS)
                TreeNode node = queue.poll();

                // Insert at correct position based on direction
                if (leftToRight) {
                    currentLevel.addLast(node.val);   // Normal L → R
                } else {
                    currentLevel.addFirst(node.val);  // Reverse R → L
                }

                // Push children normally (BFS order)
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);

            // Flip direction for next level
            leftToRight = !leftToRight;
        }

        return result;
    }


    // ==========================================================================================================
    // ✅ APPROACH 2 — Two Stacks (Classic Zig-Zag)
    // ==========================================================================================================

    /**
     * =============================================================================================
     * ✔ APPROACH 2 — Two Stacks (Classic Zig-Zag Traversal)
     * =============================================================================================
     *
     * IDEA:
     * -----
     * • Use two stacks:
     *      stack1 → left-to-right
     *      stack2 → right-to-left
     * • Pop from one stack, push children into the other.
     * • On next level, pop from the other stack.
     *
     * WHY IT WORKS?
     * -------------
     * • Stacks naturally reverse order
     * • Children are pushed in opposite order to maintain zig-zag behavior
     *
     * WHEN TO USE?
     * ------------
     * • Good approach when interviewer asks “how else can you do it?”
     * • Classic zig-zag implementation
     *
     * TIME COMPLEXITY:  O(N)   (Each node pushed/popped once)
     * SPACE COMPLEXITY: O(N)
     *
     * DRAWBACKS:
     * ----------
     * • Slightly harder to understand
     * • Not as clean as BEST approach
     * =============================================================================================
     */
    private static List<List<Integer>> spiralUsingTwoStacks(TreeNode root) {

        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Stack<TreeNode> s1 = new Stack<>(); // L → R
        Stack<TreeNode> s2 = new Stack<>(); // R → L

        s1.push(root);

        while (!s1.isEmpty() || !s2.isEmpty()) {

            List<Integer> level = new ArrayList<>();

            // Process stack1 → left-to-right
            while (!s1.isEmpty()) {
                TreeNode node = s1.pop();
                level.add(node.val);

                // Push in natural BFS order
                if (node.left != null) s2.push(node.left);
                if (node.right != null) s2.push(node.right);
            }
            if (!level.isEmpty()) ans.add(level);


            level = new ArrayList<>();

            // Process stack2 → right-to-left
            while (!s2.isEmpty()) {
                TreeNode node = s2.pop();
                level.add(node.val);

                // Push in REVERSE order (right first)
                if (node.right != null) s1.push(node.right);
                if (node.left != null) s1.push(node.left);
            }

            if (!level.isEmpty()) ans.add(level);
        }

        return ans;
    }


    // ==========================================================================================================
    // ✅ APPROACH 3 — BFS + Reverse Alternate Levels
    // ==========================================================================================================

    /**
     * =============================================================================================
     * ✔ APPROACH 3 — BFS + Reverse Alternate Levels
     * =============================================================================================
     *
     * IDEA:
     * -----
     * • Perform standard BFS (level-order).
     * • Store the nodes of each level in a list.
     * • Reverse the list for alternate levels using Collections.reverse().
     *
     * WHY USE THIS?
     * -------------
     * • Simplest to explain
     * • Best beginner-friendly approach
     * • Works well when clarity > performance
     *
     * DRAWBACK:
     * ---------
     * • Reversing list every alternate level costs extra time (still O(N))
     * • Not as optimal as Best Approach
     *
     * TIME COMPLEXITY:  O(N) + O(N) reversing = O(N)
     * SPACE COMPLEXITY: O(N)
     * =============================================================================================
     */
    private static List<List<Integer>> spiralLevelOrder(TreeNode root) {

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

                // Push children
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            // Reverse alternate levels
            if (reverse) {
                Collections.reverse(level);
            }

            ans.add(level);

            // Flip direction
            reverse = !reverse;
        }

        return ans;
    }

}
