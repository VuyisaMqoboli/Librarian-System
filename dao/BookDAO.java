
package za.ac.cput.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import za.ac.cput.domain.Book;


public class BookDAO {
    
    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/LibrarianSystem";
    private static final String dbUsername = "admins"; 
    private static final String dbPassword = "password";
    
    public static boolean isIsbnUnique(Book book){
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean unique = false;
        
           int num;
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.createStatement();
               resultSet = statement.executeQuery("SELECT * FROM Books WHERE isbn = '"+ book.getIsbn()+"'");
               if(!resultSet.next()){
                   unique = true;
               }
               
           }catch(SQLException sqe){
               JOptionPane.showMessageDialog(null,"Error: Could not add book"+ sqe);
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
    
    public static void saveToDB(Book book){
        
           Connection connection = null;
           PreparedStatement statement = null;
           int num;
           String sql = "INSERT INTO Books VALUES(?,?,?,?,?,?)";
           
           try{
               connection = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
               statement = connection.prepareStatement(sql);
               statement.setString(1, book.getIsbn());
               statement.setString(2, book.getTitle());
               statement.setString(3, book.getAuthor());
               statement.setString(4, book.getCategory());
               statement.setInt(5, book.getShelfNumber());
               statement.setBoolean(6, book.isAvailableForLoan());
               
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
}
