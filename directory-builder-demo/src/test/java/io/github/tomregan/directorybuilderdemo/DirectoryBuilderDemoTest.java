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

package io.github.tomregan.directorybuilderdemo;

import com.google.common.io.Files;
import io.github.tomregan.directorybuilderdemo.ui.UserInterface;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DirectoryBuilderDemoTest
{

    private DirectoryBuilderDemo app;

    @Before
    public void setUp() throws Exception
    {
        app = DirectoryBuilderDemo.newInstance();
    }

    @Test
    public void shouldCreateStructure()
    {
        File rootDirectory = Files.createTempDir();
        UserInterface userInterface = mock(UserInterface.class);
        when(userInterface.getUserName()).thenReturn("Tom");
        app.run(rootDirectory, userInterface);
        assertEquals("did not create directory structure", true, new File(rootDirectory, "src/main/java").isDirectory());
    }
}
