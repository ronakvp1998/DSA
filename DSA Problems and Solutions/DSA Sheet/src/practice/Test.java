package practice;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
//1423. Maximum Points You Can Obtain from Cards
    public int maxScore(int[] cardPoints, int k) {
        int n=cardPoints.length,maxScore=Integer.MIN_VALUE,score=0;
        for(int i=0;i<k;i++){
            score += cardPoints[i];
        }
        maxScore = score;
        int j=n-1;
        for(int i=k-1;i>=0;i--){
            score -= cardPoints[i];
            score += cardPoints[j];
            j--;
            maxScore = Math.max(maxScore,score);
        }
        return maxScore;
    }
}