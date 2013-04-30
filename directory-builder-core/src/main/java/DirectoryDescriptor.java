import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDescriptor
{
    private String name;
    private List<DirectoryDescriptor> children;

    public static DirectoryDescriptor createDirectoryDescriptor()
    {
        return new DirectoryDescriptor();
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

    public void create(File parentDirectory)
    {
        File currentDirectory = new File(parentDirectory, name);
        currentDirectory.mkdirs();
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
