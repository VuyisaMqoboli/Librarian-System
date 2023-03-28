
package za.ac.cput.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import za.ac.cput.domain.Loan;


public class ReturnBookGUI extends JFrame implements ActionListener,Serializable{
    private JPanel panelNorth, panelWest, panelCenter, panelSouth;
    private JLabel lblLoanNumber, lblStudentNumber, lblIsbn;
    private JTextField txtLoanNumber, txtStudentNumber,txtIsbn;
    public  JButton btnReturn,btnBack,btnSearch;
    private ImageIcon icon;
    private ArrayList<Loan> returnBookList = new ArrayList<>();
  
    
    public ReturnBookGUI(){
        super("Return Books");
        
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
         
        
        
        lblLoanNumber = new JLabel("Loan number:");
        lblStudentNumber = new JLabel("Student number:");
        lblIsbn = new JLabel("Isbn:");         
        
        txtLoanNumber = new JTextField(15);
        txtLoanNumber.requestFocus();
        txtStudentNumber = new JTextField("");
        txtIsbn = new JTextField("");
        
        btnBack = new JButton("Back");
        btnSearch = new JButton("Search");
        btnReturn = new JButton("Return book");
  
    }
            
    
    
    public void setGUI(){

        panelNorth.setLayout(new FlowLayout());
        panelCenter.setLayout(new GridLayout(4, 1));
        panelSouth.setLayout(new GridLayout(1, 2));
        
        
        panelNorth.add(lblLoanNumber);
        panelNorth.add(txtLoanNumber);
        panelNorth.add(btnSearch);
        
        panelCenter.add(lblStudentNumber);
        panelCenter.add(txtStudentNumber);
        txtStudentNumber.setEnabled(false);
        panelCenter.add(lblIsbn);
        panelCenter.add(txtIsbn); 
        txtIsbn.setEnabled(false);
        
        btnBack.addActionListener(this);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        
        panelSouth.add(btnBack);
        
        btnSearch.addActionListener(this);
        btnSearch.setBackground(Color.BLACK);
        btnSearch.setForeground(Color.WHITE);
        
        btnReturn.addActionListener(this);
        btnReturn.setBackground(Color.BLACK);
        btnReturn.setForeground(Color.WHITE);
        btnReturn.setEnabled(false);
        panelSouth.add(btnReturn);
      
        
        
        this.add(panelNorth);
        this.add(panelCenter);
        this.add(panelSouth);
        
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        
        this.setVisible(true);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
     public boolean isInputValid(){
        boolean valid = true;
        
        if(txtLoanNumber.getText().equals("")){
            valid =  false;
            lblLoanNumber.setForeground(Color.red);
       
        }else
            lblLoanNumber.setForeground(Color.black);
        
        if(txtStudentNumber.getText().equals("")){
            valid =  false;
            lblStudentNumber.setForeground(Color.red);
       
        }else
            lblStudentNumber.setForeground(Color.black);
        
        if(txtIsbn.getText().equals("")){
            valid =  false;
            lblIsbn.setForeground(Color.red);
       
        }else
            lblIsbn.setForeground(Color.black);
        
        
        return valid;
    }
    
    
    public boolean isLoanNumberValid(){
        boolean valid = true;
        
        if(txtLoanNumber.getText().equals("")){
            valid =  false;
            lblLoanNumber.setForeground(Color.red);
       
        }else
            lblLoanNumber.setForeground(Color.black);
        
        
        return valid;
    }
     
    public void resetLoanNumber(){
        txtLoanNumber.setText(null);
    }
    public void resetThisForm(){
        txtLoanNumber.setText(null);
        txtStudentNumber.setText(null);
        txtIsbn.setText(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
       if(e.getSource() == btnReturn){
            if(isInputValid()){                        
             
            Loan loan1 = new Loan(txtLoanNumber.getText(), txtStudentNumber.getText(), txtIsbn.getText());
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            DataOutputStream dataOutput = null;
            DataInputStream dataInput = null;
            Socket s = null;
            
            try{
            s = new Socket("localhost", 8500);            
            
            output = new ObjectOutputStream(s.getOutputStream());
            input = new ObjectInputStream(s.getInputStream());
            dataOutput = new DataOutputStream(s.getOutputStream());
            dataInput = new DataInputStream(s.getInputStream());
            
            dataOutput.writeInt(7);
            output.writeObject(loan1);
            dataOutput.flush();
            output.flush();
            
            boolean resetForm = dataInput.readBoolean();
            
            if(resetForm){
                resetThisForm();
                double num = input.readDouble();
                JOptionPane.showMessageDialog(null, "Book is returned. Penalty cost is: "+num);
            }else{
                JOptionPane.showMessageDialog(null, "This loan number already exist, use another loan number");
                resetLoanNumber();
                txtLoanNumber.requestFocus();
            }
 
            
        }catch(ConnectException ce){
        System.out.println("Error: "+ ce.getMessage());
        }catch(IOException io){
        System.out.println("Error: "+ io.getMessage());
        }catch(Exception exception){
        System.out.println("Error: "+ exception.getMessage());
        }finally{
                try{
                    if(s != null)
                        s.close();
                    if(output != null)
                        output.close();
                    if(dataOutput != null)
                        dataOutput.close();
                    if(input != null)
                        input.close();
                    if(dataInput != null)
                        dataInput.close();
                    System.out.println("Connection closed");
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
            }
           
    }
             
            
        }else if(e.getSource() == btnBack){
            MenuGUI m = new MenuGUI();
            m.setGUI();
            this.dispose();
        }else if(e.getSource() == btnSearch){
            if(isLoanNumberValid()){
            
            Loan loanNum = new Loan(txtLoanNumber.getText());
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            DataOutputStream dataOutput = null;
            DataInputStream dataInput = null;
            Socket s = null;
            
            try{
            s = new Socket("localhost", 5423);            
            
            output = new ObjectOutputStream(s.getOutputStream());
            input = new ObjectInputStream(s.getInputStream());
            dataOutput = new DataOutputStream(s.getOutputStream());
            dataInput = new DataInputStream(s.getInputStream());
            
            dataOutput.writeInt(6);  
            output.writeObject(loanNum);
            dataOutput.flush();
        
            
            boolean resetForm = dataInput.readBoolean();
            if(resetForm){
                    try{
                        returnBookList = (ArrayList<Loan>)input.readObject();
                    }catch(Exception ee){
                        ee.printStackTrace();
                    }
                    txtStudentNumber.setEnabled(true);
                    txtIsbn.setEnabled(true);
                    btnReturn.setEnabled(true);
      
            }else{
                JOptionPane.showMessageDialog(null, "This loan number doesn't exist, use another loan number");
                resetLoanNumber();
                txtLoanNumber.requestFocus();
            }
                
                for(int i=0;i<returnBookList.size();i++){
                    txtStudentNumber.setText(returnBookList.get(i).getStudentNumber());
                    txtIsbn.setText(returnBookList.get(i).getIsbn());
                }
                
                    
        }catch(ConnectException ce){
        System.out.println("Error: "+ ce.getMessage());
        }catch(IOException io){
        System.out.println("Error: "+ io.getMessage());
        }catch(Exception exception){
        System.out.println("Error: "+ exception.getMessage());
        }finally{
                try{
                    if(s != null)
                        s.close();
                    if(output != null)
                        output.close();
                    if(dataOutput != null)
                        dataOutput.close();
                    if(input != null)
                        input.close();
                    System.out.println("Connection closed");
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
            }
          }
        }
    }
    
}
