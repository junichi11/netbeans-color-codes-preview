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

import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.IntType;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.RGBAIntTypes;
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.ColorsUtils;
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.JavaColorType;
import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeGeneratorItem;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesPreviewOptionsPanel;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String NEW_JAVA_AWT_COLOR_PREFIX = "new java.awt.Color("; // NOI18N
    private static final String MIME_TYPE_JAVA = "text/x-java"; // NOI18N
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
                && MIME_TYPE_JAVA.equals(mimeType);
    }

    @Override
    public List<ColorValue> getColorValues(Document document, String line, int lineNumber, Map<String, List<ColorValue>> variableColorValues) {
        List<ColorValue> colorValues = new ArrayList<>();
        if (hasColorValue(line)) {
            collectStandardColors(line, colorValues, lineNumber);
            collectIntRGBsColors(line, colorValues, lineNumber);
            collectIntRGBAsColors(line, colorValues, lineNumber);
            collectIntRGBColors(line, colorValues, lineNumber);
            collectIntRGBAColors(line, colorValues, lineNumber);
            collectFloatRGBsColors(line, colorValues, lineNumber);
            collectFloatRGBAsColors(line, colorValues, lineNumber);
        }
        return colorValues;
    }

    private boolean hasColorValue(String line) {
        return (line.contains(COLOR_PREFIX)
                || line.contains(NEW_COLOR_PREFIX)
                || line.contains(NEW_JAVA_AWT_COLOR_PREFIX))
                && !line.contains("import"); // NOI18N
    }

    private void collectStandardColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaStandardColors(line, lineNumber));
    }

    private void collectFloatRGBsColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaFloatRGBsColors(line, lineNumber));
    }

    private void collectFloatRGBAsColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaFloatRGBAsColors(line, lineNumber));
    }

    private void collectIntRGBsColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBsColors(line, lineNumber));
    }

    private void collectIntRGBAsColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBAsColors(line, lineNumber));
    }

    private void collectIntRGBColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBColors(line, lineNumber));
    }

    private void collectIntRGBAColors(String line, List<ColorValue> colorValues, int lineNumber) {
        colorValues.addAll(ColorsUtils.getJavaIntRGBAColors(line, lineNumber));
    }

    @Override
    public ColorCodesPreviewOptionsPanel getOptionsPanel() {
        return ColorCodesPreviewOptionsPanel.createEmptyPanel();
    }

    @Override
    public boolean canGenerateColorCode() {
        return true;
    }

    @Override
    public List<ColorCodeGeneratorItem> getColorCodeGeneratorItems(String mimeType) {
        if (!mimeType.equals(MIME_TYPE_JAVA)) {
            return Collections.emptyList();
        }
        return Arrays.asList(JavaColorCodeGeneratorItem.values());
    }

    private static enum JavaColorCodeGeneratorItem implements ColorCodeGeneratorItem {
        INT_R_G_B(JavaColorType.JAVA_INT_R_G_B) {
            @Override
            public String getDisplayName() {
                return "new Color(r, g, b)"; // NOI18N
            }
        },
        INT_R_G_B_A(JavaColorType.JAVA_INT_R_G_B_A) {
            @Override
            public String getDisplayName() {
                return "new Color(r, g, b, a)"; // NOI18N
            }
        },
        INT_HEX_R_G_B(JavaColorType.JAVA_INT_R_G_B, new RGBAIntTypes(IntType.Hex, IntType.Hex, IntType.Hex)) {
            @Override
            public String getDisplayName() {
                return "new Color(hex r, hex g, hex b)"; // NOI18N
            }
        },
        INT_HEX_R_G_B_A(JavaColorType.JAVA_INT_R_G_B_A, new RGBAIntTypes(IntType.Hex, IntType.Hex, IntType.Hex, IntType.Hex)) {
            @Override
            public String getDisplayName() {
                return "new Color(hex r, hex g, hex b, hex a)"; // NOI18N
            }
        },
        INT_HEX_RGB(JavaColorType.JAVA_INT_RGB, new RGBAIntTypes(IntType.Hex)) {
            @Override
            public String getDisplayName() {
                return "new Color(hex rgb)"; // NOI18N
            }
        },
        INT_HEX_RGBA(JavaColorType.JAVA_INT_RGBA, new RGBAIntTypes(IntType.Hex)) {
            @Override
            public String getDisplayName() {
                return "new Color(hex argb, true)"; // NOI18N
            }
        },
        FLOAT_R_G_B(JavaColorType.JAVA_FLOAT_R_G_B) {
            @Override
            public String getDisplayName() {
                return "new Color(rf, gf, bf)"; // NOI18N
            }
        },
        FLOAT_R_G_B_A(JavaColorType.JAVA_FLOAT_R_G_B_A) {
            @Override
            public String getDisplayName() {
                return "new Color(rf, gf, bf, af)"; // NOI18N
            }
        },
        DECODE(JavaColorType.DECODE) {
            @Override
            public String getDisplayName() {
                return "Color.decode(\"#<hex>\")"; // NOI18N
            }
        };

        private final JavaColorType type;
        private final RGBAIntTypes rgbaIntTypes;

        private JavaColorCodeGeneratorItem(JavaColorType type) {
            this(type, RGBAIntTypes.ALL_DECIMAL);
        }

        private JavaColorCodeGeneratorItem(JavaColorType type, RGBAIntTypes rgbaIntTypes) {
            this.type = type;
            this.rgbaIntTypes = rgbaIntTypes;
        }

        @Override
        public ColorCodeFormatter getFormatter() {
            return new JavaColorCodeFormatter(type, rgbaIntTypes, false);
        }

        @NbBundle.Messages("JavaColorCodeGeneratorItem.tooltipText=Generate Java Color class code")
        @Override
        public String getTooltipText() {
            return Bundle.JavaColorCodeGeneratorItem_tooltipText();
        }
    }

}
