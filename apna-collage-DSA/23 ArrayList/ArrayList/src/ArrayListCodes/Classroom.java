package ArrayListCodes;

import java.util.ArrayList;

// code 1 ArrayList Demos
public class Classroom {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<Boolean> list3 = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list);

        // get opt
//        int element = list.get(2);
//        System.out.println(element);
//
//         delete
//        System.out.println(list);
//        System.out.println(list.remove(1));
//        System.out.println(list.remove(Integer.valueOf(5)));
//        System.out.println(list);

        // contains
//        System.out.println(list.contains(1));
//        System.out.println(list.contains(11));

        // get
//        System.out.println(list.get(1));

        //size
//        System.out.println(list.size());

        // print arraylist
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i) + " ");
        }

        System.out.println();
    }
}
