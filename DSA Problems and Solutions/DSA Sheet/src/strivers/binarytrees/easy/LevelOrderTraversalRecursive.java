package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class LevelOrderTraversalRecursive {

    // Helper function to print elements of a list (just for display)
    private static void printList(List<Integer> list) {
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        /*
               Constructing the sample tree:
                       1
                     /   \
                    2     3
                   / \
                  4   5
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // Calling the recursive level-order traversal
        List<List<Integer>> result = levelOrderTraversalRecursively(root);

        System.out.println("Level Order Traversal (Recursive):");

        // Printing each level on a new line
        for (List<Integer> level : result) {
            printList(level);
        }
    }

    // ------------------------------------------------------------------------------
    //  APPROACH: DFS (Depth-First Search) + Level Tracking (Recursive Level-Order)
    // ------------------------------------------------------------------------------
    //
    //  ✔ Idea:
    //      - Instead of using a queue like BFS, we use DFS recursion.
    //      - During recursion, we keep track of the current level (depth).
    //      - Each node is added into result[level].
    //
    //  ✔ Why it works:
    //      BFS naturally groups nodes by level.
    //      DFS does not, but if we *manually track levels*, we can replicate BFS output.
    //
    //  ✔ Example:
    //      Node 1 → level 0
    //      Node 2 → level 1
    //      Node 4 → level 2
    //
    //  ✔ Good Use Cases:
    //      - When a recursive solution is explicitly requested
    //      - When generating left/right view
    //      - When you want depth-based grouping without a queue
    //
    //  ✔ Drawbacks:
    //      - Not true BFS (it is DFS pretending to be BFS)
    //      - Recursion depth may overflow for skewed trees
    //      - Harder to convert to zig-zag, reverse, spiral traversal
    //
    // ------------------------------------------------------------------------------

    private static List<List<Integer>> levelOrderTraversalRecursively(TreeNode root) {

        // This list will store values level-wise:
        // result[0] → values at level 0
        // result[1] → values at level 1
        List<List<Integer>> result = new LinkedList<>();

        // Begin DFS traversal from level 0
        traverseLevel(root, 0, result);

        return result;
    }
    private static void traverseLevel(TreeNode node, int level, List<List<Integer>> result) {

        // Base Case:
        // If node is null, there is nothing to process.
        if (node == null) {
            return;
        }

        // If this level is being visited for the first time,
        // create a new list for this level.
        if (level >= result.size()) {
            result.add(new LinkedList<>());
        }

        // Add current node's value to its designated level list
        result.get(level).add(node.val);

        // Recursively visit left child:
        // Children are always one level deeper than parent.
        traverseLevel(node.left, level + 1, result);

        // Recursively visit right child:
        traverseLevel(node.right, level + 1, result);
    }
}
