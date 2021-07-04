package dao;

public class Book
{
    private Long mId;
    private String mName;
    private Category mCategory;
    private Long mAuthorId;
    private Double mCost;
    private Float mRating;

    public Book(
        final String name, final Category category, final Long authorId, final Double cost,
        final Float rating)
    {
        mName = name;
        mCategory = category;
        mAuthorId = authorId;
        mCost = cost;
        mRating = rating;
    }

    public Double getCost()
    {
        return mCost;
    }

    public void setCost(final Double cost)
    {
        mCost = cost;
    }

    public Float getRating()
    {
        return mRating;
    }

    public void setRating(final Float rating)
    {
        mRating = rating;
    }

    public Long getId()
    {
        return mId;
    }

    public void setId(final Long bookId)
    {
        mId = bookId;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(final String name)
    {
        mName = name;
    }

    public Category getCategory()
    {
        return mCategory;
    }

    public void setCategory(final Category category)
    {
        mCategory = category;
    }

    public Long getAuthorId()
    {
        return mAuthorId;
    }

    public void setAuthorId(final Long authorId)
    {
        mAuthorId = authorId;
    }
}
