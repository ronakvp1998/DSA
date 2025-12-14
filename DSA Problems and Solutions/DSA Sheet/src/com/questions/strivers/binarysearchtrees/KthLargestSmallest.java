package com.questions.strivers.binarysearchtrees;

import com.questions.strivers.binarytrees.TreeNode;

/**
 * =============================================================================
 * PROBLEM STATEMENT
 * =============================================================================
 * Given the root of a **Binary Search Tree (BST)** and an integer `k`,
 * find:
 * 1. The **k-th smallest element** in the BST
 * 2. The **k-th largest element** in the BST
 * <p>
 * -----------------------------------------------------------------------------
 * BST Property Reminder:
 * -----------------------------------------------------------------------------
 * - Inorder traversal of a BST produces nodes in **sorted (ascending) order**
 * - Reverse inorder traversal produces nodes in **descending order**
 * <p>
 * -----------------------------------------------------------------------------
 * Constraints & Notes:
 * -----------------------------------------------------------------------------
 * - 1 ≤ k ≤ number of nodes in BST
 * - Tree may be skewed or balanced
 * - Duplicates are assumed NOT to exist
 * =============================================================================
 */
public class KthLargestSmallest {

    public static void main(String[] args) {
        /*
         * Constructing the following BST:
         *
         *              8
         *            /   \
         *           3     10
         *          / \      \
         *         1   6      14
         *            / \     /
         *           4   7   13
         */

        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.right.right = new TreeNode(14);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(7);
        root.right.right.left = new TreeNode(13);

        int k = 3;

        KthLargestSmallest solver = new KthLargestSmallest();
        int[] result = solver.findKth(root, k);

        System.out.println("k = " + k);
        System.out.println("Kth Smallest Element: " + result[0]);
        System.out.println("Kth Largest Element: " + result[1]);
    }

    /* ==========================================================================
     *              REVERSE INORDER TRAVERSAL (Kth LARGEST)
     * ==========================================================================
     * Traversal Order: Right → Root → Left
     *
     * This visits nodes in descending order for a BST.
     * We count nodes as we traverse until we reach the k-th node.
     * ==========================================================================
     */

    private void reverseInOrder(TreeNode node, int[] counter, int k, int[] kLargest) {

        // Base case:
        // 1. Node is null
        // 2. We already found k-th largest (early stopping)
        if (node == null || counter[0] >= k) {
            return;
        }

        // Step 1: Traverse right subtree first (larger values)
        reverseInOrder(node.right, counter, k, kLargest);

        // Step 2: Visit current node
        counter[0]++;

        // If this is the k-th visited node, store answer
        if (counter[0] == k) {
            kLargest[0] = node.val;
            return; // Stop further traversal
        }

        // Step 3: Traverse left subtree
        reverseInOrder(node.left, counter, k, kLargest);
    }

    /* ==========================================================================
     *                  INORDER TRAVERSAL (Kth SMALLEST)
     * ==========================================================================
     * Traversal Order: Left → Root → Right
     *
     * This visits nodes in ascending order for a BST.
     * We count nodes until we reach the k-th smallest element.
     * ==========================================================================
     */
    private void inOrder(TreeNode node, int[] counter, int k, int[] kSmallest) {

        // Base case:
        // 1. Node is null
        // 2. We already found k-th smallest
        if (node == null || counter[0] >= k) {
            return;
        }

        // Step 1: Traverse left subtree (smaller values)
        inOrder(node.left, counter, k, kSmallest);

        // Step 2: Visit current node
        counter[0]++;

        // If this is the k-th visited node, store answer
        if (counter[0] == k) {
            kSmallest[0] = node.val;
            return; // Stop further traversal
        }

        // Step 3: Traverse right subtree
        inOrder(node.right, counter, k, kSmallest);
    }

    /* ==========================================================================
     *                          DRIVER FUNCTION
     * ==========================================================================
     * Finds both k-th smallest and k-th largest elements in a single class.
     *
     * @param root Root of BST
     * @param k    Target position
     * @return     Array containing {k-th smallest, k-th largest}
     * ==========================================================================
     */
    private int[] findKth(TreeNode root, int k) {

        // Default values (used only for safety)
        int[] kSmallest = new int[]{Integer.MAX_VALUE};
        int[] kLargest = new int[]{Integer.MIN_VALUE};

        // Counter wrapped in array to maintain state across recursion
        int[] counter = new int[]{0};

        // Find k-th smallest using inorder traversal
        inOrder(root, counter, k, kSmallest);

        // Reset counter for k-th largest
        counter[0] = 0;

        // Find k-th largest using reverse inorder traversal
        reverseInOrder(root, counter, k, kLargest);

        return new int[]{kSmallest[0], kLargest[0]};
    }

    /* =============================================================================
     *                      TIME & SPACE COMPLEXITY
     * =============================================================================
     * Let N = number of nodes, H = height of BST
     *
     * Time Complexity:
     * - Best Case:  O(k)
     * - Average:    O(H + k)
     * - Worst Case: O(N) (skewed BST)
     *
     * Space Complexity:
     * - O(H) due to recursion stack
     * =============================================================================
     */

    /* =============================================================================
     *                          APPROACH EXPLANATION
     * =============================================================================
     * 1. Use inorder traversal to find the k-th smallest element
     *    - BST inorder = sorted order
     *
     * 2. Use reverse inorder traversal to find the k-th largest element
     *    - Reverse inorder = descending order
     *
     * 3. Use a counter to track number of visited nodes
     * 4. Stop traversal early once k is reached (optimization)
     *
     * Why this works:
     * - BST property guarantees correct ordering
     * - No extra data structure needed
     * =============================================================================
     */

    /* =============================================================================
     *                      ALTERNATIVE APPROACHES
     * =============================================================================
     * 1. Iterative Inorder using Stack
     *    - Easier to control traversal
     *    - Uses O(H) space
     *
     * 2. Augmented BST (with subtree sizes)
     *    - Each node stores size of left subtree
     *    - Can find k-th element in O(log N)
     *    - More complex, used in advanced systems
     *
     * 3. Convert BST to Sorted Array
     *    - Simple but inefficient
     *    - Uses O(N) extra space
     * =============================================================================
     */


}

