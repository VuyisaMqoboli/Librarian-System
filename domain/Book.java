
package za.ac.cput.domain;

import java.io.Serializable;

public class Book implements Serializable{
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int shelfNumber;
    private boolean availableForLoan;
    
    public Book(){ }
    
    public Book(String isbn){
        this.isbn = isbn;
    }

    public Book(String isbn, String title, String author, String category, int shelfNumber, boolean availableForLoan) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.shelfNumber = shelfNumber;
        this.availableForLoan = availableForLoan;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public boolean isAvailableForLoan() {
        return availableForLoan;
    }

    public void setAvailableForLoan(boolean availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn=" + isbn + ", title=" + title + ", author=" + author + ", category=" + category + ", shelfNumber=" + shelfNumber + ", availableForLoan=" + availableForLoan + '}';
    }

    
    
    
}
