public class SortestDistanceCode {

    public static void main(String[] args) {
        String path = "WNEENESENNN";
        System.out.println("shortest path: " +shortestDistance(path));
    }

    public static float shortestDistance(String path){
        int x=0,y=0;
        for(int  i=0;i<path.length();i++){
            char dir = path.charAt(i);
            // South
            if(dir == 'S'){
                y--;
            }
            // North
            else if(dir == 'N'){
                y++;
            }
            //  West
            else if (dir == 'W' ) {
                x--;
            }
            // east
            else if (dir == 'E') {
                x++;
            }
        }

        int x2 = x*x;
        int y2 = y*y;
        return (float) (Math.sqrt(x2+y2));
    }
}
