package pl.javastart.library.model;

import java.util.Objects;

public class Book extends Publication {
//pola
public static final String TYPE = "Książka";
private String author;
private int pages;
private String isbn;

//konstruktory


public Book (String title, String author, int releaseDate, int pages, String publisher, String isbn){
super(releaseDate, publisher, title );
this.author = author;
this.pages = pages;
this.isbn = isbn;

}
// gettery i settery

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    //wypisanie informacji


    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return pages == book.pages &&
                author.equals(book.author) &&
                isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, pages, isbn);
    }

    @Override
    public String toCsv() {
        return (TYPE + ";") + getTitle() + ";" + getPublisher() + ";" + getYear() + ";" + author + ";" + pages + ";" + isbn + "";
    }
}
