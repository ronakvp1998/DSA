package com.questions.strivers.graph.toposortproblems;

import java.util.*;

/**
 * ==================================================================================================
 *                                 Course Schedule I | BFS (Kahn's Algorithm)
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * You are given a total of n tasks labeled from 0 to n-1.
 * Some tasks have prerequisite tasks; for example, to pick task 0 you must first finish task 1,
 * represented as a pair [0, 1].
 *
 * Task:
 *  - Determine if it is possible to finish all tasks.
 *  - Return true if all tasks can be completed, false otherwise.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS / KAHN'S ALGORITHM
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 *  - Model the problem as a Directed Acyclic Graph (DAG), where tasks are nodes and prerequisites are edges.
 *  - Use indegree array to count incoming edges for each task.
 *  - Start with tasks with zero indegree (no prerequisites).
 *  - Remove the task from graph, reduce indegree of neighbors.
 *  - If all tasks are processed successfully, return true; otherwise, return false.
 *
 * STEPS:
 * 1️⃣ Build adjacency list and indegree array from prerequisites.
 * 2️⃣ Initialize queue with all tasks having zero indegree.
 * 3️⃣ While queue is not empty:
 *      - Pop task from queue.
 *      - Reduce indegree of its neighbors.
 *      - If indegree of neighbor becomes zero, add it to the queue.
 * 4️⃣ Count processed tasks.
 * 5️⃣ If count equals numCourses, all tasks can be completed.
 *
 * COMPLEXITY ANALYSIS:
 *  - Time Complexity : O(V + E)
 *      - Each node is processed once.
 *      - Each edge is traversed once to update indegrees.
 *  - Space Complexity: O(V + E)
 *      - Queue + adjacency list + indegree array.
 * --------------------------------------------------------------------------------------------------
 */
public class CourseScheduleI {

    /**
     * Function to check if all courses/tasks can be finished
     *
     * @param numCourses    Total number of tasks
     * @param prerequisites Array of prerequisite pairs [a, b] meaning "to take a, you must first take b"
     * @return              True if all tasks can be finished, otherwise false
     */
    private static boolean canFinish(int numCourses, int[][] prerequisites) {
        // Initialize adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        // Initialize indegree array
        int[] inDegree = new int[numCourses];

        // Build graph: for each prerequisite [a, b], add edge b -> a
        for (int[] pre : prerequisites) {
            int a = pre[0], b = pre[1];
            adj.get(b).add(a);
            inDegree[a]++;
        }

        // Queue to process tasks with zero indegree
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }

        // Counter for processed tasks
        int count = 0;

        // BFS processing
        while (!q.isEmpty()) {
            int task = q.poll();
            count++;

            // Reduce indegree of neighbors
            for (int neighbor : adj.get(task)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    q.offer(neighbor); // Task is now ready to process
                }
            }
        }

        // If all tasks processed, return true
        return count == numCourses;
    }

    public static void main(String[] args) {
        // Test Case 1: Cycle exists (0 -> 1, 1 -> 0)
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}, {0, 1}};
        System.out.println("Can finish all courses? " + canFinish(numCourses1, prerequisites1)); // false

        // Test Case 2: No cycle
        int numCourses2 = 4;
        int[][] prerequisites2 = {{1, 0}, {2, 1}, {3, 2}};
        System.out.println("Can finish all courses? " + canFinish(numCourses2, prerequisites2)); // true
    }
}
