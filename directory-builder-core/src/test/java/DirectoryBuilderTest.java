import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DirectoryBuilderTest
{

    private DirectoryBuilder directoryBuilder;
    private File rootDirectory;

    private List<DirectoryDescriptor> getDirectoryDescriptors(String[] ... directories)
    {
        List<DirectoryDescriptor> descriptors = new ArrayList<DirectoryDescriptor>();
        for (String[] branch : directories)
        {
            for (int i = 0; i < branch.length; ++i)
            {
                String directory = branch[i];
                DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor();
                directoryDescriptor.setName(directory);
                if (i == 0)
                {
                    descriptors.add(directoryDescriptor);
                }
                else
                {
                    DirectoryDescriptor parentDescriptor = descriptors.get(descriptors.size() - 1);
                    parentDescriptor.addChild(directoryDescriptor);
                }
            }
        }
        return descriptors;
    }

    @Before
    public void setUp() throws Exception
    {
        rootDirectory = Files.createTempDir();
        directoryBuilder = DirectoryBuilder.createDirectoryBuilder(rootDirectory);
    }

    @Test
    public void shouldCreateDirectoriesGivenDescriptor()
    {
        List<DirectoryDescriptor> descriptors = getDirectoryDescriptors(new String[]{"foo"}, new String[]{"bar"});
        directoryBuilder.createDirectoryStructure(descriptors);
        boolean expectedFoo = new File(rootDirectory, "foo").isDirectory();
        boolean expectedBar = new File(rootDirectory, "bar").isDirectory();
        assertEquals("directory 'foo' was not created", true, expectedFoo);
        assertEquals("directory 'bar' was not created", true, expectedBar);
    }

    @Test
    public void shouldCreateNestedDirectoriesGivenDescriptor()
    {
        List<DirectoryDescriptor> descriptors = getDirectoryDescriptors(new String[]{"foo", "bar"});
        directoryBuilder.createDirectoryStructure(descriptors);
        boolean expectedFoo = new File(rootDirectory, "foo/bar").isDirectory();
        assertEquals("one descriptors was not created", 1, descriptors.size());
        assertEquals("directory 'bar' was created", false, new File(rootDirectory, "bar").isDirectory());
        assertEquals("directory 'foo' was not created", true, new File(rootDirectory, "foo").isDirectory());
        assertEquals("did not create branch foo/bar", true, expectedFoo);
    }

}
