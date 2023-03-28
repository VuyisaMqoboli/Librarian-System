
package za.ac.cput.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import za.ac.cput.domain.BookLoan;
import za.ac.cput.domain.LearnerLoan;
import za.ac.cput.domain.Loan;


public class LoanDAO {
    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/LibrarianSystem";
    private static final String dbUsername = "admins"; 
    private static final String dbPassword = "password";
        
  public static ArrayList<LearnerLoan> populateStudentNumberComboBox(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<LearnerLoan> list = new ArrayList<>();
        String sql = "SELECT studentNumber FROM learner WHERE canBorrow =  true ";      
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(sql);
               if(resultSet != null)
                   while(resultSet.next()){
                       LearnerLoan ll = new LearnerLoan(resultSet.getString(1));
                       list.add(ll);
                       
                   }
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
                 if(connection != null)  
                     connection.close();
                 
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        return list;
    }
  
  public static ArrayList<BookLoan> populateIsbnComboBox(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<BookLoan> list = new ArrayList<>();
        String sql = "SELECT isbn FROM books WHERE availableForLoan = true ";      
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(sql);
               if(resultSet != null)
                   while(resultSet.next()){
                       BookLoan bl = new BookLoan(resultSet.getString(1));
                       list.add(bl);
                   }
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
                 if(connection != null)  
                     connection.close();            
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
         return list;
    }
  
    public static boolean isLoanNumberUnique(Loan loan){
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean unique = false;
        
           int num;
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               resultSet = statement.executeQuery("SELECT * FROM loan WHERE loanNumber = '"+ loan.getLoanNumber()+"'");
               while(resultSet.next()){ 
                       unique = true;                                     
             }
               
           }catch(SQLException sqe){
               JOptionPane.showMessageDialog(null,"Error: Cannot find loan number"+ sqe);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
           
           return unique;

    }
    
    public static void storeToDB(Loan loan){
        
           Connection connection = null;
           PreparedStatement statement = null;
           String sql = "INSERT INTO loan VALUES(?,?,?,?,?,?)";
           int num;
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);
               
               statement.setString(1, loan.getLoanNumber());
               statement.setString(2, loan.getStudentNumber());
               statement.setString(3, loan.getIsbn());
               statement.setString(4, loan.getIssuedDate());
               statement.setString(5, loan.getDueDate());
               statement.setDouble(6, loan.getTotalPenaltyCost());
               
               num = statement.executeUpdate();
           }catch(SQLException sqe){
               JOptionPane.showMessageDialog(null,"Error: "+sqe.getMessage());
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception.getMessage());
            
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
           
    }
    
    public static void updateAvailableForLoan(Loan loan){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "UPDATE books SET availableForLoan = false WHERE isbn= '"+ loan.getIsbn()+"' ";
       
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               statement.execute();
             
             
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        
    }
    
    public static void updateCanBorrow(Loan loan){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "UPDATE learner SET canBorrow = false WHERE studentNumber= '"+ loan.getStudentNumber()+"' ";
            
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               statement.execute();
               
             
              
             
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        
    }
    
    public static ArrayList<Loan> retrieveStudentAndIsbn(Loan loanNum){
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Loan> list = new ArrayList<>();
        
           int num;
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               resultSet = statement.executeQuery("SELECT * FROM loan WHERE loanNumber = '"+ loanNum.getLoanNumber()+"'");
               if(resultSet != null)
               while(resultSet.next()){
                   Loan loan = new Loan(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6));
                   list.add(loan);
               }
               
           }catch(SQLException sqe){
               JOptionPane.showMessageDialog(null,"Error: Cannot borrow book"+ sqe);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
           
           return list;

    }
    
    public static void updateCanBorrowTrue(Loan loan){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "UPDATE learner SET canBorrow = true WHERE studentNumber= '"+ loan.getStudentNumber()+"' ";
            
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               statement.execute();
               
             
              
             
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        
    }
    
    public static void updateAvailableForLoanTrue(Loan loan){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "UPDATE books SET availableForLoan = false WHERE isbn= '"+ loan.getIsbn()+"' ";
       
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               statement.execute();
             
             
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        
    }
    
    public static void delete(Loan loan){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "DELETE FROM loan WHERE loanNumber ='"+ loan.getLoanNumber()+"'";    
      
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               resultSet = statement.executeQuery();
               
              while(resultSet.next()){ 
                   resultSet.getString(1);
                   resultSet.getString(2);
                   resultSet.getString(3);
                   resultSet.getString(4);
                   resultSet.getString(5);
                   resultSet.getDouble(6);                            
             }
              
             
           }catch(SQLException SQLExpcetion){
               JOptionPane.showMessageDialog(null,"Error: Could not read from the Database"+ SQLExpcetion);
           }catch(Exception exception){
               JOptionPane.showMessageDialog(null, "Error: "+ exception);
           }finally {
               try{
                 if(statement != null)  
                     statement.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }try{
                 if(connection != null)  
                     connection.close();
               }catch(Exception exception){
                   JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
               }
           }
        
    }
}
