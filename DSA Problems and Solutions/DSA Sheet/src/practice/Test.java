package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public String removeKdigits(String num, int k) {

    }

    public int carFleetPhase1(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0){
            return 0;
        }
        double[][] cars = new double[n][2];
        for(int i=0;i<n;i++){
            cars[i][0] = position[i];
            cars[i][1] = (double) (target-position[i])/speed[i];
        }
        Arrays.sort(cars, (a,b) -> Double.compare(b[0],a[0]));
        int fleets = 0;
        double maxTime = 0.0;
        for(int i=0;i<n;i++){
            if(cars[i][1] > maxTime){
                fleets++;
                maxTime = cars[i][1];
            }
        }
        return fleets;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int ans[] = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0;i<n;i++){
            int currentTemp = temperatures[i];
            while (!stack.isEmpty() && temperatures[stack.peek()] < currentTemp){
                int prevDayIndex = stack.pop();
                ans[prevDayIndex] = i - prevDayIndex;
            }
            stack.push(i);
        }
        return ans;
    }

    public List<String> generatePar(int n){
        List<String> res = new ArrayList<>();
        backtrack(res,new StringBuilder(),0,0,n);
        return res;
    }

    public void backtrack(List<String> res,StringBuilder currentString,
                          int openCount, int closeCount, int maxPairs){

        if(currentString.length() == maxPairs*2){
            res.add(currentString.toString());
            return;
        }
        if(openCount < maxPairs){
            currentString.append("(");
            backtrack(res,currentString,openCount+1,closeCount,maxPairs);
            currentString.deleteCharAt(currentString.length()-1);
        }
        if(closeCount < openCount){
            currentString.append(")");
            backtrack(res,currentString,openCount,closeCount+1,maxPairs);;
            currentString.deleteCharAt(currentString.length()-1);
        }
    }

    public int evalRPM(String [] tokens){
        Deque<Integer> stack = new ArrayDeque<>();
        for(String token : tokens){
            switch (token){
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case"-":
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b - a);
                    break;
                case "/":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b/a);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }
        return stack.pop();
    }

}
