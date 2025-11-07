package it.university.model;

public class Classroom {
    private String code;
    private int capacity;

    public Classroom(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
    }

    public String getCode() { return code; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return "Classroom{" + code + ", capacity=" + capacity + "}";
    }
}
