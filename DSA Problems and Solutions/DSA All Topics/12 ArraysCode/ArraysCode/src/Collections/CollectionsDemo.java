package Collections;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionsDemo {

    public static void show(){
        Collection<String> collection = new ArrayList<>();
        collection.add("a");
        collection.add("b");
        collection.add("c");
        for(var item : collection){
            System.out.println(item);
        }
    }
}
