package backtracking;

// code 3 Find Permutations/Arrangements for a string
// "abc" -> "abc", "acb", "bac", "bca", "cab", "cba"
public class PermutationString {

    public static void main(String[] args) {
        String s = "abc";
        findPermutation(s,"");
    }

    public static void findPermutation(String s,String ans){
        // base case
        if(s.length() == 0){
            System.out.println(ans);
            return;
        }

        // recursion
        for(int i=0;i<s.length();i++){
            char curr = s.charAt(i);
            // removing that particular char and create new String
            String newStr = s.substring(0,i) + s.substring(i+1);
            System.out.println("i= " + i + " , curr= " + curr + " , newStr= " + newStr + " , ans= " + ans);
            findPermutation(newStr,ans + curr);

        }
    }
}
