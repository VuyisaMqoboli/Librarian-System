

package za.ac.cput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import za.ac.cput.dao.BookDAO;
import za.ac.cput.dao.LearnerDAO;
import za.ac.cput.dao.LoanDAO;
import za.ac.cput.domain.Book;
import za.ac.cput.domain.BookLoan;
import za.ac.cput.domain.Learner;
import za.ac.cput.domain.LearnerLoan;
import za.ac.cput.domain.Loan;


public class LocalServer {

    public static void main(String[] args) {
        
           
           ObjectOutputStream output = null;
           ObjectInputStream input = null;
           DataInputStream dataInput = null;
           DataOutputStream dataOutput = null;
           ServerSocket ss = null;
           Socket s = null;
           
           while(true){
           try{
                ss = new ServerSocket(8500);
                s = ss.accept();
                System.out.println("Server running...listening for a connection");
                

                System.out.println("Connection is established");
                input = new ObjectInputStream(s.getInputStream());
                output = new ObjectOutputStream(s.getOutputStream());
                dataInput = new DataInputStream(s.getInputStream());
                dataOutput = new DataOutputStream(s.getOutputStream());
                 
                
                int num = dataInput.readInt();
              
                if(num == 1){
                    Book b = new Book();
                    b = (Book) input.readObject();

                    Boolean resetForm1;

                    if(BookDAO.isIsbnUnique(b)){  
                        resetForm1 = true;
                        output.writeBoolean(resetForm1);
                        output.flush();
                        BookDAO.saveToDB(b);

                    }else{
                        resetForm1 = false;
                        output.writeBoolean(resetForm1);
                        output.flush();

                    }
                    
                    
                }else if(num == 0){
                    Learner len = new Learner();
                    len = (Learner) input.readObject();
                    
                    boolean correct = true;
                    
                    if(true){
                       
                        LearnerDAO.delete(len);
                        output.writeBoolean(correct);
                        output.flush();
                    }else{
                        correct = false;
                        output.writeBoolean(correct);
                        output.flush();
                    }
                    
                    
                }else if(num == 22){
                    Learner learner = new Learner();
                    learner = (Learner)input.readObject();
                    
                    boolean correct = true;
                    if(LearnerDAO.isStudentNummberUnique(learner)){
                        correct = false;
                        output.writeBoolean(correct);
                        output.flush();
                    }else{
                        output.writeObject(correct);
                        output.flush();
                    }
                    
                    
                    
                    
                    
                }else if(num == 2){
                    Learner learner = new Learner();
                    learner = (Learner) input.readObject();


                    Boolean resetForm2;

                    if(LearnerDAO.isStudentNummberUnique(learner)){  
                        resetForm2 = true;
                        output.writeBoolean(resetForm2);
                        output.flush();
                        LearnerDAO.saveToDB(learner);

                    }else{
                        resetForm2 = false;
                        output.writeBoolean(resetForm2);
                        output.flush();

                    }
                    
                    
                }else if(num == 3){
                    ArrayList<LearnerLoan> stud = new ArrayList<>();
                    stud = LoanDAO.populateStudentNumberComboBox();
                    output.writeObject(stud);
                    output.flush();
                    
                   
                    
                }else if(num == 4){
                    ArrayList<BookLoan> boo = new ArrayList<>();
                    boo = LoanDAO.populateIsbnComboBox();
                    output.writeObject(boo);
                    output.flush();
                  
                    
                }else if(num == 5){
                    
                    Loan loan = new Loan();
                    loan = (Loan) input.readObject();
            
                  

                    boolean resetForm3 = true;

                    if(LoanDAO.isLoanNumberUnique(loan)){  
                        
                        LoanDAO.updateAvailableForLoan(loan);
                        LoanDAO.updateCanBorrow(loan);
                        LoanDAO.storeToDB(loan);
                        output.writeBoolean(resetForm3);
                        output.flush();

                    }else{
                        resetForm3 = false;
                        output.writeBoolean(resetForm3);
                        output.flush();

                    }
                    
                    
                }/*else if(num == 6){
                    Loan loanNum = new Loan();
                    loanNum = (Loan) input.readObject();
                    ArrayList<Loan> returnBook = new ArrayList<>();

                    Boolean resetForm4 = true;;

                    if(LoanDAO.isLoanNumberUnique(loanNum)){ 
                        returnBook = LoanDAO.retrieveStudentAndIsbn(loanNum);
                        dataOutput.writeBoolean(resetForm4);
                        output.writeObject(returnBook);
                        output.flush();
                        dataOutput.flush();

                    }else{
                        resetForm4 = false;
                        dataOutput.writeBoolean(resetForm4);
                        dataOutput.flush();
                        output.flush();

                    }
                
                    
                }else if(num == 7){
                    Loan loan1 = new Loan();
                    loan1 = (Loan) input.readObject();


                    boolean resetForm5 = true;

                    if(resetForm5){

                        LoanDAO.updateAvailableForLoanTrue(loan1);
                        LoanDAO.updateCanBorrowTrue(loan1);
                        try{
                            double penCost = Loan.calulatePenaltyCost(loan1);
                            output.writeDouble(penCost);
                            output.flush();
                        }catch(ParseException e){
                            e.getStackTrace();
                        }      
                         LoanDAO.delete(loan1);
                         dataOutput.writeBoolean(resetForm5);
                         dataOutput.flush();

                    }else{
                        resetForm5 = false;
                        dataOutput.writeBoolean(resetForm5);
                        dataOutput.flush();

                    }
                }*/
            dataOutput.flush();
            output.flush();
            }catch (ConnectException e) {
                    e.printStackTrace(); 
            }catch(IOException ioe){
                    System.out.println("Error: "+ ioe.getMessage());  
            }catch(ClassNotFoundException cnfe){
                    System.out.println("Error: "+ cnfe.getMessage());
            }finally{
                try{
                if(s != null)
                s.close();
                if(output != null)              
                output.close();
                if(dataOutput != null)      
                  dataOutput.close();
                
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
                 
            }
        
        } 
      
    
    }
}
