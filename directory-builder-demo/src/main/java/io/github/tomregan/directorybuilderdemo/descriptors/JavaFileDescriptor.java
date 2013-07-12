package io.github.tomregan.directorybuilderdemo.descriptors;

import io.github.tomregan.directorybuilder.descriptors.Delegate;
import io.github.tomregan.directorybuilder.descriptors.FileDescriptor;
import io.github.tomregan.directorybuilder.internal.FileFactory;

public class JavaFileDescriptor extends FileDescriptor implements Delegate
{
    protected JavaFileDescriptor(FileFactory fileFactory)
    {
        super(fileFactory);
    }

    public static JavaFileDescriptor newInstance()
    {
        return new JavaFileDescriptor(FileFactory.newInstance());
    }

    public String getClassName()
    {
        return "HelloWorld";
    }

    public String getUser()
    {
        return "Tom";
    }
}
