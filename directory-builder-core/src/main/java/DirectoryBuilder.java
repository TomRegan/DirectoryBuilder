import java.io.File;
import java.io.IOException;

public class DirectoryBuilder
{
    final File rootDirectory;

    private DirectoryBuilder(File rootDirectory)
    {
        this.rootDirectory = rootDirectory;
    }

    public static DirectoryBuilder createDirectoryBuilder(File rootDirectory)
    {
        return new DirectoryBuilder(rootDirectory);
    }

    public void createDirectoryStructure(DirectoryDescriptor... descriptors) throws IOException
    {
        for (DirectoryDescriptor descriptor : descriptors)
        {
            descriptor.create(rootDirectory);
        }
    }
}
