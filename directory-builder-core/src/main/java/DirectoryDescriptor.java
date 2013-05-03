import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDescriptor
{
    private final FileFactory fileFactory;
    private String name;
    private List<DirectoryDescriptor> children;

    private DirectoryDescriptor(FileFactory fileFactory)
    {
        this.fileFactory = fileFactory;
    }

    public static DirectoryDescriptor createDirectoryDescriptor(FileFactory fileFactory)
    {
        return new DirectoryDescriptor(fileFactory);
    }

    public void setName(String name)
    {
        this.name = name;
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
