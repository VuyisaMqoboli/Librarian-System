
package za.ac.cput.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Loan implements Serializable {
    private String loanNumber;
    private String studentNumber;
    private String isbn;
    private String issuedDate;
    private String dueDate;
    private double totalPenaltyCost;

    public Loan() { }
    
    public Loan(String loanNumber){
        this.loanNumber = loanNumber;
    }
    
    public Loan(String studentNumber, String isbn){
        this.studentNumber = studentNumber;
        this.isbn = isbn;
    }

    public Loan(String loanNumber, String studentNumber, String isbn, String issuedDate, String dueDate, double totalPenaltyCost) {
        this.loanNumber = loanNumber;
        this.studentNumber = studentNumber;
        this.isbn = isbn;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.totalPenaltyCost = totalPenaltyCost;
    }
    
    

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getTotalPenaltyCost() {
        return totalPenaltyCost;
    }

    public void setTotalPenaltyCost(double totalPenaltyCost) {
        this.totalPenaltyCost = totalPenaltyCost;
    }

    public static double calulatePenaltyCost(Loan loan) throws ParseException{
        String issued = loan.getIssuedDate();
        String dueDate = loan.getDueDate();
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse(issued);
        Date secondDate = sdf.parse(dueDate);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        if(diff>7){
         
            double num = (double)diff * 0.5;
            return num;
        }else{
           
            double num = (double)diff * 0.5;
            return num;
        }
    }
    
    @Override
    public String toString() {
        return "Loan{" + "loanNumber=" + loanNumber + ", studentNumber=" + studentNumber + ", isbn=" + isbn + ", issuedDate=" + issuedDate + ", dueDate=" + dueDate + ", totalPenaltyCost=" + totalPenaltyCost + '}';
    }

   
    
    
    
    
    
    
}
