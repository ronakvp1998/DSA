public class SubStringCode {

    public static void main(String[] args) {
        String str = "ronakPanchal";
        subStringMethod(str,2,7);
        System.out.println(str.substring(0,5));
    }

    public static void subStringMethod(String str, int start, int end){
        String substr ="";
        for(int i=start;i<end;i++){
            substr += str.charAt(i);
        }
        System.out.println(substr);
    }
}
