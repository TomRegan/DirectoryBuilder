public class DirectoryDescriptor
{
    private String name;

    public static DirectoryDescriptor createDirectoryDescriptor()
    {
        return new DirectoryDescriptor();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void addChild(DirectoryDescriptor directoryDescriptor)
    {

    }
}
