package io.github.tomregan.directorybuilderdemo.descriptors;

import io.github.tomregan.directorybuilder.descriptors.DirectoryDescriptor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SourceDirectoryDescriptorTest
{
    @Test
    public void shouldCreateDirectory() throws IOException
    {
        File rootDir = new File("/Users/tom");
        DirectoryDescriptor src = DirectoryDescriptor.newInstance();
        DirectoryDescriptor main = DirectoryDescriptor.newInstance();
        SourceDirectoryDescriptor java = SourceDirectoryDescriptor.newInstance();
        src.setValueForAttribute("name", "src");
        main.setValueForAttribute("name", "main");
        java.setValueForAttribute("name", "java");
        src.addChild(main);
        main.addChild(java);
        src.create(rootDir);
        assertEquals("did not create source directory", true, new File(rootDir, "src/main/java").isDirectory());
    }
}
