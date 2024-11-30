package generics;

public class Utils {

    public static void printUsers(GenericList<? super User> users){
//        User x = users.get(0);
        Object x = users.get(0);

        users.add(new Instructor(1));
        users.add(new User( 1));

    }
//    public static void printUsers(GenericList<? extends User> users){
//        User x = users.get(0);
//    }


//    public static void printUser(GenericList<?> user){
//        System.out.println(user);
//    }
//    public static int max(int first,int second){
//        return (first>second) ? first : second;
//    }
//    public static <T extends Comparable<T>> T max(T first,T second){
//        return (first.compareTo(second) < 0) ? second : first;
//    }
//
//    public static <K,V> void print(K first,V second){
//        System.out.println(first + " " + second );
//    }
//
//    public static void printUser(User user){
//        System.out.println(user);
//    }



}
