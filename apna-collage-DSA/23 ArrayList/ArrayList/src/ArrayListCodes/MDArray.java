package ArrayListCodes;

import java.util.ArrayList;

// code 6 -> multidimension array
public class MDArray {
    public static void main(String[] args) {
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

        list2.remove(3);
        list2.remove(2);

        for(ArrayList<Integer> i : mainList){
            System.out.println(i);
        }
    }
}
