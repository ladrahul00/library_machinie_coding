package dao;

public class Author
{
    private Long mId;
    private String mName;

    public Author(final String name)
    {
        mName = name;
    }

    public Long getId()
    {
        return mId;
    }

    public void setId(final Long id)
    {
        mId = id;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(final String name)
    {
        mName = name;
    }
}
