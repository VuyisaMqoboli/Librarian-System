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
import za.ac.cput.domain.Book;
import za.ac.cput.domain.Learner;
import za.ac.cput.domain.Loan;

public class DeleteLearnerGUI extends JFrame implements ActionListener,Serializable{
    private JPanel panelNorth, panelWest, panelCenter, panelSouth;
    private JLabel lblStudentNumber;
    private JTextField txtStudentNumber;
    public  JButton btnDelete,btnBack,btnSearch;
    
  
    
    public DeleteLearnerGUI(){
        super("Delete Learner");
        
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
         
        
        
        lblStudentNumber = new JLabel("Student number:");        
        
        txtStudentNumber = new JTextField(15);
        txtStudentNumber.requestFocus();
        
        btnBack = new JButton("Back");
        btnSearch = new JButton("Search");
        btnDelete = new JButton("Delete Learner");
  
    }
            
    
    
    public void setGUI(){

        panelNorth.setLayout(new FlowLayout());
        
        panelSouth.setLayout(new GridLayout(1, 2));
        
        
        panelNorth.add(lblStudentNumber);
        panelNorth.add(txtStudentNumber);
        panelNorth.add(btnSearch);
        
        panelCenter.add(lblStudentNumber);
        panelCenter.add(txtStudentNumber);
        
        
        btnBack.addActionListener(this);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        
        panelSouth.add(btnBack);
        
        btnSearch.addActionListener(this);
        btnSearch.setBackground(Color.BLACK);
        btnSearch.setForeground(Color.WHITE);
        
        btnDelete.addActionListener(this);
        btnDelete.setBackground(Color.BLACK);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setEnabled(false);
        panelSouth.add(btnDelete);
      
        
        
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
        
        if(txtStudentNumber.getText().equals("")){
            valid =  false;
            lblStudentNumber.setForeground(Color.red);
       
        }else
            lblStudentNumber.setForeground(Color.black);
        
        
        
        return valid;
    }
    
    
    public boolean isLoanNumberValid(){
        boolean valid = true;
        
        if(txtStudentNumber.getText().equals("")){
            valid =  false;
            lblStudentNumber.setForeground(Color.red);
       
        }else
            lblStudentNumber.setForeground(Color.black);
        
        
        return valid;
    }
     
    public void resetStudentNumber(){
        txtStudentNumber.setText(null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
       if(e.getSource() == btnDelete){
            if(isInputValid()){                        
             
            Learner learner = new Learner(txtStudentNumber.getText());
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
            
            dataOutput.writeInt(0);
            output.writeObject(learner);
            dataOutput.flush();
            output.flush();
            
            boolean resetForm = input.readBoolean();
            
            if(resetForm == true){
                resetStudentNumber();
                JOptionPane.showMessageDialog(null, "Learner is deleted successfully");
            }else{
                JOptionPane.showMessageDialog(null, "This student number doesn't exist, use another student number");
                resetStudentNumber();
                txtStudentNumber.requestFocus();
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
            
            Learner len = new Learner(txtStudentNumber.getText());
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
            
            dataOutput.writeInt(22);  
            output.writeObject(len);
            output.flush();
            dataOutput.flush();
        
            
            boolean resetForm = input.readBoolean();
            if(resetForm == false){
                JOptionPane.showMessageDialog(null, "This student number doesn't exist, use another student number");
                resetStudentNumber();
                txtStudentNumber.requestFocus();
      
            }else{
                btnDelete.setEnabled(true);
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

                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
            }
          }
        }
    }

}
