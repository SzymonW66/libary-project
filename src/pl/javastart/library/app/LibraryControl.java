package pl.javastart.library.app;
import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.UserAlreadyExistsException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.file.FileManager;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.model.*;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.comparator.AlphabeticalTitlecComparator;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class LibraryControl {


private ConsolePrinter printer = new ConsolePrinter();
private DataReader dataReader = new DataReader(printer);
private FileManager fileManager;


private Library library;

LibraryControl() {
    fileManager = new FileManagerBuilder(printer, dataReader).build();
    try {
        library = fileManager.importData();
        printer.printLine("Zaimportowano dane z pliku");
    } catch (DataImportException e ) {
        printer.printLine(e.getMessage());
        printer.printLine("Zainicjowano nową bazę");
        library = new Library();
    }
}

    private static int compare(Publication p1, Publication p2) {
        return p1.getTitle().compareToIgnoreCase(p2.getTitle());
    }

    private static int compare(LibraryUser p1, LibraryUser p2) {
        return p1.getLastName().compareToIgnoreCase(p2.getLastName());
    }

    //pętla do wybierania opcji
    public void controlLoop() {
        Option option;

        do{
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_MAGAZINES:
                    printMagazine();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUser();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie");

            }
        } while (option != Option.EXIT);

    }
//Pokazanie użytkowników
    private void printUser() {
        printer.printUsers(library.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

// dodatnie użytkownika
    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }
//wypisanie możliwych opcji wyboru (numerki i co robią)
    private Option getOption () {
        boolean optionOK = false;
        Option option = null;
        while (!optionOK) {
            try {
                option = Option.createFrotmInt(dataReader.getInt());
                optionOK = true;
            } catch (InputMismatchException i) {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą podaj ponownie");
            } catch (NoSuchElementException e) {
                printer.printLine(e.getMessage() + ", podaj ponownie");
            }
        }
        return option;
    }


// wypisanie dostępnych spisu dostępnych opcji
private void printOptions(){
    for (Option option: Option.values())
        System.out.println(option);
}
//dodanie książki
private void addBook () {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addBook(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e ){
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki");
        }

    }
    //dodoanie magazynu
private void addMagazine () {
        try{
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addMagazine(magazine);
        }catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, nie poprawne dane");
        }catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu");
        }

}
// wypisanie ksiązki i magazynu
private void printBooks() {
    printer.printBooks(library.getSortedPublications(
            Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER))
    );

}
    private void printMagazine() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }
//usunięcie magazynu
private void deleteMagazine() {
    try{
        Magazine magazine = dataReader.readAndCreateMagazine();
        if (library.removePublication(magazine)){
            printer.printLine("Usunięto magazyn");
        }
        else
            printer.printLine("Brak wskazanego magazynu");
    }catch (InputMismatchException e){
        printer.printLine("Nie udało się usunąć magazynu, nie poprawne dane");
    }
}
//usunięcie ksiązki
private void deleteBook () {
    try{
        Book book = dataReader.readAndCreateBook();
        if (library.removePublication(book)){
            printer.printLine("Usunięto książke");
        }
        else
            printer.printLine("Brak wskazanej książki ");
    } catch (InputMismatchException e) {
        printer.printLine("Nie udało sie usunąć ksiązki, nie poprawne dane");
    }
}
//wyjście z programu
    private void exit () {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e ){
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("Koniec programu, bye bye!");
    }
//enum z mozliwymi opcjami
    private enum Option {
        EXIT (0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodanie książki"),
        ADD_MAGAZINE (2, "Dodanie magazynu/ gazety"),
        PRINT_BOOKS(3, "Wyświetlenie dostępnych książek"),
        PRINT_MAGAZINES(4, "Wyświetlanie dostepnych magazynów/ gazet"),
        DELETE_BOOK(5, "Usuń książkę "),
        DELETE_MAGAZINE(6, " Usuń magazyn"),
        ADD_USER(7, "Dodaj czytelnika"),
        PRINT_USERS(8, "Wyświetl czytelników");

        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }
//nadpisanie to string z wyrzuceniem wartości i opisu
        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFrotmInt (int option) throws NoSuchElementException
        {
            try{
                return Option.values()[option];
            }catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException ("Brak opcji o id: " + option);
            }

        }
    }
}