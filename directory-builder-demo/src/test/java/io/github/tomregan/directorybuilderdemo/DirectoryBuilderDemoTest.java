package io.github.tomregan.directorybuilderdemo;

import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DirectoryBuilderDemoTest
{

    private DirectoryBuilderDemo app;

    @Before
    public void setUp() throws Exception
    {
        app = DirectoryBuilderDemo.newInstance();
    }

    @Test
    public void shouldCreateStructure()
    {
        File rootDirectory = Files.createTempDir();
        app.run(rootDirectory);
        assertEquals("did not create directory structure", true, new File(rootDirectory, "src/main/java").isDirectory());
    }
}
