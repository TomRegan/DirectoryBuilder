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

package io.github.tomregan.directorybuilder;

import io.github.tomregan.directorybuilder.internal.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.internal.IDescriptor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DescriptorGeneratorTest
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private DescriptorGenerator descriptorGenerator;

    @Before
    public void setUp()
    {
        descriptorGenerator = DescriptorGenerator.createDescriptorGenerator();
    }

    @Test
    public void shouldCreateDirectoryDescriptorGivenValidDirectoryXML()
    {
        List<String> lines = new ArrayList<String>();
        lines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
        lines.add("<directorystructure>");
        lines.add("    <directory name=\"test\"/>");
        lines.add("</directorystructure>");
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor("test");
        IDescriptor[] expected = new IDescriptor[]{directoryDescriptor};
        assertEquals("did not create DirectoryDescriptor", expected, descriptorGenerator.getDescriptors(lines));
    }

    @Test
    public void shouldThrowExceptionGivenInvalidXML()
    {
        List<String> lines = new ArrayList<String>();
        lines.add("<>nonsense<>");
        exception.expect(RuntimeException.class);
        descriptorGenerator.getDescriptors(lines);
    }
}
