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
package com.junichi11.netbeans.modules.color.codes.preview.colors.impl.ui.options;

import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorCodesProvider;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author junichi11
 */
public class ProvidersCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -6846232852442221985L;
    private final ListCellRenderer<? super ColorCodesProvider> renderer;

    public ProvidersCellRenderer(ListCellRenderer<? super ColorCodesProvider> renderer) {
        this.renderer = renderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof ColorCodesProvider) {
            ColorCodesProvider provider = (ColorCodesProvider) value;
            JLabel label = (JLabel) renderer.getListCellRendererComponent((JList<ColorCodesProvider>) list, provider, index, isSelected, cellHasFocus);
            label.setText(((ColorCodesProvider) value).getDisplayName());
            return label;
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

}
