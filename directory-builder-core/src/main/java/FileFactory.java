import java.io.File;

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
}
