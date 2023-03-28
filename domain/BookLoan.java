
package za.ac.cput.domain;

import java.io.Serializable;


public class BookLoan implements Serializable{
    private String isbn;

    public BookLoan() {
    }

    public BookLoan(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "BookLoan{" + "isbn=" + isbn + '}';
    }
    
    
}
