import java.io.File;
import java.io.IOException;

public class FileFactory
{
    public static FileFactory getFileFactory()
    {
        return new FileFactory();
    }

    public File createFile(File parentDirectory, String name)
    {
        return new File(parentDirectory, name);
    }

    public IOException createException(String message)
    {
        return new IOException(message);
    }
}
