package directorybuilder.internal;

import directorybuilder.descriptors.Delegate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VelocityProviderTest
{
    private VelocityProvider velocityProvider;

    @Before
    public void setUp()
    {
        Delegate delegate = mock(Delegate.class);
        when(delegate.getDescriptorId()).thenReturn("");
        this.velocityProvider = VelocityProvider.newInstance(ResourceResolver.CLASSPATH, delegate);
    }

    @Test
    public void getVelocityEngineShouldReturnVelocityEngine()
    {
        assertThat(velocityProvider.getVelocityEngine(), is(VelocityEngine.class));
    }

    @Test
    public void getVelocityContextShouldReturnVelocityContext()
    {
        assertThat(velocityProvider.getVelocityContext(), is(VelocityContext.class));
    }
}
