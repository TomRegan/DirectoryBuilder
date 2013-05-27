package io.github.tomregan.directorybuilderdemo.descriptors;

import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaFileDescriptorTest
{

    private JavaFileDescriptor descriptor;

    @Before
    public void setUp() throws Exception
    {
        descriptor = JavaFileDescriptor.newInstance();
    }

    @Test
    public void testGetters()
    {
        assertEquals("HelloWorld", descriptor.getClassName());
        assertEquals("Tom", descriptor.getUser());
    }

    @Test
    public void shouldCreateNewJavaFile() throws IOException
    {
        File rootDirectory = Files.createTempDir();
        descriptor.setValueForAttribute("name", "HelloWorld.java");
        descriptor.setValueForAttribute("template", "src/main/resources/HelloWorld.vm");
        String filename = descriptor.getValueForAttribute("name");
        descriptor.create(rootDirectory);
        assertEquals("did not create java file", true, new File(rootDirectory, filename).isFile());
    }
}
