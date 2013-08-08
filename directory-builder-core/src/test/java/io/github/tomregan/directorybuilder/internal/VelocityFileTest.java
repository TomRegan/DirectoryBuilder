package io.github.tomregan.directorybuilder.internal;

import com.google.common.io.Files;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VelocityFileTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private VelocityFile velocityFile;

    @Before
    public void setUp() throws Exception
    {
        VelocityEngine velocityEngine = mock(VelocityEngine.class);
        when(velocityEngine.getTemplate(any(String.class))).thenThrow(new Exception());
        VelocityProvider velocityProvider = mock(VelocityProvider.class);
        when(velocityProvider.getVelocityEngine()).thenReturn(velocityEngine);
        when(velocityProvider.getVelocityContext()).thenReturn(new VelocityContext());
        VelocityFile velocityFile = new VelocityFile(File.createTempFile("template", ".tmp"),
                Files.createTempDir(), "file.tmp", velocityProvider);
        this.velocityFile = velocityFile;
    }

    @Test
    public void testCreateNewFileShouldThrowsIOExceptionWhenVelocityThrowsException() throws Exception
    {
        exception.expect(IOException.class);
        velocityFile.createNewFile();
    }
}
