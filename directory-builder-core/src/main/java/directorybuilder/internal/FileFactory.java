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

package directorybuilder.internal;

import java.io.File;

public class FileFactory
{
    public static FileFactory newInstance()
    {
        return new FileFactory();
    }

    public File createFile(String name)
    {
        return new File(name);
    }

    public File createFile(File parentDirectory, String name)
    {
        return new File(parentDirectory, name);
    }

    public File createFile(File parentDirectory, String name, File template, VelocityProvider velocityProvider)
    {
        return new VelocityFile(template, parentDirectory, name, velocityProvider);
    }
}
