/*
 * Copyright 2019 junichi11.
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
package com.junichi11.netbeans.modules.color.codes.preview.gen;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.codegen.CodeGeneratorContextProvider;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

@MimeRegistration(mimeType = "", service = CodeGeneratorContextProvider.class)
public class ColorCodeGeneratorContextProvider implements CodeGeneratorContextProvider {

    /**
     * Adds an additional content to the original context and runs the given
     * task with the new context as a parameter.
     *
     * @param context the original context
     * @param task the task to be run
     */
    @Override
    public void runTaskWithinContext(Lookup context, Task task) {
        //JTextComponent is always guaranteed, you can get it like this
        //JTextComponent component = context.lookup(JTextComponent.class);

        // Create new Lookup with extra items to be used in the task
        // Make sure that newContext contains the original context
        Lookup extraItems = Lookups.fixed(/* Add you extra items here */);
        Lookup newContext = new ProxyLookup(context, extraItems);

        // You may aquire a lock here:
        //try {
        task.run(newContext);
        // } finally {
        // Don't forget to unlock here
        //}
    }

}
