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
package com.junichi11.netbeans.modules.color.codes.preview.impl;

import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesPreviewOptionsPanel;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * Supported colors: new Color[]{Color.blue, new Color(153, 255, 0)}; new
 * Color(0, 0, 0); Color.CYAN;
 *
 * @author junichi11
 */
@ServiceProvider(service = ColorCodesProvider.class, position = 100)
public class JavaColorCodesProvider extends AbstractColorCodesProvider {

    private static final String COLOR_PREFIX = "Color."; // NOI18N
    private static final String NEW_COLOR_PREFIX = "new Color("; // NOI18N

    private static final Logger LOGGER = Logger.getLogger(JavaColorCodesProvider.class.getName());

    @Override
    public String getId() {
        return "java"; // NOI18N
    }

    @NbBundle.Messages("JavaColorCodesProvider.displayName=Java")
    @Override
    public String getDisplayName() {
        return Bundle.JavaColorCodesProvider_displayName();
    }

    @NbBundle.Messages("JavaColorCodesProvider.description=Preview Java Color class colors. e.g. new Color(153, 255, 0), Color.CYAN")
    @Override
    public String getDescription() {
        return Bundle.JavaColorCodesProvider_description();
    }

    @Override
    public boolean isProviderEnabled(Document document) {
        String mimeType = NbEditorUtilities.getMimeType(document);
        return ColorCodesPreviewOptions.getInstance().isEnabled(getId())
                && "text/x-java".equals(mimeType); // NOI18N
    }

    @Override
    public List<ColorValue> getColorValues(Document document, String line, int lineNumber, Map<String, List<ColorValue>> variableColorValues) {
        List<ColorValue> colorValues = new ArrayList<>();
        if (hasColorValue(line)) {
            collectStandardColors(line, colorValues, lineNumber);
            collectRGBColors(line, colorValues, lineNumber);
            collectRGBAColors(line, colorValues, lineNumber);
        }
        ColorsUtils.sort(colorValues);
        return colorValues;
    }

    private boolean hasColorValue(String line) {
        return (line.contains(COLOR_PREFIX) || line.contains(NEW_COLOR_PREFIX))
                && !line.contains("import"); // NOI18N
    }

    private void collectStandardColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaStandardColors(line, lineNumber));
    }

    private void collectRGBColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBColors(line, lineNumber));
    }

    private void collectRGBAColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBAColors(line, lineNumber));
    }

    @Override
    public ColorCodesPreviewOptionsPanel getOptionsPanel() {
        return ColorCodesPreviewOptionsPanel.EMPTY_PANEL;
    }

}
