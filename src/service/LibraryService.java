package service;

import java.util.List;

import dao.Author;
import dao.Book;
import dao.Category;
import exceptions.AuthorNotFound;
import exceptions.BookAlreadyExist;
import store.AuthorStore;
import store.BookStore;
import utilities.Helper;

public class LibraryService
{
    private final AuthorStore mAuthorStore;
    private final BookStore mBookStore;

    public LibraryService()
    {
        mAuthorStore = new AuthorStore();
        mBookStore = new BookStore();
    }

    public Author addAuthor(final String name)
    {
        final Author newAuthor = new Author(name);
        mAuthorStore.add(newAuthor);
        return newAuthor;
    }

    public Book addBook(
        final String bookName, final Category bookCategory, final String authorName,
        final double cost, final float rating) throws BookAlreadyExist
    {
        final Author author = Helper.singleItemOrNull(mAuthorStore.findByName(authorName));
        if (author == null)
        {
            throw new AuthorNotFound();
        }
        final Book newBook = new Book(bookName, bookCategory, author.getId(), cost, rating);
        mBookStore.add(newBook);
        return newBook;
    }

    public List<Book> booksByAuthor(final String authorName)
    {
        final Author author = Helper.singleItemOrNull(mAuthorStore.findByName(authorName));
        if (author == null)
        {
            throw new AuthorNotFound();
        }
        return mBookStore.findByAuthorId(author.getId());
    }

    public List<Book> booksInCategory(final Category category)
    {
        return  mBookStore.findByCategory(category);
    }

    public Book mostExpensiveBook()
    {
        return mBookStore.mostExpensive();
    }

    public Book leastExpensive()
    {
        return mBookStore.leastExpensive();
    }

    public List<Book> mostRated()
    {
        return mBookStore.mostRated();
    }
}
