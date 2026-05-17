package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.*;

/**
 * ---------------------------------------------------------------
 *                        PROBLEM STATEMENT
 * ---------------------------------------------------------------
 * Print the **Top View** of a binary tree.
 *
 * The **Top View** consists of all nodes that are visible when the
 * binary tree is viewed from the top.
 *
 * Rules:
 * 1. Each node is assigned a vertical line index (HD – Horizontal Distance):
 *        - Root has HD = 0
 *        - Left child  → HD - 1
 *        - Right child → HD + 1
 *
 * 2. While doing BFS (level-order traversal):
 *        - The first node encountered for any vertical line (HD)
 *          is the TOP VIEW node for that HD.
 *
 * 3. The output should be sorted from leftmost vertical line to rightmost.
 * ---------------------------------------------------------------
 */
public class TopView {

    public static void main(String[] args) {

        // Construct the sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(10);

        // Perform Top View traversal
        List<Integer> result = topView(root);

        // Output results
        System.out.print("Top View Traversal: ");
        for (int val : result) {
            System.out.print(val + " ");
        }
    }

    /**
     * Computes the Top View of the binary tree using BFS + Pair class.
     */
    public static List<Integer> topView(TreeNode root) {

        // Final answer list
        List<Integer> ans = new ArrayList<>();

        // Edge case: empty tree
        if (root == null) {
            return ans;
        }

        // TreeMap stores HD → Node value in sorted order
        Map<Integer, Integer> mpp = new TreeMap<>();

        // Queue will store {TreeNode, horizontal distance} using Pair
        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();

        // Start BFS with root at HD = 0
        q.add(new Pair<>(root, 0));

        // BFS traversal
        while (!q.isEmpty()) {

            // Get current pair from queue
            Pair<TreeNode, Integer> pair = q.poll();

            TreeNode node = pair.getKey();
            int hd = pair.getValue();  // Horizontal Distance

            // If first time visiting this HD → add to map
            if (!mpp.containsKey(hd)) {
                mpp.put(hd, node.val);
            }

            // Add left child with HD - 1
            if (node.left != null) {
                q.add(new Pair<>(node.left, hd - 1));
            }

            // Add right child with HD + 1
            if (node.right != null) {
                q.add(new Pair<>(node.right, hd + 1));
            }
        }

        // Add values in sorted HD order
        ans.addAll(mpp.values());

        return ans;
    }
}


//package com.questions.strivers.binarytrees.medium;
//
//import com.questions.strivers.binarytrees.TreeNode;
//import java.util.*;
//
///**
// * ---------------------------------------------------------------
// *                        PROBLEM STATEMENT
// * ---------------------------------------------------------------
// * Print the **Top View** of a binary tree.
// *
// * The **Top View** consists of all nodes that are visible when the
// * binary tree is viewed from the top.
// *
// * Rules:
// * 1. Each node is assigned a vertical line index (HD – Horizontal Distance):
// *        - Root has HD = 0
// *        - Left child  → HD - 1
// *        - Right child → HD + 1
// *
// * 2. While doing BFS (level-order traversal):
// *        - The first node encountered for any vertical line (HD)
// *          is the TOP VIEW node for that HD.
// *
// * 3. The output should be sorted from leftmost vertical line to rightmost.
// *
// * ---------------------------------------------------------------
// * Example Tree:
// *
// *                  1
// *               /     \
// *              2       3
// *            /   \    /  \
// *           4    10  9   10
// *            \
// *             5
// *              \
// *               6
// *
// * Top View Output:
// * [4, 2, 1, 3, 10]
// *
// * ---------------------------------------------------------------
// * Why BFS?
// * ---------------------------------------------------------------
// * BFS processes nodes level-by-level, ensuring that:
// *   → The first time you reach a vertical line (HD), that node is
// *     the topmost node for that HD.
// *
// * DFS cannot guarantee this because deeper nodes may be visited first.
// *
// * ---------------------------------------------------------------
// */
//
//public class TopView {
//
//    public static void main(String[] args) {
//
//        // Construct the sample binary tree
//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.left.left = new TreeNode(4);
//        root.left.right = new TreeNode(10);
//        root.left.left.right = new TreeNode(5);
//        root.left.left.right.right = new TreeNode(6);
//        root.right = new TreeNode(3);
//        root.right.left = new TreeNode(9);
//        root.right.right = new TreeNode(10);
//
//        // Perform Top View traversal
//        List<Integer> result = topView(root);
//
//        // Output results
//        System.out.print("Top View Traversal: ");
//        for (int val : result) {
//            System.out.print(val + " ");
//        }
//    }
//
//    /**
//     * Computes the Top View of the binary tree.
//     *
//     * @param root The root of the binary tree
//     * @return A list of nodes visible from the top
//     *
//     * ---------------------------------------------------------------
//     * APPROACH:
//     * ---------------------------------------------------------------
//     * - Use BFS (queue) to traverse the tree level-by-level
//     * - Maintain a TreeMap<HD, NodeValue>
//     *        where HD (Horizontal Distance) acts as vertical index.
//     *
//     * - For each node:
//     *        If HD is NOT already present in map, insert it.
//     *        This ensures the first encountered node at that HD is stored.
//     *
//     * - Insert left child → HD - 1
//     * - Insert right child → HD + 1
//     *
//     * - At the end, simply read TreeMap values in sorted HD order.
//     */
//    public static List<Integer> topView(TreeNode root) {
//
//        // Final answer list
//        List<Integer> ans = new ArrayList<>();
//
//        // Edge case: If tree is empty
//        if (root == null) {
//            return ans;
//        }
//
//        // TreeMap ensures horizontal distances (HD) are sorted
//        Map<Integer, Integer> mpp = new TreeMap<>();
//
//        // Queue for BFS → stores {node, horizontal_distance}
//        Queue<AbstractMap.SimpleEntry<TreeNode, Integer>> q = new LinkedList<>();
//
//        // Start BFS with root at HD = 0
//        q.add(new AbstractMap.SimpleEntry<>(root, 0));
//
//        // BFS traversal
//        while (!q.isEmpty()) {
//
//            // Fetch current element
//            AbstractMap.SimpleEntry<TreeNode, Integer> entry = q.poll();
//
//            TreeNode node = entry.getKey();
//            int hd = entry.getValue();  // Horizontal Distance
//
//            // Store node value only if this HD is visited for the FIRST TIME
//            if (!mpp.containsKey(hd)) {
//                mpp.put(hd, node.val);
//            }
//
//            // Push left child with HD - 1
//            if (node.left != null) {
//                q.add(new AbstractMap.SimpleEntry<>(node.left, hd - 1));
//            }
//
//            // Push right child with HD + 1
//            if (node.right != null) {
//                q.add(new AbstractMap.SimpleEntry<>(node.right, hd + 1));
//            }
//        }
//
//        // Extract values from TreeMap (sorted by HD)
//        ans.addAll(mpp.values());
//
//        return ans;
//    }
//}
