package pl.javastart.library.model;

import java.util.Collection;

public class ConsolePrinter {
    public void printBooks (Collection <Publication> publications) {
        int counter = 0;
        for (Publication publication: publications) {
            if(publication instanceof Book) {
                printLine(publication.toString());
                counter++;
            }
        }
        if (counter == 0)
            printLine("Brak magazynów w bibliotece");
    }

    private void printLine(String text) {
        System.out.println(text);
    }

    public void printMagazines (Collection<Publication> publications) {
        int counter = 0;
        for (Publication publication: publications) {
            if(publication instanceof Magazine) {
                printLine(publication.toString());
                counter++;
            }
        }
        if (counter == 0)
            printLine("Brak magazynów w bibliotece");
    }
}
