package ArraysDemo;

import java.util.Arrays;

public class MyDynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public MyDynamicArray() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private int size(){
        return size;
    }

    private boolean isEmpty(){
        return size ==0;
    }

    public T get (int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index " + index + " out of bound");
        }
        return (T) array[index];
    }

    public void set(int index, T element){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index " + index + " out of bound");
        }
        array[index] = element;
    }

    public void add(T element) {
        ensureCapacity();
        array[size++] = element;
    }

    public void remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index " + index + " out of bound");
        }
        for(int i=index;i<size;i++){
            array[i] = array[i+1];
        }
        array[size-1] = null;
        size--;
    }

    private void ensureCapacity(){
        if(array.length == size ){
            int newCap = array.length * 2;
            array = Arrays.copyOf(array,newCap);
        }
    }

    public static void main(String[] args) {
        MyDynamicArray<Integer> dynamicArray = new MyDynamicArray<>();

        dynamicArray.add(10);
        dynamicArray.add(20);
        dynamicArray.add(30);

        System.out.println("Size: " + dynamicArray.size());

        System.out.println("Element at index 1: " + dynamicArray.get(1));

        dynamicArray.set(1, 25);

        System.out.println("Updated element at index 1: " + dynamicArray.get(1));

        dynamicArray.remove(0);

        System.out.println("Size after removal: " + dynamicArray.size());

    }

}
