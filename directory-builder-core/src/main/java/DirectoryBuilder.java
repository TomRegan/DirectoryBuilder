import java.io.File;

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

    public void createDirectoryStructure(DirectoryDescriptor... descriptors)
    {
        for (DirectoryDescriptor descriptor : descriptors)
        {
            descriptor.create(rootDirectory);
        }
    }
}
