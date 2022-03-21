package pl.javastart.library.io;

import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.LibraryUser;
import pl.javastart.library.model.Magazine;
import java.util.Scanner;

public class DataReader {
    private Scanner input = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader (ConsolePrinter printer) {
        this.printer = printer;
    }

    public String getString() {
        return input.nextLine();
    }

    public void close(){
        input.close();
    }

    public int getInt() {
        try {
           return input.nextInt();
        }finally {
            input.nextLine();
        }
    }
// pobierz dane i stwórz książke
   public Book readAndCreateBook(){
        printer.printLine("Tytuł: ");
       String title = input.nextLine();
       printer.printLine("Autor: ");
       String author = input.nextLine();
       printer.printLine("Wydawnictwo: ");
       String publischer = input.nextLine();
       printer.printLine("ISBN: ");
       String isbn = input.nextLine();
       printer.printLine("Rok wydania: ");
       int realseDate = input.nextInt();
       printer.printLine("Ilość stron: ");
       int pages = input.nextInt();
       input.nextLine();

       return new Book (title,author,realseDate,pages,publischer,isbn);
   }
// pobierz dane i stwórz magazyn
   public Magazine readAndCreateMagazine() {
       printer.printLine("Tytuł: ");
       String title = input.nextLine();
       printer.printLine("Wydawnictwo: ");
       String publisher = input.nextLine();
       printer.printLine("Język: ");
       String language = input.nextLine();
       printer.printLine("Rok wydania: ");
       int year = getInt();
       printer.printLine("Miesiąc: ");
       int month = getInt();
       printer.printLine("Dzień: ");
       int day = getInt();

       return new Magazine(title, publisher, language, year, month,day);
   }

   public LibraryUser createLibraryUser() {
        printer.printLine("Imię");
        String firsName = input.nextLine();
        printer.printLine("Nazwisko");
        String lastName = input.nextLine();
        printer.printLine("Pesel");
        String pesel = input.nextLine();

        return new LibraryUser(firsName, lastName, pesel);
   }


}
