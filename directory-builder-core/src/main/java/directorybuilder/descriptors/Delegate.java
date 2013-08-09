/*
 * Copyright 2013 Tom Regan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package directorybuilder.descriptors;

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
