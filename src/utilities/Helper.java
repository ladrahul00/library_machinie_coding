package utilities;

import java.util.List;
import java.util.UUID;

public class Helper
{
    public static long generateUniqueId()
    {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public static <T> T singleItemOrNull(List<T> objects)
    {
        if (objects.size() == 1)
            return objects.get(0);
        return null;
    }
}
