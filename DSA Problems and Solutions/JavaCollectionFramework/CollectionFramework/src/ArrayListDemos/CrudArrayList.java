package ArrayListDemos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//    Java Collection: ArrayList Exercises

public class CrudArrayList {

    public static void main(String[] args) {
        List<String> list = addElements();
        iterateList(list);
        insertFirst(list,"Ronak");
        retrieveIndexElement(list,3);
        updateIndexElement(list,3,"Panchal");
        removeIndex(list,3);
        searchElement(list,"Ronak");
        sortList(list);
        copyList();
        shuffleList(list);
        reverseList(list);
        extractPortionList(list);
        compareLists();
    }

    

//    compare two array lists.
    public static void compareLists(){
        ArrayList<String> c1= new ArrayList<String>();
        c1.add("Red");
        c1.add("Green");
        c1.add("Black");
        c1.add("White");
        c1.add("Pink");
        System.out.println(c1);

        ArrayList<String> c2= new ArrayList<String>();
        c2.add("Red");
        c2.add("Green");
        c2.add("Black");
        c2.add("Pink");
        System.out.println(c2);

        //Storing the comparison output in ArrayList<String>
        ArrayList<String> c3 = new ArrayList<>();
        for(String s : c1){
            c3.add(c2.contains(s) ? "Yes" : "No");
        }
        System.out.println(c3);

//        [Red, Green, Black, White, Pink]
//        [Red, Green, Black, Pink]
//        [Yes, Yes, Yes, No, Yes]
    }

//    extract a portion of an array list.
    public static void extractPortionList(List<String> list){
        iterateList(list);
        System.out.println(list.subList(0,3));
//        White->Green->Black->Ronak->Red->
//          [White, Green, Black]
    }

//    reverse elements in an array list.
    public static void reverseList(List<String> List){
        iterateList(List);
        Collections.reverse(List);
        iterateList(List);
    }
//    White->Green->Black->Red->Ronak->
//    Ronak->Red->Black->Green->White->

//    shuffle elements in an array list.
    public static void shuffleList(List<String> List){
        iterateList(List);
        Collections.shuffle(List);
        iterateList(List);
    }
//    Black->Green->Red->Ronak->White->
//    Green->White->Ronak->Black->Red->


//    program to copy one array list into another.
    public static void copyList(){
        List<String> List1 = new ArrayList<String>();
        List1.add("1");
        List1.add("2");
        List1.add("3");
        List1.add("4");
        System.out.println("List1: " + List1);
        List<String> List2 = new ArrayList<String>();
        List2.add("A");
        List2.add("B");
        List2.add("C");
        List2.add("D");
        System.out.println("List2: " + List2);
        // Copy List2 to List1
        Collections.copy(List1,List2);
        System.out.println("Copy List to List2,\nAfter copy:");
        System.out.println("List1: " + List1);
        System.out.println("List2: " + List2);
    }
//    List1: [1, 2, 3, 4]
//    List2: [A, B, C, D]
//    Copy List to List2,
//    After copy:
//    List1: [A, B, C, D]
//    List2: [A, B, C, D]

//    program to sort a given array list.
    public static void sortList(List<String> list){
        iterateList(list);
        Collections.sort(list);
        iterateList(list);
    }
//    Ronak->Red->Green->White->Black->
//    Black->Green->Red->Ronak->White->

//    search for an element in an array list.
    public static void searchElement (List<String> list,String element){
        System.out.println(list.contains(element));
        iterateList(list);
    }
//    true
//    Ronak->Red->Green->White->Black->

//    remove the third element from an array list.
    public static void removeIndex (List<String> list,int index){
        System.out.println(list.remove(index));
        iterateList(list);
    }
//    Ronak->Red->Green->Panchal->White->Black->
//    Panchal
//    Ronak->Red->Green->White->Black->


//    update an array index by the given element.
    public static void updateIndexElement (List<String> list,int index,String element){
        System.out.println(list.set(index,element));
        iterateList(list);
    }
//    Ronak->Red->Green->Orange->White->Black->
//    Orange
//    Ronak->Red->Green->Panchal->White->Black->

//    retrieve an element (at a specified index) from a given array list.
    public static void retrieveIndexElement (List<String> list,int index){
        System.out.println(list.get(index));
        iterateList(list);
    }
//    Orange
//    Ronak->Red->Green->Orange->White->Black->

    // insert an element into the array list at the first position.
    public static void insertFirst (List<String> list,String element){
        list.add(0,element);
        iterateList(list);
    }
    //    Red->Green->Orange->White->Black->
//    Ronak->Red->Green->Orange->White->Black->

    // Add elements to the list
    public static  List<String> addElements (){
        List<String> list_Strings = new ArrayList<>();
        list_Strings.add("Red");
        list_Strings.add("Green");
        list_Strings.add("Orange");
        list_Strings.add("White");
        list_Strings.add("Black");
//        System.out.println(list_Strings);
        return list_Strings;
    }
    //    Red->Green->Orange->White->Black->

    //  iterate through all elements in an array list..
    public static void iterateList ( List<String> list) {
        for(String s : list){
            System.out.print(s + "->");
        }
        System.out.println();
    }
//    Red->Green->Orange->White->Black->
}
