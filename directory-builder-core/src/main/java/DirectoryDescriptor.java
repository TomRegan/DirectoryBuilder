import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDescriptor
{
    private final FileFactory fileFactory;
    private final String name;
    private List<DirectoryDescriptor> children;

    private DirectoryDescriptor(String name, FileFactory fileFactory)
    {
        this.fileFactory = fileFactory;
        this.name = name;
    }

    public static DirectoryDescriptor createDirectoryDescriptor(String name, FileFactory fileFactory)
    {
        return new DirectoryDescriptor(name, fileFactory);
    }

    public void addChild(DirectoryDescriptor directoryDescriptor)
    {
        if (children == null)
        {
            children = new ArrayList<DirectoryDescriptor>();
        }
        children.add(directoryDescriptor);
    }

    public void create(File parentDirectory) throws IOException
    {
        File currentDirectory = fileFactory.createFile(parentDirectory, name);
        if (!currentDirectory.mkdir())
        {
            throw new IOException("could not create directory " + name);
        }
        if (children != null)
        {
            for (DirectoryDescriptor child : children)
            {
                child.create(currentDirectory);
            }
        }
    }

    public DirectoryDescriptor getLastChild()
    {
        return children != null
                ? children.get(children.size() - 1)
                : this;
    }
}
