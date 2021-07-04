package store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import dao.Book;
import dao.Category;
import exceptions.BookAlreadyExist;
import exceptions.BookNotFound;
import utilities.Helper;

public class BookStore
{
    private final HashMap<Long, Book> mBookId2Book;
    private final HashMap<Long, HashSet<Long>> mAuthorId2BookIds;

    public BookStore()
    {
        mBookId2Book = new HashMap<>();
        mAuthorId2BookIds = new HashMap<>();
    }

    public void add(final Book book) throws BookAlreadyExist
    {
        if (book.getId() == null)
            book.setId(Helper.generateUniqueId());
        final Long bookId = book.getId();
        final Long authorId = book.getAuthorId();
        if (mBookId2Book.containsKey(bookId))
        {
            throw new BookAlreadyExist();
        }
        final HashSet<Long> bookIds = mAuthorId2BookIds.getOrDefault(
            authorId, new HashSet<>());
        bookIds.add(bookId);
        mBookId2Book.put(bookId, book);
        mAuthorId2BookIds.put(authorId, bookIds);
    }

    public List<Book> findAll()
    {
        return mBookId2Book.values().stream().collect(Collectors.toList());
    }

    public List<Book> findByName(String bookName)
    {
        return mBookId2Book.values().stream().filter(
            book -> book.getName().contains(bookName)).collect(Collectors.toList());
    }

    public List<Book> findByCategory(Category bookCategory)
    {
        return mBookId2Book.values().stream().filter(
            book -> book.getCategory().equals(bookCategory)).collect(Collectors.toList());
    }

    public Book get(final Long bookId ) throws Exception
    {
        if (bookId  == null)
            throw new BookNotFound();
        return mBookId2Book.get(bookId);
    }

    public Book mostExpensive()
    {
        return mBookId2Book.values().stream().max(
            Comparator.comparing(book -> book.getCost())).get();
    }

    public List<Book> mostRated()
    {
        final Book maxRatedBook = mBookId2Book.values().stream().max(
            Comparator.comparing(book -> book.getRating())).get();
        return mBookId2Book.values().stream().filter(
            book -> book.getRating() == maxRatedBook.getRating()).collect(Collectors.toList());
    }

    public Book leastExpensive()
    {
        return mBookId2Book.values().stream().min(
            Comparator.comparing(book -> book.getCost())).get();
    }

    public ArrayList<Book> findByAuthorId(final Long authorId)
    {
        final ArrayList<Book> authorBooks = new ArrayList<>();
        final HashSet<Long> bookIds = mAuthorId2BookIds.getOrDefault(authorId, new HashSet<>());
        for (final Long bookId : bookIds)
        {
            authorBooks.add(mBookId2Book.get(bookId));
        }
        return authorBooks;
    }

    public void remove(final Long bookId ) throws Exception
    {
        if (bookId  == null)
            throw new BookNotFound();
        final Book book = mBookId2Book.get(bookId);
        if (book == null) return;
        synchronized (this)
        {
            final Long authorId = book.getAuthorId();
            mBookId2Book.remove(bookId);
            final HashSet<Long> bookIds = mAuthorId2BookIds.get(authorId);
            bookIds.remove(bookId);
        }
    }

    public void update(final Book updatedBook) throws Exception
    {
        final Long bookId = updatedBook.getId();
        if (bookId  == null || !mBookId2Book.containsKey(bookId ))
            throw new BookNotFound();
        final Book book = mBookId2Book.get(bookId);
        mBookId2Book.put(bookId , updatedBook);
        if (book.getAuthorId() == updatedBook.getAuthorId())
        {
            return;
        }
        final Long previousAuthorId = book.getAuthorId();
        final Long updatedAuthorId = updatedBook.getAuthorId();
        final HashSet<Long> bookIds = mAuthorId2BookIds.get(previousAuthorId);
        bookIds.remove(bookId);
        mAuthorId2BookIds.put(previousAuthorId, bookIds);
        final HashSet<Long> bookIdsByUpdatedAuthor = mAuthorId2BookIds.getOrDefault(
            updatedAuthorId, new HashSet<>());
        bookIdsByUpdatedAuthor.add(bookId);
        mAuthorId2BookIds.put(updatedAuthorId, bookIdsByUpdatedAuthor);
    }
}
