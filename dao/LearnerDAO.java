
package za.ac.cput.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import za.ac.cput.domain.*;

public class LearnerDAO {
    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/LibrarianSystem";
    private static final String dbUsername = "admins"; 
    private static final String dbPassword = "password";
    
    public static boolean isStudentNummberUnique(Learner learner){
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean unique = false;
        
           int ok;
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               resultSet = statement.executeQuery("SELECT * FROM Learner WHERE studentnumber = '"+ learner.getStudentNumber()+"'");
               if(!resultSet.next()){
                   unique = true;
               }
               
           }catch(SQLException sqe){
               JOptionPane.showMessageDialog(null,"Error: Could not add learner"+ sqe);
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
    
    public static void saveToDB(Learner learner){
        
           Connection connection = null;
           PreparedStatement statement = null;
           int ok;
           String sql = "INSERT INTO Learner VALUES(?,?,?,?)";
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);
               statement.setString(1, learner.getStudentNumber());
               statement.setString(2, learner.getName());
               statement.setString(3, learner.getSurname());
               statement.setBoolean(4, learner.isCanBorrow());
               
               ok = statement.executeUpdate();
               if(ok>0){
                 //  JOptionPane.showMessageDialog(null, "Successful! Student added.");
               }else{
                 //  JOptionPane.showMessageDialog(null, "Error: Could not add student");
               }
               
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
    
     public static void delete(Learner learner){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "DELETE FROM learner WHERE studentNumber ='"+ learner.getStudentNumber()+"'";    
      
        
        try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);    
               resultSet = statement.executeQuery();
               
              while(resultSet.next()){ 
                   resultSet.getString(1);
                   resultSet.getString(2);
                   resultSet.getString(3);
                   resultSet.getBoolean(4);
                                              
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
