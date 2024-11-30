package ArrayListDemos;

import java.util.ArrayList;
import java.util.List;

public class Demos {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
//        reversePrint(list);
//        findMax(list);
//        multidimensionArray();
        containerMostWater();
    }

    public static void containerMostWater2(){
        List<Integer> list = List.of(1,8,6,2,5,4,8,3,7);

        int lp = 0;
        int rp = list.size();
        int currWater = 0;
        int height = 0;
        int width = 0;
        while (lp < rp){
//            currWater = h
        }
    }

    public static void containerMostWater(){
        List<Integer> list = List.of(1,8,6,2,5,4,8,3,7);

        int maxWater = 0;
        int maxI = 0;
        int maxJ = 0;

        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++){
                int width = j-i;
                int height = Math.min(list.get(i),list.get(j));
                if(maxWater<(width*height)){
                    maxWater = width*height;
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        System.out.println("i = " + maxI + " j = " + maxJ);

    }

    public static void multidimensionArray(){
        ArrayList<ArrayList<Integer>> mainList = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();

        for(int i=1;i<=5;i++){
            list1.add(i*1);
            list2.add(i*2);
            list3.add(i*3);
        }

        mainList.add(list1);
        mainList.add(list2);
        mainList.add(list3);

        for(ArrayList<Integer> i : mainList){
            System.out.println(i);
        }

    }

    public static void findMax(ArrayList<Integer> list){
        int max = Integer.MIN_VALUE;
        for(Integer i : list){
            if(i > max){
                max = i;
            }
        }
        System.out.println("Max " + max);
    }

    public static void reversePrint(ArrayList<Integer> list){
        for(int i=list.size()-1;i>=0;i--){
            System.out.print(list.get(i) + " ");
        }
    }
}
