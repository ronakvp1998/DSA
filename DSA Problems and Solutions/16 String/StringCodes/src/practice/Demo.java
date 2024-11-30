package practice;

public class Demo {

    public static void main(String[] args) {
//        System.out.println(pal("racecar"));
        shortestPath("WNEENESENNN");
    }

    public static void shortestPath(String path){

        int x1=0,y1=0,x2=0,y2=0;
        for(int i=0;i<path.length();i++){
            if(path.charAt(i) == 'N'){
                y2++;
            }
            else if(path.charAt(i) == 'S'){
                y2--;
            }
            else if (path.charAt(i) == 'E') {
                x2++;
            }else if(path.charAt(i) == 'W'){
                x2--;
            }else{
                System.out.println("Path is incorrect");
                break;
            }
        }

        double dist = Math.sqrt(Math.pow((x2-x1),2) +  Math.pow((y2-y1),2));
        System.out.println("Shortest dist " + dist);
    }

    public static boolean pal(String str){
        for(int i=0;i<str.length()/2;i++){
            if(str.charAt(i) != str.charAt(str.length()-i-1)){
                return false;
            }
        }
        return true;
    }
}
