package entity;

public class ExerciseAppointment {

    private int setCount;
    private int repCount;
    private int weight;

    public ExerciseAppointment(int setCount, int repCount, int weight) {
        this.setCount = setCount;
        this.repCount = repCount;
        this.weight = weight;
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
}
