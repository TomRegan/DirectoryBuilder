import java.io.File;
import java.util.List;

public class DirectoryBuilder
{
    final File parent;

    private DirectoryBuilder(File parent)
    {
        this.parent = parent;
    }

    public static DirectoryBuilder createDirectoryBuilder(File rootDirectory)
    {
        return new DirectoryBuilder(rootDirectory);
    }

    public void createDirectoryStructure(List<DirectoryDescriptor> descriptors)
    {
        for (DirectoryDescriptor descriptor : descriptors)
        {
            new File(parent, descriptor.getName()).mkdirs();
        }
    }
}
