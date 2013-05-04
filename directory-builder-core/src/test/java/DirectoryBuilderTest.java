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
import java.nio.charset.Charset;
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
    private FileFactory mockFileFactory;
    private File template;

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
        File mockFile = mock(File.class);
        when(mockFile.createNewFile()).thenReturn(false);
        mockFileFactory = mock(FileFactory.class);
        when(mockFileFactory.createFile(any(File.class), any(String.class))).thenReturn(mockFile);
        when(mockFileFactory.createFile(any(File.class), any(File.class), any(String.class), any(FileDescriptor.class))).thenReturn(mockFile);
        template = new File("src/test/resources/test.template");
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
        DirectoryDescriptor descriptor = DirectoryDescriptor.createDirectoryDescriptor("CrashTestDummy", mockFileFactory);
        exception.expect(IOException.class);
        directoryBuilder.createDirectoryStructure(descriptor);
    }

    @Test
    public void shouldCreateFileGivenDescriptor() throws IOException
    {
        FileDescriptor fileDescriptor = FileDescriptor.createFileDescriptor(template, "foo.txt");
        directoryBuilder.createDirectoryStructure(fileDescriptor);
        assertEquals("did not create foo.txt", true, new File(rootDirectory, "foo.txt").isFile());
    }

    @Test
    public void shouldHandleNestingFilesInsideDirectories() throws IOException
    {
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor("foo");
        FileDescriptor spam = FileDescriptor.createFileDescriptor(template, "spam.txt");
        FileDescriptor eggs = FileDescriptor.createFileDescriptor(template, "eggs.txt");
        directoryDescriptor.addChild(spam);
        directoryDescriptor.addChild(eggs);
        directoryBuilder.createDirectoryStructure(directoryDescriptor);
        assertEquals("did not create directory foo", true, new File(rootDirectory, "foo").isDirectory());
        assertEquals("did not create file spam.txt", true, new File(rootDirectory, "foo/spam.txt").isFile());
        assertEquals("did not create file eggs.txt", true, new File(rootDirectory, "foo/eggs.txt").isFile());
    }

    @Test
    public void shouldThrowExceptionWhenCreateFileFails() throws IOException
    {
        FileDescriptor fileDescriptor = FileDescriptor.createFileDescriptor(template, "CrashTextDummy.txt", mockFileFactory);
        exception.expect(IOException.class);
        directoryBuilder.createDirectoryStructure(fileDescriptor);
    }

    @Test
    public void shouldCreateFileContentsGivenTemplate() throws IOException
    {
        List<String> expected = new ArrayList<String>();
        expected.add("// foo.txt");
        expected.add("ohai wurld xxx");
        FileDescriptor fileDescriptor = FileDescriptor.createFileDescriptor(template, "foo.txt");
        directoryBuilder.createDirectoryStructure(fileDescriptor);
        List<String> actual = Files.readLines(new File(rootDirectory, "foo.txt"), Charset.defaultCharset());
        assertEquals("foo.txt did not contain the expected contents", expected, actual);
    }

}
