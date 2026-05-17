package generics;

import java.util.Comparator;

//public class GenericList <T extends Number>{
//public class GenericList <T extends Comparable & Cloneable>{
public class GenericList <T extends Comparable>{


    private T[] items = (T[]) new Object[10];
    private int count;

    public void add(T item){
        items[count++] = item;
    }

    public T get (int index){
        return items[index];
    }
}
