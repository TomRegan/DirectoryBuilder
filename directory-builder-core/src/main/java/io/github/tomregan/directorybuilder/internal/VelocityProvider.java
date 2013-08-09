package io.github.tomregan.directorybuilder.internal;

import io.github.tomregan.directorybuilder.descriptors.Delegate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityProvider
{
    private Delegate delegate;
    private ResourceResolver resourceResolver;

    private VelocityProvider() {}

    private VelocityProvider(ResourceResolver resourceResolver, Delegate delegate)
    {
        this();
        this.resourceResolver = resourceResolver;
        this.delegate = delegate;
    }

    public static VelocityProvider newInstance(ResourceResolver resourceResolver, Delegate delegate)
    {
        return new VelocityProvider(resourceResolver, delegate);
    }

    public VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        if (resourceResolver.equals(ResourceResolver.CLASSPATH))
        {
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        }
        else
        {
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        }
        return velocityEngine;
    }

    public VelocityContext getVelocityContext()
    {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(delegate.getDescriptorId(), delegate);
        return velocityContext;
    }
}
