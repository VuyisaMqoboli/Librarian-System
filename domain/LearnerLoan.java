
package za.ac.cput.domain;

import java.io.Serializable;

public class LearnerLoan implements Serializable{
    private String studentNumber;

    public LearnerLoan() {
    }

    public LearnerLoan(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "LearnerLoan{" + "studentNumber=" + studentNumber + '}';
    }
    
    
}
