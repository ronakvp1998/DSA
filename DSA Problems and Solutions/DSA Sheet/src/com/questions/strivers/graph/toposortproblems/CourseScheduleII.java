package com.questions.strivers.graph.toposortproblems;

import java.util.*;

/**
 * ==================================================================================================
 *                                 Course Schedule II | BFS (Kahn's Algorithm)
 * ==================================================================================================
 *
 * ------------------------------- PROBLEM STATEMENT -----------------------------------------------
 * You are given a total of N tasks labeled from 0 to N-1.
 * Some tasks have prerequisites; for example, to do task 0 you must first complete task 1,
 * represented as a pair [0, 1].
 *
 * Task:
 *  - Determine a valid order of tasks to complete all tasks.
 *  - If it is impossible to complete all tasks (due to a cycle), return an empty array.
 *
 * --------------------------------------------------------------------------------------------------
 *                                APPROACH — BFS / KAHN'S ALGORITHM
 * --------------------------------------------------------------------------------------------------
 * 🔑 KEY IDEA:
 *  - Model the problem as a Directed Acyclic Graph (DAG), where tasks are nodes and prerequisites are edges.
 *  - Use an indegree array to count incoming edges for each task.
 *  - Start with tasks with zero indegree (no prerequisites).
 *  - Remove the task from the graph, reduce indegree of neighbors.
 *  - Add tasks to the result array as they are processed.
 *  - If all tasks are processed, return the result array; otherwise, return empty array.
 *
 * STEPS:
 * 1️⃣ Build adjacency list and indegree array from prerequisites.
 * 2️⃣ Initialize queue with all tasks having zero indegree.
 * 3️⃣ While queue is not empty:
 *      - Pop a task from queue and add it to result.
 *      - Reduce indegree of all dependent tasks.
 *      - If indegree of a dependent task becomes zero, add it to queue.
 * 4️⃣ After processing, if result contains all tasks → valid ordering exists.
 *      Else → cycle exists → return empty array.
 *
 * COMPLEXITY ANALYSIS:
 *  - Time Complexity : O(V + E)
 *      - Each node is processed once.
 *      - Each edge is traversed once to update indegrees.
 *  - Space Complexity: O(V + E)
 *      - Queue + adjacency list + indegree array + result array.
 * --------------------------------------------------------------------------------------------------
 */
public class CourseScheduleII {

    /**
     * Function to find ordering of tasks/courses
     *
     * @param numCourses    Total number of tasks
     * @param prerequisites Array of prerequisite pairs [a, b] meaning "to do a, you must first do b"
     * @return              Array of task ordering if possible; otherwise empty array
     */
    private static int[] findOrder(int numCourses, int[][] prerequisites) {
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
            adj.get(b).add(a); // b -> a
            inDegree[a]++;
        }

        // Queue to process tasks with zero indegree
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }

        // Array to store the order of tasks
        int[] order = new int[numCourses];
        int idx = 0;

        // BFS processing
        while (!q.isEmpty()) {
            int task = q.poll();
            order[idx++] = task;

            // Reduce indegree of dependent tasks
            for (int neighbor : adj.get(task)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    q.offer(neighbor); // Task now ready to process
                }
            }
        }

        // If all tasks are processed, return order; otherwise, return empty array
        return (idx == numCourses) ? order : new int[0];
    }

    public static void main(String[] args) {
        // Test Case 1: Possible to complete all tasks
        int numCourses1 = 4;
        int[][] prerequisites1 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[] ans1 = findOrder(numCourses1, prerequisites1);
        System.out.println("Task order: " + Arrays.toString(ans1)); // Example: [0, 1, 2, 3]

        // Test Case 2: Impossible to complete due to cycle
        int numCourses2 = 2;
        int[][] prerequisites2 = {{0, 1}, {1, 0}};
        int[] ans2 = findOrder(numCourses2, prerequisites2);
        System.out.println("Task order: " + Arrays.toString(ans2)); // []
    }
}
