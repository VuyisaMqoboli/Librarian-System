
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
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MenuGUI extends JFrame implements ActionListener{
    private JPanel panelCenter, panelSouth;
    private JButton btnAddBook,btnAddLearner,btnBorrowBook,btnReturnBook, btnDeleteLearner,btnLogout;
    
    
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    DataInputStream dataInput = null;
    DataOutputStream dataOutput = null;
    Socket s = null;

    public MenuGUI(){
        super("Menu");
        
        panelCenter = new JPanel();
        panelSouth = new JPanel();
    
        btnBorrowBook = new JButton("Borrow book");
        btnReturnBook = new JButton("Return book");
        btnAddBook = new JButton("Add book to Database");
        btnAddLearner = new JButton("Add learner to Database");
        btnDeleteLearner = new JButton("Delete learner");
        btnLogout = new JButton("Exit");
    }
    
    public void setGUI(){
   
       
        panelCenter.setLayout(new GridLayout(6, 1));
        
        
        panelCenter.add(btnBorrowBook);       
        panelCenter.add(btnReturnBook);
        panelCenter.add(btnAddLearner);
        panelCenter.add(btnAddBook);
        panelCenter.add(btnDeleteLearner);
        
        btnDeleteLearner.addActionListener(this);
        btnDeleteLearner.setBackground(Color.LIGHT_GRAY);
        panelCenter.add(btnDeleteLearner);
        btnAddBook.addActionListener(this);
        btnAddBook.setBackground(Color.LIGHT_GRAY);
        panelCenter.add(btnBorrowBook);
        btnAddLearner.addActionListener(this);
        btnAddLearner.setBackground(Color.LIGHT_GRAY);
        panelCenter.add(btnReturnBook);
        btnBorrowBook.addActionListener(this);
        btnBorrowBook.setBackground(Color.LIGHT_GRAY);
        panelCenter.add(btnAddLearner);
        btnReturnBook.addActionListener(this);
        btnReturnBook.setBackground(Color.LIGHT_GRAY);
        panelCenter.add(btnAddBook);
        panelCenter.add(btnLogout);
        
        btnLogout.setBackground(Color.BLACK);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(this);
        
      
        this.add(panelCenter);
        this.add(panelSouth);
       
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        
        this.setVisible(true);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelCenter.setPreferredSize(new Dimension(300,200));
        this.setLocationRelativeTo(null);
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnAddLearner){
           AddLearnerGUI al = new AddLearnerGUI();
           al.setGUI();
           this.dispose();
        }else if(e.getSource() == btnAddBook){
            AddBookGUI ab = new AddBookGUI();
            ab.setGUI();
            this.dispose();
        }else if(e.getSource() == btnBorrowBook){
            BorrowBookGUI bb = new BorrowBookGUI();
            bb.setGUI();
            this.dispose();
        }else if(e.getSource() == btnReturnBook){
            ReturnBookGUI rb = new ReturnBookGUI();
            rb.setGUI();
            this.dispose();
          
        }else if(e.getSource() == btnDeleteLearner){
            DeleteLearnerGUI d = new DeleteLearnerGUI();
            d.setGUI();
            this.dispose();
        }else if(e.getSource() == btnLogout){
            System.exit(0);
                          
       }
    }
}
