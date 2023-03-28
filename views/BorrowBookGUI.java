
package za.ac.cput.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import za.ac.cput.domain.BookLoan;
import za.ac.cput.domain.LearnerLoan;
import za.ac.cput.domain.Loan;

public class BorrowBookGUI extends JFrame implements ActionListener,Serializable{
    
    private JPanel panelCenter, panelSouth;
    private JLabel lblLoanNumber, lblStudentNumber, lblIsbn;
    private JTextField txtLoanNumber;
    public  JButton btnLoan,btnBack,btnRequestStudentNumber,btnRequestIsbn;
    private JComboBox cmbStudentNumber, cmbIsbn;
    private ImageIcon icon;
    private ArrayList<LearnerLoan> cmbStudentNumberList = new ArrayList<>();
    private ArrayList<BookLoan> cmbIsbnList = new ArrayList<>();
    
  
    
    public BorrowBookGUI(){
        super("Borrow Books");
        
     
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        
        lblLoanNumber = new JLabel("Loan number:");
        lblStudentNumber = new JLabel("Student number:");
        lblIsbn = new JLabel("Isbn:");         
        
        txtLoanNumber = new JTextField("");
        cmbStudentNumber = new JComboBox();
        cmbStudentNumber.setMaximumRowCount(100);
        cmbStudentNumber.setMinimumSize(new Dimension(0, 0));
        cmbStudentNumber.setMaximumSize(new Dimension(100, 100));
        cmbIsbn = new JComboBox(); 
        cmbIsbn.setMaximumRowCount(100);
        cmbIsbn.setMinimumSize(new Dimension(0, 0));
        cmbIsbn.setMaximumSize(new Dimension(100, 100));
        
        btnBack = new JButton("Back");
        btnLoan = new JButton("Loan");
        btnRequestIsbn = new JButton("Request Isbn");
        btnRequestStudentNumber = new JButton("Request Student number");
    }
            
    
    
    public void setGUI(){

       
        panelCenter.setLayout(new GridLayout(8, 1));
        panelSouth.setLayout(new GridLayout(1, 2));
             
        panelCenter.add(lblLoanNumber);
        panelCenter.add(txtLoanNumber);
        panelCenter.add(btnRequestStudentNumber);
        panelCenter.add(btnRequestIsbn);
        
        
        panelCenter.add(btnRequestStudentNumber);
        panelCenter.add(btnRequestIsbn);
        panelCenter.add(lblStudentNumber);
        panelCenter.add(cmbStudentNumber); 
        panelCenter.add(lblIsbn);
        panelCenter.add(cmbIsbn);
        
        btnBack.addActionListener(this);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        panelSouth.add(btnBack);
        btnLoan.addActionListener(this);
        btnLoan.setForeground(Color.WHITE);
        btnLoan.setBackground(Color.BLACK);
        panelSouth.add(btnLoan);
        btnRequestStudentNumber.addActionListener(this);
        btnRequestStudentNumber.setForeground(Color.WHITE);
        btnRequestStudentNumber.setBackground(Color.BLACK);
        btnRequestIsbn.addActionListener(this);
        btnRequestIsbn.setForeground(Color.WHITE);
        btnRequestIsbn.setBackground(Color.BLACK);
        
        this.add(panelCenter);
        this.add(panelSouth);
        this.setBackground(Color.GRAY);
        
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
        
        if(cmbIsbn.getItemCount() == 0){
            valid =  false;
            lblIsbn.setForeground(Color.red);
       
        }else
            lblIsbn.setForeground(Color.black);
        
        if(cmbStudentNumber.getItemCount() == 0){
            valid =  false;
            lblStudentNumber.setForeground(Color.red);
       
        }else
            lblStudentNumber.setForeground(Color.black);
        
        return valid;
    }
     
    public void resetLoanNumber(){
        txtLoanNumber.setText(null);
    }
    
    public void loan(int num,Loan loan){
        
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            Socket s = null;
            DataInputStream dataInput = null;
            DataOutputStream dataOutput = null;
            
       boolean connected = true;
       while(connected){         
        try{
              
            s = new Socket("localhost", 8500);
            
            input  = new ObjectInputStream(s.getInputStream());
            output = new ObjectOutputStream(s.getOutputStream());         
            dataOutput = new DataOutputStream(s.getOutputStream());
            dataInput = new DataInputStream(s.getInputStream());
            
            dataOutput.write(num);
            output.writeObject(loan);
            dataOutput.flush();
            output.flush();
            
            Thread.sleep(10000);
            
            boolean resetForm = input.readBoolean();
            
            if(resetForm){
                resetLoanNumber();
                JOptionPane.showMessageDialog(null, "Book loan is successful! ");
            }else{
                JOptionPane.showMessageDialog(null, "This loan number already exist, use another loan number");
                resetLoanNumber();
                txtLoanNumber.requestFocus();
            }
          dataOutput.flush();
          output.flush();
        }catch(ConnectException ce){
        System.out.println("Error: "+ ce.getMessage());
        }catch(IOException io){
        System.out.println("Error: "+ io.getMessage());
           io.printStackTrace();
           connected = false;
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
                
                
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
                 
            }
       }
    }
    
    public void requestIsbn(int num){
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
            
            dataOutput.writeInt(num);  
            dataOutput.flush();
        
            
            try{
                cmbIsbnList = (ArrayList<BookLoan>)input.readObject();
            }catch(Exception ee){
                ee.printStackTrace();
            }
                for(int i=0;i<cmbIsbnList.size();i++){
                cmbIsbn.addItem(cmbIsbnList.get(i).getIsbn());
                }
                
                
          output.flush();
          dataOutput.flush();
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
                
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
                 
            }
    }
    
    public void requestStudentNumber(int num){
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
            
            dataOutput.writeInt(num);  
            dataOutput.flush();
            
            
            try{
                cmbStudentNumberList = (ArrayList<LearnerLoan>)input.readObject();
            }catch(Exception ee){
                ee.printStackTrace();
            }
                for(int i=0;i<cmbStudentNumberList.size();i++){
                cmbStudentNumber.addItem(cmbStudentNumberList.get(i).getStudentNumber());
                }
 
                 output.flush();
                 dataOutput.flush();
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
                
                }catch(IOException io2){
                    System.out.println("Error: "+io2.getMessage());
                }
                 
            }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnLoan){
            if(isInputValid()){
                          
            Date date = Calendar.getInstance().getTime(); 
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 7); 
            Date date2 = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);  

            String issuedDate = dateFormat.format(date);  
            String dueDate = dateFormat.format(date2);
            double totalPenaltyCost = 0.0;

            Loan loan = new Loan(txtLoanNumber.getText(),
                                 cmbStudentNumber.getSelectedItem().toString(),
                                 cmbIsbn.getSelectedItem().toString(),issuedDate, dueDate,totalPenaltyCost);
            loan(5, loan);
            
           
        }
      
        }else if(e.getSource() == btnBack){
            MenuGUI m = new MenuGUI();
            m.setGUI();
            this.dispose();
        }else if(e.getSource() == btnRequestStudentNumber){   
            requestStudentNumber(3);               
        }else if(e.getSource() == btnRequestIsbn){          
            requestIsbn(4);
        }
    }
}
