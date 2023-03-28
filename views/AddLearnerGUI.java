
package za.ac.cput.views;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import za.ac.cput.domain.*;

public class AddLearnerGUI extends JFrame implements ActionListener,Serializable {
    
    private JPanel  panelWest, panelCenter, panelSouth, panelBorrow;
    private JLabel lblStudentNumber, lblName, lblSurname,lblCanBorrow;
    private JTextField txtStudentNumber, txtName, txtSurname, txtCanBorrow;
    private JButton btnAdd,btnBack;
    private JRadioButton rbtnCanBorrow, rbtnCannotBorrow;
    private ButtonGroup btngBorrow;
 
    
    public AddLearnerGUI(){
       super("Learners");
        
        
        panelWest = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        panelBorrow = new JPanel();
        
        
        
        lblStudentNumber = new JLabel("Student No:");
        lblName = new JLabel("Name:");
        lblSurname = new JLabel("Surname:");
        lblCanBorrow = new JLabel("Borrow:");
        
        txtStudentNumber = new JTextField("");
        txtName = new JTextField("");
        txtSurname = new JTextField("");
        
        btngBorrow = new ButtonGroup();
        rbtnCanBorrow = new JRadioButton("Can borrow");
        rbtnCannotBorrow = new JRadioButton("Cannot borrow");
        
        btnBack = new JButton("Back");
        btnAdd = new JButton("Add");

    }
    
    public void setGUI(){
      
       
        panelCenter.setLayout(new GridLayout(8, 1));
        panelSouth.setLayout(new GridLayout(1, 2));
     
        panelCenter.add(lblStudentNumber);
        panelCenter.add(txtStudentNumber);
        
        panelCenter.add(lblName);
        panelCenter.add(txtName);
        
        panelCenter.add(lblSurname);
        panelCenter.add(txtSurname);
        
        panelCenter.add(lblCanBorrow);
        panelCenter.add(panelBorrow);
        
        
        
        
        btngBorrow.add(rbtnCanBorrow);
        rbtnCanBorrow.setSelected(true);
        btngBorrow.add(rbtnCannotBorrow);
        panelBorrow.add(rbtnCanBorrow);
        panelBorrow.add(rbtnCannotBorrow);
        rbtnCannotBorrow.setEnabled(false);
        
        
        btnBack.addActionListener(this);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        panelSouth.add(btnBack);
        btnAdd.addActionListener(this);
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        panelSouth.add(btnAdd);
        
        
        this.add(panelWest);
        this.add(panelCenter);
        this.add(panelSouth);
       
        this.add(panelWest, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        
        this.setVisible(true);
        this.setSize(500, 350);
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
        
        if(txtName.getText().equals("")){
            valid = false;
            lblName.setForeground(Color.red);
        }else{
            lblName.setForeground(Color.black);
        }
        
        if(txtSurname.getText().equals("")){
            valid = false;
            lblSurname.setForeground(Color.red);
        }else{
            lblSurname.setForeground(Color.black);
        }
        
        
        return valid;
    }
     
    public void resetForm(){
        txtStudentNumber.setText(null);
        txtName.setText(null);
        txtSurname.setText(null); 
        rbtnCanBorrow.setSelected(true);
    }
    public void resetIsbn(){
        txtStudentNumber.setText(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAdd){
            if(isInputValid()){
                
                Learner learner = new Learner(txtStudentNumber.getText(),
                                        txtName.getText(),
                                        txtSurname.getText(),
                                        rbtnCanBorrow.isSelected()?true:false);
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            DataOutputStream dataOutput = null;
            Socket s = null;
            
            try{
            s = new Socket("localhost", 8500);            
            
            output = new ObjectOutputStream(s.getOutputStream());
            input = new ObjectInputStream(s.getInputStream());
            dataOutput = new DataOutputStream(s.getOutputStream());
            
            dataOutput.writeInt(2);
            output.writeObject(learner);
            dataOutput.flush();
            output.flush();
            
            boolean resetForm = input.readBoolean();
            if(resetForm){
                resetForm();
                JOptionPane.showMessageDialog(null, "Successful! Learner added.");
            }else{
                JOptionPane.showMessageDialog(null, "This student number already exist, use another student number");
                resetIsbn();
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
        }
    }
}



