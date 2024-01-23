package drools.project;

public class Vehicle {

    private String type;
    private int count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Vehicle(String string, int i) {
        
        type = string;
        count = i;
    }

}
