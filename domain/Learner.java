
package za.ac.cput.domain;

import java.io.Serializable;

public class Learner implements Serializable{
    private String studentNumber;
    private String name;
    private String surname;
    private boolean canBorrow;

    public Learner() { }
    
    public Learner(String studentNumber){
        this.studentNumber = studentNumber;
    }

    public Learner(String studentNumber, String name, String surname, boolean canBorrow) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.surname = surname;
        this.canBorrow = canBorrow;
    }

    
    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public boolean isCanBorrow() {
        return canBorrow;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    
    
    @Override
    public String toString() {
        return "Learner{" + "studentNumber=" + studentNumber + ", name=" + name + ", surname=" + surname + ", canBorrow=" + canBorrow + '}';
    }
    
    
}
