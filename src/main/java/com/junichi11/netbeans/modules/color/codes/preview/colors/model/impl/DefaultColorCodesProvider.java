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
package com.junichi11.netbeans.modules.color.codes.preview.colors.model.impl;

import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Document;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author arsi
 */
@ServiceProvider(service = ColorCodesProvider.class, position = 0)
public class DefaultColorCodesProvider implements ColorCodesProvider {

    private static final Pattern CSS_VARIABLE_PATTERN = Pattern.compile("(?<var>[\\$@][^ ]+)\\s*:\\s*(?<value>).+\\s*;"); // NOI18N

    @Override
    public boolean isMimeTypeSupported(Document document, String mimeType) {
        return mimeType.matches(ColorCodesPreviewOptions.getInstance().getMimeTypeRegex());
    }

    @Override
    public boolean isProviderEnabled() {
        return true;
    }

    @Override
    public boolean isColorEditable(ColorValue colorValue) {
        return true;
    }

    @Override
    public void addAllColorValues(Document document, String mimeType, String line, int lineNumber, List<ColorValue> colorValues) {
        colorValues.addAll(ColorsUtils.getHexColorCodes(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssIntRGBs(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssIntRGBAs(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBs(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBAs(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLs(this, line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLAs(this, line, lineNumber));
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        if (options.useNamedColors()) {
            colorValues.addAll(ColorsUtils.getNamedColors(this, line, lineNumber));
        }
    }

    @Override
    public boolean isResolveVariablesSupported(Document document, String mimeType) {
        return resolveCssVariables(mimeType);
    }

    private boolean resolveCssVariables(String mimeType) {
        return isLessOrSass(mimeType)
                && ColorCodesPreviewOptions.getInstance().resolveCssVariables();
    }

    private boolean isLessOrSass(String mimeType) {
        return "text/less".equals(mimeType) || "text/scss".equals(mimeType); // NOI18N
    }

    @Override
    public void checkVariables(Document document, String mimeType, String line, Map<String, List<ColorValue>> cssVariables, List<ColorValue> colorValues) {
        if (!isLessOrSass(mimeType)) {
            return;
        }

        Matcher matcher = CSS_VARIABLE_PATTERN.matcher(line);
        if (matcher.find()) {
            String variable = matcher.group("var"); // NOI18N
            if (variable != null) {
                cssVariables.put(variable, colorValues);
            }
        } else {
            final String l = line;
            Map<Integer, String> map = new HashMap<>();
            cssVariables.forEach((String var, List<ColorValue> colors) -> {
                if (l.contains(var)) {
                    int indexOfVar = l.indexOf(var);
                    int offsetBehindVariableName = indexOfVar + var.length();
                    if (offsetBehindVariableName < l.length()) {
                        // e.g. when searche $green, ignore $green1, $green2,...
                        char c = l.charAt(offsetBehindVariableName);
                        if (c == ' ' || c == ';') {
                            map.put(indexOfVar, var);
                        }
                    }
                }
            });
            List<Integer> offsetNumbers = new ArrayList<>(map.keySet());
            Collections.sort(offsetNumbers);
            offsetNumbers.forEach(offset -> colorValues.addAll(cssVariables.get(map.get(offset))));
        }
    }

    @Override
    public String toFormattedString(Color newColor, ColorValue originalColor) {
        return ColorsUtils.toFormattedString(newColor, originalColor.getType());
    }

}
