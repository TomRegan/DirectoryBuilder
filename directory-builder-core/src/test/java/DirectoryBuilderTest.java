import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DirectoryBuilderTest
{
    private DirectoryBuilder directoryBuilder;
    private File rootDirectory;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private DirectoryDescriptor[] getDirectoryDescriptors(String... directories)
    {
        FileFactory fileFactory = FileFactory.getFileFactory();
        List<DirectoryDescriptor> descriptors = new ArrayList<DirectoryDescriptor>();
        for (String branches : directories)
        {
            String[] branch = branches.split("/");
            for (int i = 0; i < branch.length; ++i)
            {
                DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor(fileFactory);
                directoryDescriptor.setName(branch[i]);
                if (i == 0)
                {
                    descriptors.add(directoryDescriptor);
                }
                else
                {
                    DirectoryDescriptor parentDescriptor = descriptors.get(descriptors.size() - 1).getLastChild();
                    parentDescriptor.addChild(directoryDescriptor);
                }
            }
        }
        return descriptors.toArray(new DirectoryDescriptor[descriptors.size()]);
    }

    @Before
    public void setUp() throws Exception
    {
        rootDirectory = Files.createTempDir();
        directoryBuilder = DirectoryBuilder.createDirectoryBuilder(rootDirectory);
    }

    @Test
    public void shouldCreateDirectoriesGivenDescriptor() throws IOException
    {
        DirectoryDescriptor[] descriptors = getDirectoryDescriptors("foo", "bar");
        directoryBuilder.createDirectoryStructure(descriptors);
        assertEquals("directory 'foo' was not created", true, new File(rootDirectory, "foo").isDirectory());
        assertEquals("directory 'bar' was not created", true, new File(rootDirectory, "bar").isDirectory());
    }

    @Test
    public void shouldCreateNestedDirectoriesGivenDescriptor() throws IOException
    {
        DirectoryDescriptor[] descriptors = getDirectoryDescriptors("foo/bar/baz");
        directoryBuilder.createDirectoryStructure(descriptors);
        assertEquals("more than one descriptor was created", 1, descriptors.length);
        assertEquals("directory 'bar' was created", false, new File(rootDirectory, "bar").isDirectory());
        assertEquals("directory 'foo' was not created", true, new File(rootDirectory, "foo").isDirectory());
        assertEquals("did not create branch foo/bar", true, new File(rootDirectory, "foo/bar").isDirectory());
        assertEquals("did not create branch foo/bar/baz", true, new File(rootDirectory, "foo/bar/baz").isDirectory());
    }

    @Test
    public void shouldThrowExceptionWhenMakeDirectoryFails() throws IOException
    {
        File mockFile = mock(File.class);
        when(mockFile.mkdir()).thenReturn(false);
        FileFactory fileFactory = mock(FileFactory.class);
        when(fileFactory.createFile(any(File.class), any(String.class))).thenReturn(mockFile);
        DirectoryDescriptor descriptor = DirectoryDescriptor.createDirectoryDescriptor(fileFactory);
        exception.expect(IOException.class);
        directoryBuilder.createDirectoryStructure(descriptor);
    }

}
