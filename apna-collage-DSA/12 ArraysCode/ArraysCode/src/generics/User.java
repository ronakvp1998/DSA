package generics;

import java.util.Comparator;

public class User implements Comparable<User> {
    private int points;
    public User(int points){
        this.points = points;
    }
    @Override
    public int compareTo(User o) {
        if(points<o.points)
            return -1;
        if(points == o.points)
            return 0;
        return -1;
    }

    @Override
    public String toString(){
        return "Points=" + points;
    }
}
