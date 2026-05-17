
// code 12 Remove Duplicates in a String "appnnacollege"
public class RemoveDuplicates {

    public static void removeDuplicates(String str, int idx, StringBuilder newStr , boolean map[]){

        // base case
        if(idx == str.length()){
            System.out.println(newStr);
            return;
        }

        // kaam
        char currChar = str.charAt(idx);
        if(map[currChar - 'a'] == true){
            // duplicate char
            removeDuplicates(str,idx+1,newStr,map);
        }else{
            // non duplicate char
            map[currChar - 'a'] = true;
            removeDuplicates(str,idx+1,newStr.append(currChar),map);
        }

    }

    public static void main(String[] args) {
        String str = "appnnacollege";
        removeDuplicates(str,0,new StringBuilder(),new boolean[26]);
    }
}
