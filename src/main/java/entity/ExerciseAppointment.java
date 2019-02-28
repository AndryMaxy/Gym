package entity;

import java.util.Objects;

public class ExerciseAppointment {

    private int id;
    private String name;
    private int setCount;
    private int repCount;
    private int weight;

    public ExerciseAppointment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExerciseAppointment)) {
            return false;
        }
        ExerciseAppointment that = (ExerciseAppointment) o;
        return id == that.id &&
                setCount == that.setCount &&
                repCount == that.repCount &&
                weight == that.weight &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, setCount, repCount, weight);
    }

    @Override
    public String toString() {
        return "ExerciseAppointment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", setCount=" + setCount +
                ", repCount=" + repCount +
                ", weight=" + weight +
                '}';
    }
}
