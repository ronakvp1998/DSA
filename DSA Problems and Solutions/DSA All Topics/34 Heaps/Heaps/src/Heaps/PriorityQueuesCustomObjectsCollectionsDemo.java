package Heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

// code 2 : demo on PQ Custom objects using collections

public class PriorityQueuesCustomObjectsCollectionsDemo {

    static class Student implements Comparable<Student>{
        String name;
        int rank;

        public Student(String name, int rank){
            this.name = name;
            this.rank = rank;
        }


        @Override
        public int compareTo(Student o) {
            return this.rank - o.rank;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Student> pq = new PriorityQueue<>();
        pq.add(new Student("A",4));  // O(logn)
        pq.add(new Student("B",5));  // O(logn)
        pq.add(new Student("C",2));  // O(logn)
        pq.add(new Student("D",12));  // O(logn)

        while (!pq.isEmpty()){
            System.out.println(pq.peek().name + " " + pq.peek().rank);     // O(1)
            pq.remove();    // O(logn)
        }

        System.out.println();

        PriorityQueue<Student> pq2 = new PriorityQueue<>(Comparator.reverseOrder());
        pq2.add(new Student("A",4));  // O(logn)
        pq2.add(new Student("B",5));  // O(logn)
        pq2.add(new Student("C",2));  // O(logn)
        pq2.add(new Student("D",12));  // O(logn)

        while (!pq2.isEmpty()){
            System.out.println(pq2.peek().name + " " + pq2.peek().rank);     // O(1)
            pq2.remove();    // O(logn)
        }
    }
}
