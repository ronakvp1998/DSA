package ArrayListCodes;

import java.util.ArrayList;

// code 4-> swap 2 numbers
public class Swap2Numbers {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(5);
        list.add(9);
        list.add(3);
        list.add(6);
        int idx1 = 1;
        int idx2 = 3;
        System.out.println(list);
        int temp = list.get(idx1);
        list.set(idx1,list.get(idx2));
        list.set(idx2,temp);
        System.out.println(list);


    }
}
