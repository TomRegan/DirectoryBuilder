package io.github.tomregan.directorybuilder.descriptors;

/**
 * Methods which can be called from a file template.
 */
public interface Delegate
{
    /**
     * Returns the ID of the delegate. By default this is the class name of the delegate.
     * <p/>
     * It's expected that in templates the delegate class will be accessed like:
     * <code>$FileDescriptor.Name</code>
     * Where <code>$FileDescriptor</code> is the delegateId.
     *
     * @return the class name.
     */
    String getDescriptorId();

    /**
     * Returns the name of the descriptor.
     * <p/>
     * It's expected that in templates the delegate class will be accessed like:
     * <code>$FileDescriptor.Name</code>
     * Where <code>Name</code> is the getter <code>getName</code>.
     *
     * @return the name of the descriptor.
     */
    String getName();
}
