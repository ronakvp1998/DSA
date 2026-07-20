# 🤖 Java-Centric DSA Prompt Template

This version of the prompt ensures that the entire breakdown—from the recursion tree to the space-optimized code—is contained within the **Java comments**. This is perfect for single-file documentation in your Git repository.

---

## 🎯 The Master Prompt (Java Focus)
*Copy and paste the block below into your AI assistant, then append your problem statement.*

> **Role:** Act as a **Senior DSA Interviewer and Competitive Programming Evaluator**.
>
> **Objective:** Provide a masterclass-level solution for the problem provided below, contained entirely within a **single, well-documented Java class**.
>
> **Requirements (To be included strictly within the Java Class Comments):**
>
> * If code is already provided with the problem statement, use the provided code and document it using the pointers below. Evaluate if it is the optimal approach; if not, add the optimal approach alongside the other approaches mentioned below.
> * Use Java 8 Stream API syntax wherever appropriate.
>
> ### 1. Header & Problem Context
> * **Formal problem statement, constraints, and input/output formats:** Please keep the exact problem statement (especially if it is a LeetCode problem). Ensure there are at least 2 good examples.
> * **Conceptual Visualization:** For Dynamic Programming (DP) problems, include a text-based **Recursion Tree** mapping the overlapping subproblems. Include the completely filled final DP array for the primary example just below the recursion tree.
>
> ### 2.1. Progressive Implementation Roadmap (For DP Problems)
> *(If the problem is not a DP problem, ignore this section and its explanations).*
>
> Provide the following methods in order within the class. For each phase, briefly explain the approach and the steps to be performed:
> * **Phase 1: Brute Force Recursion** - The "Think it" stage, also add the for loop and normal recursion/backtracking code both if present.
> * **Phase 2: Top-Down Memoization** - The "Refine it" stage.
> * **Phase 3: Bottom-Up Tabulation** - The "Build it" stage.
    >     * *CRITICAL:* In the comments directly above this method, provide a text-based grid showing the **exact default state of the DP array immediately after base case initialization**, right before the main nested `for` loops begin. Additionally, provide a text-based grid showing the **exact final state of the DP array** for the same example.
> * **Phase 4: Space Optimization** - The "Perfect it" stage.
> * **Phase 5: Alternative Approaches** - (Greedy, Bitmask, Binary Search, etc., if applicable).
>
> ### 2.2. Progressive Implementation Roadmap (For Non-DP Problems)
> Provide the following methods in order within the class:
> * **Phase 1: Optimal Approach** - The best and recommended approach.
> * **Phase 2: Brute Force Approach** - The "Think it" stage.
> * **Phase 3: Alternative Approaches** - (Greedy, Bitmask, Binary Search, Sliding Window, Two Pointers, Hashing, etc., if applicable).
>
> ### 3. In-Code Technical Analysis
> For **every phase** method, the preceding comments must include:
> * **Detailed Intuition:** Explain the exact logical transition from the previous phase to the current one.
> * **Complexity Analysis:** Formal Big O notation for **Time O(...)** and **Space O(...)** (explicitly specifying auxiliary stack space vs. heap space).
>
> ### 4. Testing Suite
> * Add a `main` method to thoroughly test all the different approaches against standard and edge cases (especially zero-value edge cases if applicable).
>
> ---
> **Problem Statement:** > [INSERT PROBLEM HERE]