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

import java.io.File;

public class FileFactory
{
    public static FileFactory createFileFactory()
    {
        return new FileFactory();
    }

    public File createFile(File parentDirectory, String name)
    {
        return new File(parentDirectory, name);
    }

    public File createFile(File template, File parentDirectory, String name, FileDescriptor delegate)
    {
        return new TemplateFile(template, parentDirectory, name, delegate);
    }
}
