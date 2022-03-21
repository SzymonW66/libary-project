package pl.javastart.library.io.file;

import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.InvalidDataException;
import pl.javastart.library.model.*;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class CsvFileManager implements FileManager {
    //nazwy finalne dla plików
    private static final String PUBLICATIONS_FILE_NAME = "Library.csv";
    private static final String USERS_FILE_NAME = "Library_users.csv";
//implementacja exportu danych z Interfesju FileManager
    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }
//implementacja importu danych z interfejsu FileManager
    @Override
    public Library importData() {
       Library library = new Library();
       importPublications(library);
       importUsers(library);
       return library;
    }
//metoda do exportu Publikacji
    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
       exportToCsv(publications, PUBLICATIONS_FILE_NAME);
    }
//metoda do exportu Użytkowników
    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }
// metoda do eksportu do CSV
    private <T extends CsvConvertible> void exportToCsv (Collection<T> collection, String fileName){
        try(
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ){
            for (T element: collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }

//Stworzenie obiektu ze Stringu (pocięcie danych do odopowiednich ugrupowań
    private Publication createObjectFromString (String csvText) {
        String[] split = csvText.split(";");
        String type = split[0];
        if (Book.TYPE.equals(type)){
            return createBook (split);
        }
        else if (Magazine.TYPE.equals(type)){
            return createMagazine(split);
        }
        throw new InvalidDataException("Nienznay typ publikacji " + type);
    }

// przypisanie pociętych danych ksiązki do ugrupowania
    private Book createBook (String[]data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.valueOf(data[3]);
        String author = data[4];
        int pages = Integer.valueOf(data[5]);
        String isbn = data[6];
        return new Book(title, author, year, pages, publisher, isbn);
    }
    // przypisanie pociętych danych Magazynu do ugrupowania
    private Magazine createMagazine(String [] data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.valueOf(data[3]);
        int month = Integer.valueOf(data[4]);
        int day = Integer.valueOf(data[5]);
        String language = data[6];
        return new Magazine(title, publisher,language ,year, month, day );
    }

    private void importPublications(Library library) {
        try (Scanner fileReader = new Scanner(new File(PUBLICATIONS_FILE_NAME))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + PUBLICATIONS_FILE_NAME);
        }
    }

    private void importUsers(Library library) {
        try (Scanner fileReader = new Scanner(new File(USERS_FILE_NAME))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                LibraryUser libUser = createUserFromString(line);
                library.addUser(libUser);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        }
    }

    private LibraryUser createUserFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new LibraryUser(firstName, lastName, pesel);
    }
}

