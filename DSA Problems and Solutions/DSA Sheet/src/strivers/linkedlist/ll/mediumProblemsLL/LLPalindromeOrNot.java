package strivers.linkedlist.ll.mediumProblemsLL;

/**
 * ============================================================================
 * 234. Palindrome Linked List
 * ============================================================================
 *
 * Problem Statement:
 * Given the head of a singly linked list, return true if it is a palindrome
 * or false otherwise.
 *
 * Example 1:
 * Input: head = [1,2,2,1]
 * Output: true
 *
 * Example 2:
 * Input: head = [1,2]
 * Output: false
 *
 * Constraints:
 * - The number of nodes in the list is in the range [1, 10^5].
 * - 0 <= Node.val <= 9
 *
 * Follow up: Could you do it in O(n) time and O(1) space?
 *
 * ============================================================================
 * CONCEPTUAL VISUALIZATION (O(1) SPACE APPROACH)
 * ============================================================================
 * Example: [1, 2, 3, 2, 1]
 *
 * Step 1: Find the Middle (Tortoise and Hare)
 *   1  ->  2  ->  3  ->  2  ->  1  -> null
 *                 ^             ^
 *                Slow          Fast
 *   (Since length is odd, Fast is not null. We move Slow one more step to
 *    ignore the exact center element during comparison).
 *
 * Step 2: Reverse the Second Half
 *   List 1 (First Half):   1 -> 2 -> 3 -> 2 -> 1 ...
 *   List 2 (Reversed):     1 -> 2 -> null
 *
 * Step 3: Compare Pointers
 *   p1 at 1 (List 1), p2 at 1 (List 2) -> Match!
 *   p1 at 2 (List 1), p2 at 2 (List 2) -> Match!
 *   p2 reaches null. It is a Palindrome!
 *
 * Step 4: (Optional but Professional) Restore the List
 *   Reverse List 2 back to its original state so the input structure is
 *   not permanently mutated.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LLPalindromeOrNot {

    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * ============================================================================
     * Phase 1: Optimal Approach (Fast/Slow Pointers & In-Place Reversal)
     * ============================================================================
     * Detailed Intuition:
     * To achieve O(1) space, we cannot use a secondary data structure (like an
     * array or stack) to store values. Instead, we can divide the list in half.
     * 1. Use the Fast and Slow pointer technique to find the middle of the list.
     * 2. Reverse the second half of the linked list in place.
     * 3. Traverse the first half and the reversed second half simultaneously,
     *    comparing their values. If any pair differs, it's not a palindrome.
     * 4. Restore the reversed second half back to its original state (best practice
     *    in production environments).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   Finding the middle takes O(N/2). Reversing takes O(N/2). Comparing takes
     *   O(N/2). Restoring takes O(N/2). Overall time is strictly bounded by O(N).
     * - Space Complexity: O(1)
     *   We only allocate a few pointers (`slow`, `fast`, `prev`, etc.). No
     *   auxiliary stack space or heap space is utilized.
     * ============================================================================
     */
    public boolean isPalindromeOptimal(ListNode head) {
        if (head == null || head.next == null) return true;

        // Step 1: Find the middle using Fast & Slow pointers
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // If fast is not null, the list length is odd.
        // We skip the middle element by advancing slow.
        if (fast != null) {
            slow = slow.next;
        }

        // Step 2: Reverse the second half
        ListNode secondHalfHead = reverseList(slow);
        ListNode copyOfSecondHalfHead = secondHalfHead; // Save for restoring later

        // Step 3: Compare both halves
        ListNode p1 = head;
        ListNode p2 = secondHalfHead;
        boolean isPalindrome = true;

        while (isPalindrome && p2 != null) {
            if (p1.val != p2.val) {
                isPalindrome = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // Step 4: Restore the list structure
        // Not strictly required by LeetCode, but highly expected by interviewers
        reverseList(copyOfSecondHalfHead);

        return isPalindrome;
    }

    /** Helper method to reverse a singly linked list */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * ============================================================================
     * Phase 2: Brute Force Approach (Array Copy)
     * ============================================================================
     * Detailed Intuition:
     * The easiest way to check for a palindrome is to have bidirectional access
     * to the sequence. Singly linked lists only go forward. To fix this, we
     * iterate through the list and copy every value into an ArrayList.
     * Once in the array, we can use standard Two Pointers (start and end) moving
     * towards the center to verify symmetry.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   One pass to copy the nodes O(N), and one pass to compare the array O(N).
     * - Space Complexity: O(N) (Heap Space)
     *   We instantiate an ArrayList that stores all N elements of the linked list
     *   in memory (Heap).
     * ============================================================================
     */
    public boolean isPalindromeBruteForce(ListNode head) {
        List<Integer> values = new ArrayList<>();

        // Copy linked list values to ArrayList
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // Two-pointer check
        int left = 0;
        int right = values.size() - 1;
        while (left < right) {
            // Must use .equals() or unboxing, since List holds Integer objects
            if (!values.get(left).equals(values.get(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * ============================================================================
     * Phase 3: Alternative Approach (Recursive Call Stack)
     * ============================================================================
     * Detailed Intuition:
     * We can simulate moving backwards from the tail by utilizing the recursion
     * call stack. We define an instance pointer `frontPointer` that starts at
     * the head. We recursively traverse to the very end of the list.
     * As the recursion unwinds (moving backwards from the tail), we compare the
     * current node with `frontPointer`. If they match, we advance `frontPointer`
     * and continue unwinding.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We visit each node once going deep into recursion, and once unwinding.
     * - Space Complexity: O(N) (Auxiliary Stack Space)
     *   The call stack grows to size N. While we don't allocate data structures
     *   on the heap, the recursive stack still consumes O(N) memory, which can
     *   cause a StackOverflowError for extremely large lists.
     * ============================================================================
     */
    private ListNode frontPointer;

    public boolean isPalindromeRecursive(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);
    }

    private boolean recursivelyCheck(ListNode currentNode) {
        if (currentNode != null) {
            // Dive to the very end of the list
            if (!recursivelyCheck(currentNode.next)) {
                return false;
            }
            // Now we are unwinding from the tail.
            // Compare the unwinding node with the front pointer.
            if (currentNode.val != frontPointer.val) {
                return false;
            }
            // Move the front pointer forward for the next unwinding step
            frontPointer = frontPointer.next;
        }
        return true;
    }

    /**
     * ============================================================================
     * 4. Testing Suite
     * ============================================================================
     */
    public static void main(String[] args) {
        LLPalindromeOrNot solution = new LLPalindromeOrNot();

        System.out.println("--- Executing Testing Suite ---");

        // Test 1: Even length palindrome -> [1, 2, 2, 1]
        ListNode head1 = buildList(1, 2, 2, 1);
        System.out.println("\nTest 1: [1, 2, 2, 1] (Expected: true)");
        System.out.println("Optimal:   " + solution.isPalindromeOptimal(buildList(1, 2, 2, 1)));
        System.out.println("Brute:     " + solution.isPalindromeBruteForce(buildList(1, 2, 2, 1)));
        System.out.println("Recursive: " + solution.isPalindromeRecursive(buildList(1, 2, 2, 1)));

        // Test 2: Odd length palindrome -> [1, 2, 3, 2, 1]
        ListNode head2 = buildList(1, 2, 3, 2, 1);
        System.out.println("\nTest 2: [1, 2, 3, 2, 1] (Expected: true)");
        System.out.println("Optimal:   " + solution.isPalindromeOptimal(buildList(1, 2, 3, 2, 1)));

        // Test 3: Not a palindrome -> [1, 2]
        ListNode head3 = buildList(1, 2);
        System.out.println("\nTest 3: [1, 2] (Expected: false)");
        System.out.println("Optimal:   " + solution.isPalindromeOptimal(buildList(1, 2)));

        // Test 4: Single element -> [1]
        ListNode head4 = buildList(1);
        System.out.println("\nTest 4: [1] (Expected: true)");
        System.out.println("Optimal:   " + solution.isPalindromeOptimal(buildList(1)));

        // Test 5: Verify Restoration of List structure (Optimal approach constraint)
        ListNode head5 = buildList(1, 2, 3, 2, 1);
        System.out.println("\nTest 5: List Integrity Check after Optimal Traversal");
        System.out.println("Before: " + listToString(head5));
        solution.isPalindromeOptimal(head5);
        System.out.println("After:  " + listToString(head5) + " (Should match Before)");
    }

    /**
     * Helper Method: Builds a linked list from an array of integers using Java 8 Streams.
     */
    private static ListNode buildList(int... values) {
        if (values == null || values.length == 0) return null;

        List<ListNode> nodes = IntStream.of(values)
                .mapToObj(ListNode::new)
                .collect(Collectors.toList());

        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        return nodes.get(0);
    }

    /**
     * Helper Method: Serializes the linked list back to a string format.
     */
    private static String listToString(ListNode head) {
        if (head == null) return "[]";

        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
