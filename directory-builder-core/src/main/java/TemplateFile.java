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

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class TemplateFile extends File
{
    private final File template;

    public TemplateFile(File template, File parentDirectory, String name)
    {
        super(parentDirectory, name);
        this.template = template;
    }

    public boolean createNewFile() throws IOException
    {
        if (super.createNewFile())
        {
            try
            {
                Files.copy(this.template, super.getAbsoluteFile());
            }
            catch (IOException e)
            {
                // to be substitutable, we have to ignore this exception
            }
            return true;
        }
        return false;
    }
}
