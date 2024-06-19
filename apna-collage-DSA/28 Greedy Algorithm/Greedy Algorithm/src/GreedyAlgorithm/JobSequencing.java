package GreedyAlgorithm;

// code 6 :- Job Sequencing

import java.util.ArrayList;
import java.util.Collections;

public class JobSequencing {

    static class Job{
        int deadline;
        int profit;
        int id;    // id of the jobs 0-A, 1-B, 2-C, 3-D

        public Job(int i,int d,int p){
            id = i;
            deadline = d;
            profit = p;
        }
    }

    public static void main(String[] args) {
        int jobsInfo [][] = {{4,20}, {1,10} ,{ 1,40} , {1,30}};

        ArrayList<Job> jobs = new ArrayList<>();

        for(int i=0;i< jobsInfo.length;i++){
            jobs.add(new Job(i,jobsInfo[i][0],jobsInfo[i][1]));
        }

        // descending order of profit
        Collections.sort(jobs,(a,b) -> b.profit - a.profit);

        ArrayList<Integer> seq = new ArrayList<>();
        int time = 0;
        for(int i=0;i<jobs.size();i++){
            Job curr = jobs.get(i);
            if(curr.deadline > time){
                seq.add(curr.id);
                time++;
            }
        }

        // print seq of jobs
        System.out.println("Max jobs = " + seq.size());
        for(int i=0;i<seq.size();i++){
            System.out.println(seq.get(i) + " ");
        }

        System.out.println();
    }
}
