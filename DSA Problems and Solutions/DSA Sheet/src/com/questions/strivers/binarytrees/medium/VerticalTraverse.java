package com.questions.strivers.binarytrees.medium;

import com.questions.strivers.binarytrees.TreeNode;
import java.util.*;

/**
 * ---------------------------------------------------------------
 *                     PROBLEM STATEMENT
 * ---------------------------------------------------------------
 * Perform a **Vertical Order Traversal** of a Binary Tree.
 *
 * Every node in the tree is placed on a 2D grid:
 *
 *    - The root is at coordinate (x = 0, y = 0)
 *    - Left child  → (x - 1, y + 1)
 *    - Right child → (x + 1, y + 1)
 *
 * Rules for Vertical Order Traversal:
 *
 * 1. Output should be grouped column-by-column from leftmost x to rightmost x.
 * 2. For nodes that lie in the same (x, y):
 *       → They must be sorted by their value (ascending).
 * 3. For nodes in the same x but different y:
 *       → Higher nodes (smaller y) must appear first.
 *
 * Final output format:
 *     List<List<Integer>>
 * where each inner list represents one vertical column.
 *
 * ---------------------------------------------------------------
 * Example Tree from main():
 *
 *                  1
 *               /    \
 *              2      3
 *            /  \    /  \
 *           4    10 9    10
 *            \
 *             5
 *              \
 *               6
 *
 * Expected Vertical Order:
 * [
 *   [4],               // x = -2
 *   [2, 5, 6],         // x = -1
 *   [1, 9, 10],        // x = 0
 *   [3],               // x = +1
 *   [10]               // x = +2
 * ]
 *
 * ---------------------------------------------------------------
 * APPROACH SUMMARY (BFS + TreeMap + PriorityQueue)
 * ---------------------------------------------------------------
 * We use:
 *
 *  → BFS traversal to correctly process nodes level by level (y-coordinate)
 *  → TreeMap<X, TreeMap<Y, Min-Heap>> to maintain sorting order:
 *
 *        X-axis = vertical index (column)
 *        Y-axis = level depth
 *        Min-heap ensures multiple nodes at same (x, y) are sorted
 *
 * Steps:
 * 1. Push root at (0,0) into BFS queue.
 * 2. For each node:
 *        store it in nodes[x][y] min-heap.
 *        push children to queue with updated (x,y).
 * 3. Extract results by sorted X, then sorted Y, then PQ sorted values.
 *
 * ---------------------------------------------------------------
 * WHY BFS?
 * ---------------------------------------------------------------
 * BFS ensures:
 *  - We discover nodes in increasing order of Y (top → bottom)
 *  - Coordinates (x,y) assigned accurately level-wise
 *
 * ---------------------------------------------------------------
 */

public class VerticalTraverse {

    /**
     * Performs Vertical Order Traversal using:
     *
     * TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>>
     *    verticals       levels          all the nodes in that level
     *
     * Why this structure?
     *  - TreeMap keeps keys sorted → maintains vertical order (x-axis)
     *  - Inner TreeMap keeps y-level sorted
     *  - PriorityQueue ensures values at same (x,y) are sorted
     */

    static class Pair{
        TreeNode node;
        int vertical;
        int level;

        Pair(TreeNode _node, int _vertical, int _level) {
            node = _node;
            vertical = _vertical;
            level = _level;
        }
    }

    private static List<List<Integer>> findVertical(TreeNode root) {

        // Edge case: Empty tree → return empty list
        if (root == null) {
            return new ArrayList<>();
        }

        // TreeMap stores:
        //   x → (y → min-heap of node values)
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> nodes = new TreeMap<>();

        // Queue used for BFS traversal
        // Stores Pair(node, x-axis, y-axis)
        Queue<Pair> todo = new LinkedList<>();

        // Start BFS with root at coordinate (0,0)
        todo.offer(new Pair(root, 0, 0));

        // BFS ensures correct vertical & level-order mapping
        while (!todo.isEmpty()) {

            Pair p = todo.poll();
            TreeNode temp = p.node;

            int x = p.vertical;  // vertical index (column)
            int y = p.level;     // depth level (row)

            // STEP 1: Insert into map structure
            nodes.putIfAbsent(x, new TreeMap<>());
            nodes.get(x).putIfAbsent(y, new PriorityQueue<>());

            // Insert node value → PQ maintains ascending order for same (x,y)
            nodes.get(x).get(y).offer(temp.val);

            // STEP 2: Add left child to BFS if exists
            if (temp.left != null) {
                todo.offer(new Pair(temp.left, x - 1, y + 1));
            }

            // STEP 3: Add right child to BFS if exists
            if (temp.right != null) {
                todo.offer(new Pair(temp.right, x + 1, y + 1));
            }
        }

        // Prepare final result in correct sorted order
        List<List<Integer>> ans = new ArrayList<>();

        // Traverse x-axis from leftmost to rightmost
        for (TreeMap<Integer, PriorityQueue<Integer>> ys : nodes.values()) {

            List<Integer> col = new ArrayList<>();

            // Traverse y-axis (level order)
            for (PriorityQueue<Integer> pq : ys.values()) {

                // Extract sorted values from PQ
                while (!pq.isEmpty()) {
                    col.add(pq.poll());
                }
            }

            ans.add(col); // Add complete vertical column
        }

        return ans;
    }


    /** Utility function to print final result visually */
    private static void printResult(List<List<Integer>> result) {
        for (List<Integer> level : result) {
            for (int val : level) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }


    /**
     * MAIN METHOD
     * Builds the example binary tree and runs vertical traversal
     */
    public static void main(String[] args) {

        // Construct example tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(10);

        // Perform vertical traversal
        List<List<Integer>> verticalTraversal = findVertical(root);

        // Print result
        System.out.println("Vertical Traversal:");
        printResult(verticalTraversal);
    }
}


/**
 * Helper Pair class to store:
 *   - TreeNode reference
 *   - X coordinate (vertical)
 *   - Y coordinate (level)
 */
//class Pair {
//    TreeNode node;
//    int vertical;
//    int level;
//
//    Pair(TreeNode _node, int _vertical, int _level) {
//        node = _node;
//        vertical = _vertical;
//        level = _level;
//    }
//}
