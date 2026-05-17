package practice;

import java.util.Arrays;

public class DynamicArray <T>{

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public DynamicArray(){
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public T get(int index){
        check(index);
        return (T) array[index];
    }

    public void check(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index " + index + " out od bounds");
        }
    }

    public void set(int index,T element){
        check(index);
        array[index] = element;
    }

    public void add(T element){
        ensureCapacity();
        array[size++] = element;
    }

    public void ensureCapacity(){
        if(size == array.length){
            int newLength = size*2;
            array = Arrays.copyOf(array,newLength);
        }
    }

    public void remove(int index){
        check(index);
        for(int i=index;i<size-1;i++){
            array[i] = array[i+1];
        }
        array[size-1] = null;
        size--;

    }
}
