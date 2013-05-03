import java.io.File;

public class FileFactory
{
    public static FileFactory createFileFactory()
    {
        return new FileFactory();
    }

    public File createFile(File parentDirectory, String name)
    {
        return new File(parentDirectory, name);
    }
}
