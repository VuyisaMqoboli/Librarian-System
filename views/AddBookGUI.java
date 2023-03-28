
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
import java.util.ArrayList;
import za.ac.cput.domain.Book;


public class AddBookGUI extends JFrame implements ActionListener,Serializable {
    private JPanel  panelWest, panelCenter, panelSouth, panelAvailable;
    private JLabel  lblTitle, lblAuthor, lblIsbn, lblCategory, lblShelfNumber, lblAvailableForLoan;
    private JTextField txtAuthor, txtTitle, txtIsbn, txtShelfNumber;
    public  JButton btnAdd,btnBack;
    private JComboBox cmbCategory;
    private JRadioButton radAvailableForLoan, radUnavailableForLoan;
    private ButtonGroup btnAvailableButton;
    
   

    public AddBookGUI(){
        super("Books");
        
       
        panelWest = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        panelAvailable = new JPanel();
        
        
        
        lblIsbn = new JLabel("Isbn:");
        lblTitle = new JLabel("Title:");
        lblAuthor = new JLabel("Author:");        
        lblCategory = new JLabel("Category:");
        lblShelfNumber = new JLabel("Shelf number:");
        lblAvailableForLoan = new JLabel("Available for loan:");
        
        txtIsbn = new JTextField("");
        txtTitle = new JTextField("");
        txtAuthor = new JTextField("");     
        txtShelfNumber = new JTextField("");
        
        
        String[] categories = {"Thriller", "Horror", "Romance", "Fantasy", "Fairy Tales", "Comics", "Comedy", "Historical", "Dystopian","Fiction","Science-Fiction"};
        cmbCategory = new JComboBox(categories);
        
        btnAvailableButton = new ButtonGroup();
        radAvailableForLoan = new JRadioButton("Available");
        radUnavailableForLoan = new JRadioButton("Unavailable");
        
        btnBack = new JButton("Back");
        btnAdd = new JButton("Add");
  
    }
    
    
    public void setGUI(){
   

        panelCenter.setLayout(new GridLayout(12, 1));
        panelSouth.setLayout(new GridLayout(1, 2));
  
        
        panelCenter.add(lblIsbn);
        panelCenter.add(txtIsbn);
        panelCenter.add(lblTitle);  
        panelCenter.add(txtTitle);
        panelCenter.add(lblAuthor);
        panelCenter.add(txtAuthor);
        panelCenter.add(lblCategory);
        panelCenter.add(cmbCategory);
        panelCenter.add(lblShelfNumber);
        panelCenter.add(txtShelfNumber);
        panelCenter.add(lblAvailableForLoan);
        panelCenter.add(panelAvailable);
        
        btnAvailableButton.add(radAvailableForLoan);
        radAvailableForLoan.setSelected(true);
        btnAvailableButton.add(radUnavailableForLoan);
        radUnavailableForLoan.setEnabled(false);
        panelAvailable.add(radAvailableForLoan);
        panelAvailable.add(radUnavailableForLoan);
        
        
        btnBack.addActionListener(this);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        panelSouth.add(btnBack);
        btnAdd.addActionListener(this);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.BLACK);
        panelSouth.add(btnAdd);
        
        
        this.add(panelWest);
        this.add(panelCenter);
        this.add(panelSouth);
       
        this.add(panelWest, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        
        this.setVisible(true);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
     public boolean isInputValid(){
        boolean valid = true;
        
        if(txtIsbn.getText().equals("")){
            valid =  false;
            lblIsbn.setForeground(Color.red);
       
        }else
            lblIsbn.setForeground(Color.black);
        
        if(txtTitle.getText().equals("")){
            valid = false;
            lblTitle.setForeground(Color.red);
        }else{
            lblTitle.setForeground(Color.black);
        }
        
        if(txtAuthor.getText().equals("")){
            valid = false;
            lblAuthor.setForeground(Color.red);
        }else{
            lblAuthor.setForeground(Color.black);
        }
        
        if(txtShelfNumber.getText().equals("")){
            valid = false;
            lblShelfNumber.setForeground(Color.red);
        }else{
            lblShelfNumber.setForeground(Color.black);
        }
        
        return valid;
    }
     
    public void resetForm(){
        txtIsbn.setText(null);
        txtTitle.setText(null);
        txtAuthor.setText(null); 
        txtShelfNumber.setText(null);
        radAvailableForLoan.setSelected(true);
    }
    public void resetIsbn(){
        txtIsbn.setText(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAdd){
            if(isInputValid()){
            Book b = new Book(txtIsbn.getText(),
                                  txtTitle.getText(),
                                  txtAuthor.getText(), cmbCategory.getSelectedItem().toString(),Integer.valueOf(txtShelfNumber.getText()),
                                  radAvailableForLoan.isSelected()?true:false);
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            DataOutputStream dataOutput = null;
            Socket s = null;
            
            try{
            s = new Socket("localhost", 8500);            
            
            output = new ObjectOutputStream(s.getOutputStream());
            input = new ObjectInputStream(s.getInputStream());
            dataOutput = new DataOutputStream(s.getOutputStream());
            
            dataOutput.writeInt(1);
            output.writeObject(b);
            dataOutput.flush();
            output.flush();
            
            boolean resetForm = input.readBoolean();
            if(resetForm){
                resetForm();
                JOptionPane.showMessageDialog(null, "Successful! Book added.");
            }else{
                JOptionPane.showMessageDialog(null, "This isbn already exist, use another isbn");
                resetIsbn();
                txtIsbn.requestFocus();
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

