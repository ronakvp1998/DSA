package strivers.binarytrees.medium;

import strivers.binarytrees.TreeNode;

/**
 * ------------------------------------------------------------
 * 🧩 PROBLEM: Diameter of a Binary Tree
 * ------------------------------------------------------------
 * The *diameter of a binary tree* is defined as:
 *
 *      ➜ The longest path between ANY two nodes in the tree.
 *
 * IMPORTANT:
 * - The path does NOT have to pass through the root.
 * - The path is measured in *number of edges*, not nodes.
 *
 * Example diameter path:
 *      nodeA → ... → LCA → ... → nodeB
 *
 * The goal is to compute this longest path efficiently.
 *
 * ------------------------------------------------------------
 * ❗ WHY THIS PROBLEM IS ASKED IN INTERVIEWS?
 * ------------------------------------------------------------
 * - Tests tree recursion understanding
 * - Tests ability to compute two dependent values in one DFS
 * - Tests ability to optimize from O(N²) → O(N)
 *
 */
public class DiameterBT {

    public static void main(String[] args) {

        /*
         * Constructing a sample Binary Tree
         * (Note: Your diagram in the original comment missed node 2's placement)
         *
         *                  1
         *                /   \
         *               2     3
         *                    /   \
         *                   4     7
         *                  /       \
         *                 5         8
         *                /           \
         *               6             9
         */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.left.left = new TreeNode(5);
        root.right.left.left.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(8);
        root.right.right.right.right = new TreeNode(9);

        int diameter[] = new int[1];   // Stores global diameter (Java doesn't allow returning 2 values)

        findDiameter(root, diameter);  // Compute diameter
        System.out.println("Diameter of Binary Tree: " + diameter[0]);
    }


    // ============================================================
    // ⭐ APPROACH 2 — OPTIMAL O(N) (Recommended)
    // ============================================================

    /**
     * Computes the diameter using a SINGLE PASS DFS.
     *
     * Why optimal?
     * - Height and diameter are computed in the same recursion.
     * - Each node is processed exactly once.
     *
     * @param root The root of the binary tree
     * @param diameter Array of size 1 used to store global max diameter
     * @return The diameter of the tree (for convenience)
     */
    private static int findDiameter(TreeNode root, int[] diameter) {
        // Height function ALSO updates 'diameter'
        computeHeight(root, diameter);
        return diameter[0];
    }

    /**
     * Returns height of current subtree while updating diameter.
     *
     * --------------------------------------------
     * HOW THIS WORKS (VERY IMPORTANT):
     * --------------------------------------------
     * For each node:
     *      leftHeight  = height of left subtree
     *      rightHeight = height of right subtree
     *
     * A possible diameter passes THROUGH this node:
     *
     *          longest path = leftHeight + rightHeight
     *
     * We track the maximum of these values.
     *
     * Then return:
     *
     *          height = 1 + max(leftHeight, rightHeight)
     *
     * --------------------------------------------
     * EDGE CASES:
     * --------------------------------------------
     * - If node is null → height = 0
     * - Diameter remains unaffected
     *
     * @param node Current tree node
     * @param diameter Array storing max diameter
     * @return Height of subtree rooted at 'node'
     */
    private static int computeHeight(TreeNode node, int[] diameter) {
        if (node == null)
            return 0;   // Base case → empty subtree height is 0

        // Recursively compute heights of subtrees
        int leftHeight = computeHeight(node.left, diameter);
        int rightHeight = computeHeight(node.right, diameter);

        // Update global diameter: longest path through this node
        diameter[0] = Math.max(diameter[0], leftHeight + rightHeight);

        // Return height of current subtree
        return 1 + Math.max(leftHeight, rightHeight);
    }


    // ============================================================
    // ❌ APPROACH 1 — BRUTE FORCE O(N²) (NOT RECOMMENDED)
    // ============================================================

    /**
     * Brute Force:
     *
     * For every node:
     *      - compute left height   O(N)
     *      - compute right height  O(N)
     *      - recursively check both subtrees
     *
     * Too many repeated computations → O(N²)
     *
     * @param node Current node
     * @param diameter Stores max diameter
     */
    private static void findDiameterBrute(TreeNode node, int[] diameter) {
        if (node == null)
            return;

        // Compute heights again and again → expensive
        int leftHeight = heightBrute(node.left);
        int rightHeight = heightBrute(node.right);

        // Update diameter
        diameter[0] = Math.max(diameter[0], leftHeight + rightHeight);

        // Explore rest of the tree
        findDiameterBrute(node.left, diameter);
        findDiameterBrute(node.right, diameter);
    }

    /**
     * Computes height in O(N)
     * Called for every node → leads to O(N²)
     */
    private static int heightBrute(TreeNode node) {
        if (node == null)
            return 0;

        return 1 + Math.max(heightBrute(node.left), heightBrute(node.right));
    }


    // ============================================================
    // 📌 TIME & SPACE COMPLEXITY ANALYSIS
    // ============================================================

    /**
     * ⭐ Optimal Approach (Single DFS)
     * Time Complexity  : O(N)
     *      - Every node is visited exactly once.
     *
     * Space Complexity : O(H)
     *      - H = height of tree
     *      - Balanced tree → O(log N)
     *      - Skewed tree   → O(N)
     *
     * ❌ Brute Force Approach
     * Time Complexity  : O(N²)
     *      - For every node, height (O(N)) is recomputed
     *
     * Space Complexity : O(H)
     */

    // ============================================================
    // 🔁 ALTERNATIVE APPROACHES (INTERVIEW DISCUSSION)
    // ============================================================

    /*
        1️⃣ Using Pair (height, diameter) return type
            - Cleaner and avoids array wrapper
            - Java requires creating custom Pair class

        2️⃣ Using a global static variable
            - Works but discouraged for interviews & multi-threaded environments

        3️⃣ Iterative DFS version
            - Possible but overly complex and less readable
            - Recursion is preferred for tree height-based problems

        ✔ Recommended approach for interviews:
            → Single DFS computing height + diameter (used above)
    */
}
