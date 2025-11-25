package com.questions.strivers.binarytrees.easy;

import com.questions.strivers.binarytrees.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class LevelOrderTraversalRecursive {

    public static void printList(List<Integer> list) {
        // Helper function to print elements in a list.
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Creating a sample binary tree
        /*
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

        // Recursive level order traversal
        List<List<Integer>> result = levelOrderTraversalRecursively(root);

        System.out.println("Level Order Traversal (Recursive):");

        for (List<Integer> level : result) {
            printList(level);
        }
    }


    // ---------------------------------------------------------------------------
//  APPROACH 1: DFS RECURSIVE LEVEL ORDER TRAVERSAL (DEPTH-BASED GROUPING)
// ---------------------------------------------------------------------------
//
//  ✔ Approach Name:
//      DFS (Depth-First Search) with Level Tracking
//
//  ✔ Core Idea:
//      - We do NOT use a queue (so this is not BFS).
//      - Instead, we perform DFS (root → left → right).
//      - While traversing, we pass the current depth (level) as a parameter.
//      - Every node is inserted into result[level] list.
//      - This produces the same structure as BFS level order.
//
//  ✔ Why this works:
//      - BFS groups nodes by their levels.
//      - DFS can also group by level **if we explicitly track the level**.
//      - Example:
//            Node 1 → level 0
//            Node 2 → level 1
//            Node 3 → level 1
//            Node 4 → level 2
//      - So DFS + "level" parameter = BFS output style.
//
//  ✔ When to use this approach:
//      - When interviewer specifically asks for:
//          • Recursive solution
//          • Level order without using a queue
//      - When recursion feels more natural than iterative BFS.
//      - When building other tree views like:
//          • Left view
//          • Right view
//          • Root-to-leaf path collection
//
//  ✔ Time Complexity: O(N)
//      - Each node is visited exactly ONCE.
//
//  ✔ Space Complexity:
//      - Result list: O(N)
//      - Recursion stack: O(H)
//          • H = height of tree
//          • Balanced tree → H = log N
//          • Skewed tree → H = N (worst case)
//
//  ✔ Drawbacks / Limitations:
//      - Not a “true” BFS (queue-based) → it is DFS disguised as BFS.
//      - Recursion depth may overflow for skewed trees.
//      - Slightly harder to modify for:
//          • zig-zag
//          • reverse level order
//          • spiral traversal
//        compared to queue-based BFS.
//
// ---------------------------------------------------------------------------
//  CODE
// ---------------------------------------------------------------------------

    public static List<List<Integer>> levelOrderTraversalRecursively(TreeNode root) {

        // This list will store multiple lists:
        // result[0] → values at level 0
        // result[1] → values at level 1
        // result[2] → values at level 2
        List<List<Integer>> result = new LinkedList<>();

        // Start DFS traversal from level = 0
        traverseLevel(root, 0, result);

        return result;
    }

    private static void traverseLevel(TreeNode node, int level, List<List<Integer>> result) {

        // Base case:
        // When recursion reaches a null child, simply stop.
        if (node == null) {
            return;
        }

        // IMPORTANT LOGIC:
        // If this is the FIRST time visiting this "level",
        // then result does NOT have a list for that level yet.
        //
        // Example:
        // Visiting level 0 → result.size() = 0 → need new list
        // Visiting level 1 → result.size() = 1 → need new list
        //
        // This ensures:
        // result.get(level) is ALWAYS SAFE.
        if (level >= result.size()) {
            result.add(new LinkedList<>());
        }

        // Add the current node's value into the list of its level.
        //
        // Example:
        // For tree:
        //       1
        //     /   \
        //    2     3
        //
        // Level grouping becomes:
        // result[0] → [1]
        // result[1] → [2, 3]
        result.get(level).add(node.val);

        // Recursive DFS calls:
        // We pass (level + 1) for child nodes because
        // children are always one level deeper than parent.

        // Traverse left subtree
        traverseLevel(node.left, level + 1, result);

        // Traverse right subtree
        traverseLevel(node.right, level + 1, result);
    }

}
