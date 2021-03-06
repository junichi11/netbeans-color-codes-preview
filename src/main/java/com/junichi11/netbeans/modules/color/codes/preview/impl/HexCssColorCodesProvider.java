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

import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.HexCssColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.impl.ui.options.HexCssOptionsPanel;
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.ColorsUtils;
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.HexCssColorType;
import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeGeneratorItem;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesPreviewOptionsPanel;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Document;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ColorCodesProvider.class, position = 0)
public class HexCssColorCodesProvider implements ColorCodesProvider {

    // check sass and less variables e.g. $green: #0f0;, @green: #0f0;
    private static final Pattern CSS_VARIABLE_PATTERN = Pattern.compile("(?<var>[\\$@][^ ]+)\\s*:\\s*(?<value>).+\\s*;"); // NOI18N
    private static final Pattern DEFAULT_PATTERN = Pattern.compile(ColorCodesPreviewOptions.HEX_CSS_DEFAULT_MIME_TYPE_REGEX);

    @Override
    public String getId() {
        return "hex.css"; // NOI18N
    }

    @NbBundle.Messages("HexCssColorCodesProvider.displayName=Hex and CSS")
    @Override
    public String getDisplayName() {
        return Bundle.HexCssColorCodesProvider_displayName();
    }

    @NbBundle.Messages("HexCssColorCodesProvider.description=Preview hex and CSS colors. e.g. #ff0000, rgb(100 , 100 , 100)")
    @Override
    public String getDescription() {
        return Bundle.HexCssColorCodesProvider_description();
    }

    @Override
    public boolean isProviderEnabled(Document document) {
        String mimeType = NbEditorUtilities.getMimeType(document);
        return ColorCodesPreviewOptions.getInstance().isEnabled(getId())
                && mimeType.matches(ColorCodesPreviewOptions.getInstance().getMimeTypeRegex());
    }

    @Override
    public List<ColorValue> getColorValues(Document document, String line, int lineNumber, Map<String, List<ColorValue>> variableColorValues) {
        List<ColorValue> colorValues = new ArrayList<>();
        colorValues.addAll(ColorsUtils.getHexColorCodes(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssIntRGBs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssIntRGBAs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBAs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLAs(line, lineNumber));
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        if (options.useNamedColors()) {
            colorValues.addAll(ColorsUtils.getNamedColors(line, lineNumber));
        }

        // for sass and less variables
        checkVariables(document, line, variableColorValues, colorValues);
        return colorValues;
    }

    @Override
    public int getStartIndex(Document document, int currentIndex) {
        String mimeType = NbEditorUtilities.getMimeType(document);
        return resolveCssVariables(mimeType) ? 0 : currentIndex;
    }

    private boolean resolveCssVariables(String mimeType) {
        return isLessOrSass(mimeType)
                && ColorCodesPreviewOptions.getInstance().resolveCssVariables();
    }

    private boolean isLessOrSass(String mimeType) {
        return "text/less".equals(mimeType) || "text/scss".equals(mimeType); // NOI18N
    }

    private void checkVariables(Document document, String line, Map<String, List<ColorValue>> cssVariables, List<ColorValue> colorValues) {
        if (!isLessOrSass(NbEditorUtilities.getMimeType(document))) {
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
                        // e.g. when search $green, ignore $green1, $green2,...
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
    public ColorCodesPreviewOptionsPanel getOptionsPanel() {
        return new HexCssOptionsPanel();
    }

    @Override
    public boolean canGenerateColorCode() {
        return true;
    }

    @Override
    public List<ColorCodeGeneratorItem> getColorCodeGeneratorItems(String mimeType) {
        Matcher matcher = Pattern.compile(ColorCodesPreviewOptions.getInstance().getMimeTypeRegex()).matcher(mimeType);
        if (!matcher.matches()) {
            return Collections.emptyList();
        }
        List<ColorCodeGeneratorItem> items = new ArrayList<>();
        items.add(HexCssColorCodeGeneratorItem.HEX);
        for (HexCssColorCodeGeneratorItem item : HexCssColorCodeGeneratorItem.values()) {
            if (item != HexCssColorCodeGeneratorItem.HEX && DEFAULT_PATTERN.matcher(mimeType).matches()) {
                items.add(item);
            }
        }
        return items;
    }

    private static enum HexCssColorCodeGeneratorItem implements ColorCodeGeneratorItem {
        HEX(HexCssColorType.HEX) {
            @Override
            public String getDisplayName() {
                return "#<hex>"; // NOI18N
            }

            @Override
            public String getTooltipText() {
                return Bundle.HexCssColorCodeGeneratorItem_hex_tooltipText();
            }
        },
        CSS_INT_RGB(HexCssColorType.CSS_INT_RGB) {
            @Override
            public String getDisplayName() {
                return "rgb(r, g, b)"; // NOI18N
            }

        },
        CSS_PERCENT_RGB(HexCssColorType.CSS_PERCENT_RGB) {
            @Override
            public String getDisplayName() {
                return "rgb(r%, g%, b%)"; // NOI18N
            }

        },
        CSS_INT_RGBA(HexCssColorType.CSS_INT_RGBA) {
            @Override
            public String getDisplayName() {
                return "rgba(r, g, b, a)"; // NOI18N
            }
        },
        CSS_PERCENT_RGBA(HexCssColorType.CSS_PERCENT_RGBA) {
            @Override
            public String getDisplayName() {
                return "rgba(r%, g%, b%, a)"; // NOI18N
            }
        },
        CSS_HSL(HexCssColorType.CSS_HSL) {
            @Override
            public String getDisplayName() {
                return "hsl(h, s%, l%)"; // NOI18N
            }
        },
        CSS_HSLA(HexCssColorType.CSS_HSLA) {
            @Override
            public String getDisplayName() {
                return "hsla(h, s%, l%, a)"; // NOI18N
            }
        };

        private final HexCssColorType type;

        private HexCssColorCodeGeneratorItem(HexCssColorType type) {
            this.type = type;
        }

        @NbBundle.Messages({
            "HexCssColorCodeGeneratorItem.hex.tooltipText=Generate Hex color codes (e.g. #ff00ff)",
            "HexCssColorCodeGeneratorItem.css.tooltipText=Generate CSS color codes"
        })
        @Override
        public String getTooltipText() {
            return Bundle.HexCssColorCodeGeneratorItem_css_tooltipText();
        }

        @Override
        public ColorCodeFormatter getFormatter() {
            return new HexCssColorCodeFormatter(type);
        }

    }

}
