package tcp.object;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private  String name;
    private double empSalary;

    public Employee(int id, String name, double empSalary) {
        this.id = id;
        this.name = name;
        this.empSalary = empSalary;
    }

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

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }
}
