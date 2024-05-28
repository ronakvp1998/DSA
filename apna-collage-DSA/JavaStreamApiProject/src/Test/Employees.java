package Test;
import java.util.Date;
import java.util.Objects;

public final class Employees {
    private final String name;
    private final int id;

    private final Date date;

    public Employees(String name, int id,Date date) {
        this.name = name;
        this.id = id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employees employees = (Employees) o;
        return id == employees.id && Objects.equals(name, employees.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
