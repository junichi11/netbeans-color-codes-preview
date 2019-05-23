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
package com.junichi11.netbeans.modules.color.codes.preview;

import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.ui.ColorCodeGeneratorPanel;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

public class Installer extends ModuleInstall {

    private static final long serialVersionUID = 7690650702153820108L;

    @Override
    public void restored() {
    }

    @Override
    public void close() {
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        options.setLastSelectedColor(ColorCodeGeneratorPanel.getLastSelectedColor());
        options.setLastAppendSemicolonSelected(ColorCodeGeneratorPanel.isLastAppendSemicolonSelected());
        try {
            // if other exceptions are thrown when IDE is closed, the options may not be saved
            // so sleep a bit
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
