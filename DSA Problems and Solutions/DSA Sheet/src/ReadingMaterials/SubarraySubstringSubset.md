# 🧩 Subarrays vs. Subsequences vs. Subsets

Understanding the difference between these three concepts is one of the most common stumbling blocks in Data Structures and Algorithms (DSA). This guide breaks down the definitions, rules, and the standard algorithmic approaches for each.

## 📊 Quick Comparison

Given the array `[1, 2, 3]` of size `N`:

| Concept | The Rule | Contiguous? | Order Matters? | Total Possible |
| :--- | :--- | :---: | :---: | :--- |
| **Subarray / Substring** | A continuous slice of the original. | ✅ Yes | ✅ Yes | `N(N+1)/2` |
| **Subsequence** | Elements picked in their original sequence. | ❌ No | ✅ Yes | `2^N - 1` (excluding empty) |
| **Subset** | Any mathematical combination of elements. | ❌ No | ❌ No | `2^N` (including empty) |

---

## 1. Subarray (Arrays) / Substring (Strings)

A subarray is a contiguous part of an array. You **cannot** skip elements.

* **Example for `[1, 2, 3]`:** `[1]`, `[2]`, `[3]`, `[1, 2]`, `[2, 3]`, `[1, 2, 3]`.
* ⚠️ *Note: `[1, 3]` is not a subarray because it skips `2`.*

### 🛠️ Standard Approaches
* **Sliding Window:** Use this when you need to find the longest/shortest subarray meeting a specific condition (e.g., "longest subarray with sum <= K"). It drops the time complexity from `O(N^2)` to `O(N)`.
* **Prefix Sums & HashMaps:** Use this for exact sum matches or when dealing with negative numbers (e.g., "count subarrays with sum exactly K").
* **Kadane’s Algorithm:** The undisputed go-to for finding the "Maximum Subarray Sum" in `O(N)` time.

---

## 2. Subsequence

A subsequence is derived by deleting zero or more elements without changing the order of the remaining elements. You **can** skip elements, but you **cannot** swap them.

* **Example for `[1, 2, 3]`:** All subarrays, plus `[1, 3]`.

### 🛠️ Standard Approaches
* **Dynamic Programming (DP):** If a problem asks for the "Longest", "Shortest", "Maximum", or "Count" of subsequences matching a rule, it is almost certainly DP.
    * *Classic Problems:* Longest Increasing Subsequence (LIS), Longest Common Subsequence (LCS).
* **Two Pointers:** Use this if you simply need to check if string/array `A` is a subsequence of string/array `B`. One pointer traverses `A`, the other traverses `B`.

---

## 3. Subset

A subset is a mathematical concept. It is any combination of elements from the original group. Order **does not matter** (`[1, 3]` and `[3, 1]` are the exact same subset), and it always includes the empty set `[]`.

* **Example for `[1, 2, 3]`:** `[]`, `[1]`, `[2]`, `[3]`, `[1, 2]`, `[1, 3]`, `[2, 3]`, `[1, 2, 3]`.

### 🛠️ Standard Approaches
* **Backtracking (Pick / Don't Pick):** The standard pattern for generating all subsets. You create a recursive decision tree where, for every element, you make two recursive calls: one where you include the element in your current subset, and one where you don't.
* **Bitmasking:** Because there are exactly `2^N` subsets, you can represent each subset using a binary number from `0` to `2^N - 1`. For example, the binary number `101` (which is `5` in decimal) means "take the 1st and 3rd elements, leave the 2nd". This is highly effective when `N <= 20`.

---

## 🧠 The Interview Decision Tree

When reading a problem statement, ask yourself:

1. **Does the problem require elements to be contiguous?**
    * Yes ➡️ **Subarray** (Think Sliding Window, Prefix Sum, Kadane's)
    * No ➡️ Move to question 2.
2. **Does the original order of the elements matter?**
    * Yes ➡️ **Subsequence** (Think Dynamic Programming, Two Pointers)
    * No ➡️ **Subset** (Think Backtracking, Bitmasking)