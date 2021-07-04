import java.util.List;

import dao.Author;
import dao.Book;
import dao.Category;
import service.LibraryService;

public class Main {

    public static void main(String[] args) {
        final LibraryService service = new LibraryService();
        final String authorName = "Rahul";
        final Author rahul = service.addAuthor(authorName);
        service.addAuthor("Pratik");
        try
        {
            service.addBook("Book one", Category.ACTION, authorName, 99, 3);
        }
        catch (final Exception exception)
        {
            throw exception;
        }
        try
        {
            service.addBook("Book two", Category.ACTION, authorName, 121, 3);
        }
        catch (final Exception exception)
        {
            throw exception;
        }
        try
        {
            service.addBook("Book three", Category.ACTION, "Pratik", 999, 4);
        }
        catch (final Exception exception)
        {
            throw exception;
        }
        final Book mostExpensive = service.mostExpensiveBook();
        System.out.println(mostExpensive.getName() + " :: " + mostExpensive.getCost());

        final List<Book> mostRated = service.mostRated();
        mostRated.stream().map(
            book -> book.getName() + " :: " + book.getRating()).forEach(
                s -> System.out.println(s));

        final List<Book> booksByRahul = service.booksByAuthor(authorName);
        System.out.println("Books by Rahul: " + booksByRahul.size());
        booksByRahul.stream().forEach(book -> System.out.println(book.getName()));
    }
}
