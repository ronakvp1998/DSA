package com.questions.strivers.graph.minspanningtreedisjointset;

import java.util.*;

/**
 * =====================================================================================
 *  LeetCode 721 – Accounts Merge
 * =====================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------
 *
 * You are given a list of accounts where:
 * - Each account is a list of strings.
 * - The first string is the person's name.
 * - The remaining strings are emails belonging to that person.
 *
 * Two accounts belong to the same person if they share **at least one common email**.
 *
 * Your task is to merge accounts that belong to the same person and return the merged
 * accounts.
 *
 * Each merged account should:
 * - Start with the user's name.
 * - Contain all unique emails sorted lexicographically.
 *
 * -------------------------------------------------------------------------------------
 *
 * EXAMPLE:
 *
 * Input:
 * [
 *   ["John","johnsmith@mail.com","john_newyork@mail.com"],
 *   ["John","johnsmith@mail.com","john00@mail.com"],
 *   ["Mary","mary@mail.com"],
 *   ["John","johnnybravo@mail.com"]
 * ]
 *
 * Output:
 * [
 *   ["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],
 *   ["Mary","mary@mail.com"],
 *   ["John","johnnybravo@mail.com"]
 * ]
 *
 * -------------------------------------------------------------------------------------
 *
 * CONSTRAINTS:
 * - 1 <= accounts.length <= 1000
 * - 2 <= accounts[i].length <= 10
 * - accounts[i][j] consists of lowercase letters
 *
 * =====================================================================================
 *
 * ----------------------------------- APPROACH ----------------------------------------
 *
 * This is a **graph connectivity problem**, solved efficiently using
 * **Disjoint Set Union (Union-Find)**.
 *
 * 🔹 Each **email** is treated as a node.
 * 🔹 If two emails appear in the same account, they belong to the same component.
 * 🔹 Union all emails within the same account.
 *
 * After union operations:
 * - Emails sharing the same root belong to the same person.
 * - Group emails by root.
 * - Sort emails and prepend the name.
 *
 * WHY UNION-FIND?
 * - Efficiently groups related emails.
 * - Handles transitive relationships naturally.
 *
 * -------------------------------------------------------------------------------------
 *
 * EDGE CASES:
 * - Multiple accounts with same name but no common emails → NOT merged
 * - Same email appearing in many accounts → all merged
 * - Single account → returned as-is
 *
 * -------------------------------------------------------------------------------------
 *
 * TIME COMPLEXITY:
 * - Union-Find operations: O(N α(N))
 * - Sorting emails per component: O(K log K)
 *
 * Overall: O(N log N)
 *
 * SPACE COMPLEXITY:
 * - O(N) for DSU, maps, and result storage
 *
 * =====================================================================================
 */
public class AccountMerge {

    /**
     * ---------------------------- DISJOINT SET (DSU) ----------------------------------
     * Supports:
     * - Path Compression
     * - Union by Rank
     */
    class DisjointSet {

        int[] parent;
        int[] rank;

        /**
         * Initialize DSU with n nodes.
         */
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initially, each node is its own parent
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        /**
         * Find operation with path compression.
         */
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // compress path
            }
            return parent[x];
        }

        /**
         * Union operation using rank heuristic.
         */
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px == py) return;

            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }

    /**
     * Main function that merges accounts.
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        // Map each email to a unique ID
        Map<String, Integer> emailToId = new HashMap<>();

        // Map each email to its owner name
        Map<String, String> emailToName = new HashMap<>();

        int id = 0;

        // =========================
        // STEP 1: Assign IDs to emails
        // =========================
        for (List<String> account : accounts) {
            String name = account.get(0);

            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);

                if (!emailToId.containsKey(email)) {
                    emailToId.put(email, id++);
                    emailToName.put(email, name);
                }
            }
        }

        // Initialize DSU with number of unique emails
        DisjointSet ds = new DisjointSet(id);

        // =========================
        // STEP 2: Union emails within same account
        // =========================
        for (List<String> account : accounts) {

            int firstEmailId = emailToId.get(account.get(1));

            for (int i = 2; i < account.size(); i++) {
                ds.union(firstEmailId, emailToId.get(account.get(i)));
            }
        }

        // =========================
        // STEP 3: Group emails by root parent
        // =========================
        Map<Integer, List<String>> components = new HashMap<>();

        for (String email : emailToId.keySet()) {
            int root = ds.find(emailToId.get(email));
            components.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        // =========================
        // STEP 4: Build final result
        // =========================
        List<List<String>> result = new ArrayList<>();

        for (List<String> emails : components.values()) {

            // Sort emails lexicographically
            Collections.sort(emails);

            // All emails belong to same user
            String name = emailToName.get(emails.get(0));

            List<String> merged = new ArrayList<>();
            merged.add(name);
            merged.addAll(emails);

            result.add(merged);
        }

        return result;
    }

    /**
     * ------------------------------ MAIN METHOD --------------------------------
     * Used for local testing.
     */
    public static void main(String[] args) {

        AccountMerge solution = new AccountMerge();

        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        accounts.add(Arrays.asList("Mary", "mary@mail.com"));
        accounts.add(Arrays.asList("John", "johnnybravo@mail.com"));

        List<List<String>> result = solution.accountsMerge(accounts);

        System.out.println("Merged Accounts:");
        for (List<String> acc : result) {
            System.out.println(acc);
        }
    }
}
