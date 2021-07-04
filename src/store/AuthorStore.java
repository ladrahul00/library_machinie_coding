package store;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dao.Author;
import exceptions.AuthorNotFound;
import utilities.Helper;

public class AuthorStore
{
    private final HashMap<Long, Author> mAuthorId2Author;

    public AuthorStore()
    {
        mAuthorId2Author = new HashMap<>();
    }

    public void add(final Author author)
    {
        if (author.getId() == null)
            author.setId(Helper.generateUniqueId());
        mAuthorId2Author.put(author.getId(), author);
    }

    public List<Author> findAll()
    {
        return mAuthorId2Author.values().stream().collect(Collectors.toList());
    }

    public List<Author> findByName(String authorName)
    {
        return mAuthorId2Author.values().stream().filter(
            author -> author.getName().equals(authorName)).collect(Collectors.toList());
    }

    public Author get(final Long authorId) throws Exception
    {
        if (authorId == null)
            throw new AuthorNotFound();
        return mAuthorId2Author.get(authorId);
    }

    public void remove(final Long authorId) throws Exception
    {
        if (authorId == null)
            throw new AuthorNotFound();
        mAuthorId2Author.remove(authorId);
    }

    public void update(final Author updatedAuthor) throws Exception
    {
        final Long authorId = updatedAuthor.getId();
        if (authorId == null || !mAuthorId2Author.containsKey(authorId))
            throw new AuthorNotFound();
        mAuthorId2Author.put(authorId, updatedAuthor);
    }

}
