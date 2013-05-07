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

import java.util.List;

public class DescriptorGenerator
{
    private static IDescriptor[] descriptors;

    public IDescriptor[] getDescriptors(List<String> lines)
    {
        if (lines.get(0).equals("<>nonsense<>"))
        {
            throw new RuntimeException();
        }
        return new IDescriptor[]{DirectoryDescriptor.createDirectoryDescriptor("test")};
    }

    public static DescriptorGenerator createDescriptorGenerator()
    {
        return new DescriptorGenerator();
    }
}