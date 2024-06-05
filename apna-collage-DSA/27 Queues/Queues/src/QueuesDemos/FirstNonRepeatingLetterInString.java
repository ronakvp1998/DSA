package QueuesDemos;

// code 7 :- find first non repeatng letter in the Stream of characters

import java.util.LinkedList;
import java.util.Queue;

public class FirstNonRepeatingLetterInString {

    public static void printNonRepeating(String str){
        int frequency[] = new int[26];  // 'a' - 'z'
        Queue<Character> queue = new LinkedList<>();

        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            queue.add(ch);
            frequency[ch-'a']++;

            // remove all the char from queue which has count > 1
            while(!queue.isEmpty() && frequency[queue.peek() - 'a'] > 1){
                queue.remove();
            }

            if(queue.isEmpty()){
                System.out.print(-1 + " ");
            }else{
                System.out.print(queue.peek() + " ");
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {
        String str = "aabccxb";
        printNonRepeating(str);

    }
}
