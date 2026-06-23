package strivers.stackandqueues.monotonicstackqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: EVALUATE REVERSE POLISH NOTATION
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem: 150. Evaluate Reverse Polish Notation (Medium)
 * * You are given an array of strings `tokens` that represents an arithmetic
 * expression in a Reverse Polish Notation (Postfix Notation).
 * Evaluate the expression. Return an integer that represents the value of the
 * expression.
 * * Note that:
 * - The valid operators are '+', '-', '*', and '/'.
 * - Each operand may be an integer or another expression.
 * - The division between two integers always truncates toward zero.
 * - There will not be any division by zero.
 * - The input represents a valid arithmetic expression in a reverse polish notation.
 * - The answer and all the intermediate calculations can be represented in a
 * 32-bit integer.
 * * Constraints:
 * - 1 <= tokens.length <= 10^4
 * - tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in
 * the range [-200, 200].
 * * Examples:
 * Example 1:
 * Input: tokens = ["2","1","+","3","*"] | Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 * * Example 2:
 * Input: tokens = ["4","13","5","/","+"] | Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 * * Example 3:
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * * ----------------------------------------------------------------------------
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ----------------------------------------------------------------------------
 * Since this is not a DP problem, we utilize a data-structure-centric roadmap:
 * - Phase 1: Optimal Approach - Standard Stack using Deque.
 * - Phase 2: Perfected Approach - Array Simulation of Stack (Highest Performance).
 * - Phase 3: Alternative Approach - Right-to-Left Recursion.
 * ============================================================================
 */
public class EvaluateReversePolishNotation {

    /**
     * ----------------------------------------------------------------------------
     * Phase 1: Optimal Approach (Standard Stack)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * Reverse Polish Notation (Postfix) dictates that operators immediately follow
     * their operands. This inherently aligns with a LIFO (Last-In-First-Out)
     * data structure—a Stack.
     * We iterate through the tokens. If we see a number, we push it onto the stack.
     * If we see an operator, we pop the top two numbers (the most recently seen
     * operands), apply the operator, and push the evaluated result back onto the
     * stack to be used by subsequent operations.
     * * Note: When popping for operations like division and subtraction, order matters.
     * The first element popped is the right operand (divisor/subtrahend), and the
     * second is the left operand (dividend/minuend).
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the length of the tokens array. We process
     * each token exactly once.
     * - Space Complexity: O(N) Auxiliary Space. In the worst case (an expression
     * with all operands and no operators initially, e.g., "1 2 3 + +"), the
     * stack will hold at most N/2 + 1 elements. The space scales linearly.
     * ----------------------------------------------------------------------------
     */
    public int evalRPNPhase1(String[] tokens) {
        // Using Deque (ArrayDeque) as it is faster than the legacy java.util.Stack
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int subtrahend = stack.pop();
                    int minuend = stack.pop();
                    stack.push(minuend - subtrahend);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor);
                    break;
                default:
                    // It's a number, push it to the stack
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }

        // The final element remaining is the evaluated result
        return stack.pop();
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 2: Perfected Approach (Array Simulation of Stack)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * While the standard Stack/Deque is conceptually perfect, object wrappers
     * (`Integer`) and dynamic resizing in collections introduce memory and
     * garbage collection overhead. Since we know from the constraints that
     * `tokens.length <= 10^4`, the maximum size of our stack will never exceed
     * 10^4. We can simulate the stack perfectly using a primitive `int[]` array
     * and a pointer (`top`). This skips object unboxing/boxing entirely.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Single pass through the array. Faster constant
     * factors than Phase 1 due to primitive operations.
     * - Space Complexity: O(N) Heap Space for the fixed primitive array.
     * Auxiliary space is O(1) beyond the allocated array.
     * ----------------------------------------------------------------------------
     */
    public int evalRPNPhase2(String[] tokens) {
        int[] stack = new int[tokens.length];
        int top = -1; // Pointer to the top of the simulated stack

        for (String token : tokens) {
            switch (token) {
                case "+":
                    // Result stored in the previous slot, top decrements
                    stack[top - 1] = stack[top - 1] + stack[top];
                    top--;
                    break;
                case "-":
                    stack[top - 1] = stack[top - 1] - stack[top];
                    top--;
                    break;
                case "*":
                    stack[top - 1] = stack[top - 1] * stack[top];
                    top--;
                    break;
                case "/":
                    stack[top - 1] = stack[top - 1] / stack[top];
                    top--;
                    break;
                default:
                    // Increment top and store the parsed integer
                    stack[++top] = Integer.parseInt(token);
                    break;
            }
        }
        return stack[0];
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 3: Alternative Approaches (Right-to-Left Recursion)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * Reverse Polish Notation is just a post-order traversal of an Abstract Syntax
     * Tree (AST). If we reverse our perspective and iterate through the tokens array
     * from RIGHT to LEFT, we are encountering the root operator first, followed by
     * its right child expression, then its left child expression.
     * We can evaluate this using recursion. We maintain a global/instance index
     * starting at the end of the array.
     * * Complexity Analysis:
     * - Time Complexity: O(N). Each token is visited once recursively.
     * - Space Complexity: O(N) Auxiliary Stack Space in the worst-case due to
     * the recursion depth (e.g., highly unbalanced expression trees). No extra
     * heap space structures are used.
     * ----------------------------------------------------------------------------
     */
    private int pointer; // Shared state for recursive approach

    public int evalRPNPhase3(String[] tokens) {
        this.pointer = tokens.length - 1;
        return evaluateRecursive(tokens);
    }

    private int evaluateRecursive(String[] tokens) {
        String token = tokens[pointer--];

        // If it's an operator, recursively evaluate the right, then left operand
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            // Right operand MUST be evaluated first because of right-to-left traversal
            int rightOperand = evaluateRecursive(tokens);
            int leftOperand = evaluateRecursive(tokens);

            switch (token) {
                case "+": return leftOperand + rightOperand;
                case "-": return leftOperand - rightOperand;
                case "*": return leftOperand * rightOperand;
                case "/": return leftOperand / rightOperand;
            }
        }

        // Base case: it's a number
        return Integer.parseInt(token);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Thorough testing of all approaches using standard and edge cases.
     * Java 8 Streams are used for clean, declarative test execution.
     */
    public static void main(String[] args) {
        EvaluateReversePolishNotation evaluator = new EvaluateReversePolishNotation();

        // Define Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase(new String[]{"2","1","+","3","*"}, 9, "Example 1"),
                new TestCase(new String[]{"4","13","5","/","+"}, 6, "Example 2"),
                new TestCase(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}, 22, "Example 3"),
                new TestCase(new String[]{"18"}, 18, "Edge Case: Single Number"),
                new TestCase(new String[]{"3","-4","+"}, -1, "Edge Case: Negative Numbers")
        );

        System.out.println("--- Starting Evaluation Test Suite ---");

        // Method references to test
        List<MethodReference> methods = Arrays.asList(
                new MethodReference("Phase 1: Standard Stack", evaluator::evalRPNPhase1),
                new MethodReference("Phase 2: Array Simulated Stack", evaluator::evalRPNPhase2),
                new MethodReference("Phase 3: Right-to-Left Recursion", evaluator::evalRPNPhase3)
        );

        // Java 8 Streams execution
        methods.forEach(method -> {
            System.out.println("\nExecuting " + method.name + "...");
            testCases.stream().forEach(test -> {
                int result = method.function.apply(test.tokens);
                boolean passed = result == test.expectedOutput;
                System.out.printf("[%s] %s | Expected: %d | Got: %d%n",
                        passed ? "PASS" : "FAIL", test.description, test.expectedOutput, result);
            });
        });
    }

    // Helper classes for clean testing
    static class TestCase {
        String[] tokens;
        int expectedOutput;
        String description;

        TestCase(String[] tokens, int expectedOutput, String description) {
            this.tokens = tokens;
            this.expectedOutput = expectedOutput;
            this.description = description;
        }
    }

    static class MethodReference {
        String name;
        Function<String[], Integer> function;

        MethodReference(String name, Function<String[], Integer> function) {
            this.name = name;
            this.function = function;
        }
    }
}