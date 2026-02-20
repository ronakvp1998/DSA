package com.questions.strivers.heaps.hard;

import java.util.*;

/**
 * ==================================================================================================
 * APPROACH: OOP Design with Max-Heap for News Feed Merging
 * ==================================================================================================
 * 1. Tweet Class: A node in a linked list containing tweetId and a global timestamp.
 * 2. User Class: Contains a Set of followee IDs and a pointer to the head of their Tweet list.
 * 3. Twitter Class: Maps userIds to User objects.
 * ==================================================================================================
 */
class Twitter {
    private static int timestamp = 0; // Global counter to track tweet recency
    private Map<Integer, User> userMap;

    // Internal Tweet class (Linked List Node)
    private class Tweet {
        int id;
        int time;
        Tweet next;

        Tweet(int id, int time) {
            this.id = id;
            this.time = time;
            this.next = null;
        }
    }

    // Internal User class
    private class User {
        int id;
        Set<Integer> followed;
        Tweet tweetHead;

        User(int id) {
            this.id = id;
            followed = new HashSet<>();
            follow(id); // A user follows themselves to see their own tweets
            tweetHead = null;
        }

        void follow(int id) { followed.add(id); }
        void unfollow(int id) { if (id != this.id) followed.remove(id); }

        void post(int id) {
            Tweet t = new Tweet(id, timestamp++);
            t.next = tweetHead; // Add to the front of the list (most recent)
            tweetHead = t;
        }
    }

    public Twitter() {
        userMap = new HashMap<>();
    }

    /** Compose a new tweet. Time: O(1) */
    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) userMap.put(userId, new User(userId));
        userMap.get(userId).post(tweetId);
    }

    /** * Retrieve the 10 most recent tweets.
     * Time: O(K log K) where K is the number of followees (usually capped at 10).
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<>();
        if (!userMap.containsKey(userId)) return res;

        Set<Integer> users = userMap.get(userId).followed;
        // PriorityQueue stores current head of each followee's tweet list
        // Ordered by timestamp descending (Max-Heap)
        PriorityQueue<Tweet> q = new PriorityQueue<>(users.size(), (a, b) -> b.time - a.time);

        for (int user : users) {
            Tweet t = userMap.get(user).tweetHead;
            if (t != null) q.add(t);
        }

        int n = 0;
        while (!q.isEmpty() && n < 10) {
            Tweet t = q.poll();
            res.add(t.id);
            n++;
            if (t.next != null) q.add(t.next); // Add next tweet from the same user
        }
        return res;
    }

    /** Follow a user. Time: O(1) */
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) userMap.put(followerId, new User(followerId));
        if (!userMap.containsKey(followeeId)) userMap.put(followeeId, new User(followeeId));
        userMap.get(followerId).follow(followeeId);
    }

    /** Unfollow a user. Time: O(1) */
    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId) || followerId == followeeId) return;
        userMap.get(followerId).unfollow(followeeId);
    }
}