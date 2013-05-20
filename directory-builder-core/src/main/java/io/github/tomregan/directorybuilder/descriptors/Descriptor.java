package io.github.tomregan.directorybuilder.descriptors;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Represents a file or directory which can be configured before being created on the file system.
 */
public interface Descriptor
{
    /**
     * Creates a file system entity.
     *
     * @param parentDirectory the parent of this descriptor.
     * @throws java.io.IOException
     */
    void create(File parentDirectory) throws IOException;

    /**
     * Adds a child descriptor.
     *
     * @param descriptor The child descriptor, for example a subdirectory.
     */
    void addChild(Descriptor descriptor);

    /**
     * Sets the requested attribute to the value provided.
     *
     * @param name  the attribute.
     * @param value the attribute value.
     */
    void setValueForAttribute(String name, String value);

    /**
     * Returns the current value for an attribute.
     *
     * @param name the attribute.
     * @return the attribute value.
     */
    String getValueForAttribute(String name);

    /**
     * Returns a set of all expected attributes.
     *
     * @return a set of all expected attributes.
     */
    Set<String> getAttributeNames();
}
