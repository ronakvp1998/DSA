public class FirstLetterUpper {
    //code 5

    public static void main(String[] args) {
        String str = "hi, i am shradha";
        System.out.println(upperWord2(str));
    }

    public static String upperWord(String str){
        String arr[] = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< arr.length;i++){
            String a = arr[i].toUpperCase();
            String b = arr[i];
            sb.append(a.charAt(0));
            sb.append(b.substring(1) + " ");
        }
        return sb.toString();
    }

    public static String upperWord2(String str){

        StringBuilder sb = new StringBuilder("");
//        char ch = str.toUpperCase().charAt(0);
        char ch = Character.toUpperCase(str.charAt(0));
        sb.append(ch);

        for(int i=1;i<str.length();i++){
            if(str.charAt(i) == ' ' && i<str.length()-1){
                sb.append(str.charAt(i));
                i++;
                sb.append(Character.toUpperCase(str.charAt(i)));
            }else{
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}
