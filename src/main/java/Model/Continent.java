package Model;

public class Continent {
    String name, color;
    Integer number,controlValue;

    public Continent(Integer number, String name, Integer controlValue, String color) {
        this.number= number;
        this.name = name;
        this.color = color;
        this.controlValue = controlValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getControlValue() {
        return controlValue;
    }

    public void setControlValue(Integer controlValue) {
        this.controlValue = controlValue;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
}
