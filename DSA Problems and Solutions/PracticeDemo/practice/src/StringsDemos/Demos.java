package StringsDemos;

public class Demos {
    public static void main(String[] args) {
//        System.out.println(palindrome("12321"));
//        System.out.println(shortestDistance("WNEENESENNN"));
        String arr[] = {"apple","amango","abanana"};
        // output should be mango
        System.out.println(largestStringLexo(arr));
        System.out.println(FirstUpper("hi i am ronak"));
        System.out.println(FirstUpper2("hi i am ronak"));

        System.out.println(stringCompression("aaaabbbdd"));
    }

    public static String stringCompression(String s){
        int count = 1;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length()-1;i++){
            sb.append(s.charAt(i));
            while (i < s.length()-1 && s.charAt(i) == s.charAt(i+1) ){
                count++;
                i++;
            }
            sb.append(count);
            count = 1;
        }
        return sb.toString();
    }

    public static String FirstUpper2(String s){
        StringBuilder sb = new StringBuilder();
        String arr[] = s.split(" ");
        for(int i=0;i<arr.length;i++){
            sb.append((arr[i].charAt(0) + "").toUpperCase());
            sb.append(arr[i].substring(1) + " ");
        }

        return sb.toString();

    }

    public static String FirstUpper(String s){

        StringBuilder sb = new StringBuilder();
        sb.append((s.charAt(0) + "" ).toUpperCase());
        for(int i=1;i<s.length();i++){
            if(s.charAt(i) == ' '){
                sb.append(' ');
                i++;
                sb.append((s.charAt(i) + "" ).toUpperCase());
                continue;
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static String largestStringLexo(String arr[]){

        String largest = arr[0];
        for(int i=0;i<arr.length;i++){
            if(largest.compareTo(arr[i]) < 0){
                largest = arr[i];
            }
        }
        return largest;
    }

    public static float shortestDistance(String s){
        int x=0,y=0;
        for(int i=0;i<s.length();i++){
            char a = s.charAt(i);
            if(a == 'N'){
                y++;
            }else if(a == 'S'){
                y--;
            } else if (a == 'E') {
                x++;
            } else if (a == 'W') {
                x--;
            }
        }

        float dis = (float) Math.sqrt(Math.pow(x-0,2) + Math.pow((y-0),2));
        return dis;
    }

    public static boolean palindrome(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) != s.charAt(s.length()-i-1)){
                return false;
            }
        }
        return true;
    }
}
