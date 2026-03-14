# 🤖 Java-Centric DSA Prompt Template

This version of the prompt ensures that the entire breakdown—from the recursion tree to the space-optimized code—is contained within the **Java comments**. This is perfect for single-file documentation in your Git repo.

---

## 🎯 The Master Prompt (Java Focus)
*Copy and paste the block below into your AI assistant, then append your problem statement.*

> **Role:** Act as a **Senior DSA Interviewer and Competitive Programming Evaluator**.
>
> **Objective:** Provide a masterclass-level solution for the problem provided below, contained entirely within a **single, well-documented Java class**.
>
> **Requirements (To be included strictly within the Java Class Comments):**
>
> ### 1. Header & Problem Context
> * Formal problem statement, constraints, and input/output formats. please keep the exact problem statement from the leetcode its the its an leetcode problem, also tried to add good example at least 2.
> * **Conceptual Visualization:** For DP problems, include a text-based **Recursion Tree** mapping the overlapping subproblems. with complete final dp array filled for the same example just below the recursion tree
>
> ### 2. Progressive Implementation Roadmap
> Provide the following methods in order within the class:
> * **Phase 1: Brute Force Recursion** - The "Think it" stage.
> * **Phase 2: Top-Down Memoization** - The "Refine it" stage.
> * **Phase 3: Bottom-Up Tabulation** - The "Build it" stage.
    >   * *CRITICAL:* In the comments directly above this method, provide a text-based grid showing the **exact default state of the DP array immediately after base case initialization**, right before the main nested `for` loops begin. provide a text-based grid showing the **exact final state of the DP array for the same example
> * **Phase 4: Space Optimization** - The "Perfect it" stage.
> * **Phase 5: Alternative Approaches** - (Greedy, Bitmask, BinarySearch, etc., if applicable).
>
> ### 3. In-Code Technical Analysis
> For **every phase** method, the preceding comments must include:
> * **Detailed Intuition:** Explain the exact logical transition from the previous phase to the current one.
> * **Complexity Analysis:** Formal Big O notation for **Time (O)** and **Space (O)** (explicitly specifying auxiliary stack space vs. heap space).
>
> ### 4. Testing Suite
> * Add a `main` method to thoroughly test all the different approaches against standard and edge cases (especially zero-value edge cases if applicable).
>
> ---
> **Problem Statement:** > [INSERT PROBLEM HERE]