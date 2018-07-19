/*
 * Copyright 2018 junichi11.
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
package com.junichi11.netbeans.modules.color.codes.preview.ui;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.SideBarFactory;
import org.netbeans.spi.editor.mimelookup.MimeLocation;

/**
 *
 * @author junichi11
 */
@MimeRegistration(position = 0, mimeType = "", service = SideBarFactory.class)
@MimeLocation(subfolderName = "SideBars")
public class ColorsSideBarFactory implements SideBarFactory {

    public static final String KEY_COLORS = "enable.colors"; // NOI18N
    public static final boolean DEFAULT_COLORS = true;

    @Override
    public JComponent createSideBar(JTextComponent editor) {
//        return new DrawingPanel(editor);
        return new ColorsSideBarPanel(editor);
    }
}
