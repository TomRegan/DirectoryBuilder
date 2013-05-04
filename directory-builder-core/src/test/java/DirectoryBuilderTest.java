/*
 * Copyright 2013 Tom Regan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        List<DirectoryDescriptor> descriptors = new ArrayList<DirectoryDescriptor>();
        for (String branches : directories)
        {
            String[] branch = branches.split("/");
            DirectoryDescriptor lastDirectory = null;
            for (String directory : branch)
            {
                DirectoryDescriptor newDirectory = DirectoryDescriptor.createDirectoryDescriptor(directory);
                if (lastDirectory == null)
                {
                    descriptors.add(newDirectory);
                }
                else
                {
                    lastDirectory.addChild(newDirectory);
                }
                lastDirectory = newDirectory;
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
    public void shouldCreateArbitraryDirectoryStructureGivenDescriptor() throws IOException
    {
        DirectoryDescriptor a = DirectoryDescriptor.createDirectoryDescriptor("a");
        DirectoryDescriptor b = DirectoryDescriptor.createDirectoryDescriptor("b");
        DirectoryDescriptor c = DirectoryDescriptor.createDirectoryDescriptor("c");
        DirectoryDescriptor d = DirectoryDescriptor.createDirectoryDescriptor("d");
        DirectoryDescriptor e = DirectoryDescriptor.createDirectoryDescriptor("e");
        a.addChild(b);
        b.addChild(c);
        b.addChild(d);
        d.addChild(e);
        directoryBuilder.createDirectoryStructure(a);
        assertEquals("did not create branch a/", true, new File(rootDirectory, "a/").isDirectory());
        assertEquals("did not create branch a/b", true, new File(rootDirectory, "a/b").isDirectory());
        assertEquals("did not create branch a/b/c", true, new File(rootDirectory, "a/b/c").isDirectory());
        assertEquals("did not create branch a/b/d", true, new File(rootDirectory, "a/b/d").isDirectory());
        assertEquals("did not create branch a/b/d/e", true, new File(rootDirectory, "a/b/d/e").isDirectory());
    }

    @Test
    public void shouldThrowExceptionWhenMakeDirectoryFails() throws IOException
    {
        File mockFile = mock(File.class);
        when(mockFile.mkdir()).thenReturn(false);
        FileFactory mockFileFactory = mock(FileFactory.class);
        when(mockFileFactory.createFile(any(File.class), any(String.class))).thenReturn(mockFile);
        DirectoryDescriptor descriptor = DirectoryDescriptor.createDirectoryDescriptor("CrashTestDummy", mockFileFactory);
        exception.expect(IOException.class);
        directoryBuilder.createDirectoryStructure(descriptor);
    }

    @Test
    public void shouldCreateFileGivenDescriptor() throws IOException
    {
        FileDescriptor fileDescriptor = FileDescriptor.createFileDescriptor("foo.txt");
        directoryBuilder.createDirectoryStructure(fileDescriptor);
        assertEquals("did not create foo.txt", true, new File(rootDirectory, "foo.txt").isFile());
    }

}
