package Collections;

import generics.GenericList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Collection<String> collection = new ArrayList<>();
        Collections.addAll(collection,"a","b","c");
        System.out.println(collection);
        collection.addAll(List.of("1,2,3,4,4"));
    }
}
