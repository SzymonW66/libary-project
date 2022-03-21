package pl.javastart.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryUser extends User {
    //stworzenie list z historią publikacji i wypożyczonymi publikacjami
    private List<Publication> publicationHistory = new ArrayList<>();
    private List<Publication> borrowedPublication = new ArrayList<>();
//gettery i settery do tablic
    public List<Publication> getPublicationHistory() {
        return publicationHistory;
    }

    public List<Publication> getBorrowedPublication() {
        return borrowedPublication;
    }
    //Konstruktor dla użytkownika biblioteki
    public LibraryUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    private void  addPublicationToHistory(Publication pub) {
        publicationHistory.add(pub);
    }

    private void borrowPublication (Publication pub) {
        borrowedPublication.add(pub);
    }

    public boolean returnPublication (Publication pub) {
        boolean result = false;
        if (borrowedPublication.remove(pub)){
            result = true;
            addPublicationToHistory(pub);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryUser)) return false;
        if (!super.equals(o)) return false;
        LibraryUser that = (LibraryUser) o;
        return Objects.equals(publicationHistory, that.publicationHistory) && Objects.equals(borrowedPublication, that.borrowedPublication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publicationHistory, borrowedPublication);
    }
}
