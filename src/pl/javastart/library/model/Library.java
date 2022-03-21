package pl.javastart.library.model;

import pl.javastart.library.exception.PublicationAlreadyExistsException;
import pl.javastart.library.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;


public class Library implements Serializable {
   private  static final int INITIAL_CAPACITY = 1;
   private int publicationsNumber;
   private Map<String, Publication> publications = new HashMap<>();
   private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection <Publication> getSortedPublications(Comparator<Publication> comparator){
        ArrayList<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Collection <LibraryUser> getSortedUsers (Comparator<LibraryUser> comparator) {
        ArrayList<LibraryUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle()))
            throw new PublicationAlreadyExistsException("Publikcja o takim tytule już istnieje" + publication.getTitle());
        publications.put(publication.getTitle(), publication);
    }

    public boolean removePublication (Publication pub){
        if(publications.containsValue(pub)){
            publications.remove(pub.getTitle());
            return true;
        }
        else {
            return false;
        }
    }

    public void addMagazine(Magazine magazine) {
        addPublication(magazine);
    }

    public void addBook (Book book) {
        addPublication(book);
    }

    public void addUser(LibraryUser user) {
        if(users.containsKey(user.getPesel()))
            throw new UserAlreadyExistsException(
                    "Użytkownik ze wskazaniem peselem już istnieje" + user.getPesel());
        users.put(user.getPesel(), user);
    }

    @Override
    public String toString() {
        return "Library{" +
                "publicationsNumber=" + publicationsNumber +
                ", publications=" + publications +
                ", users=" + users +
                '}';
    }
}

