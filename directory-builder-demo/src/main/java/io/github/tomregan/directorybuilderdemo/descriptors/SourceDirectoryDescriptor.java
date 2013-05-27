package io.github.tomregan.directorybuilderdemo.descriptors;

import io.github.tomregan.directorybuilder.descriptors.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.internal.FileFactory;

public class SourceDirectoryDescriptor extends DirectoryDescriptor
{
    protected SourceDirectoryDescriptor(FileFactory fileFactory)
    {
        super(fileFactory);
    }

    public static SourceDirectoryDescriptor newInstance()
    {
        return new SourceDirectoryDescriptor(FileFactory.newInstance());
    }
}
