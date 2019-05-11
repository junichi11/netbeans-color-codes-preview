/*
 * Copyright 2019 arsi.
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
package com.junichi11.netbeans.modules.color.codes.preview.colors.impl;

import com.junichi11.netbeans.modules.color.codes.preview.colors.JavaIntRGBColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * Supported colors: new Color[]{Color.blue, new Color(153, 255, 0)}; new
 * Color(0, 0, 0); Color.CYAN;
 *
 * @author arsi
 */
@ServiceProvider(service = ColorCodesProvider.class, position = 100)
public class JavaColorCodesProvider extends AbstractColorCodesProvider {

    public enum StandardColor {
        // upper
        BLACK("BLACK", Color.BLACK), // NOI18N
        BLUE("BLUE", Color.BLUE), // NOI18N
        CYAN("CYAN", Color.CYAN), // NOI18N
        DARK_GRAY("DARK_GRAY", Color.DARK_GRAY), // NOI18N
        GRAY("GRAY", Color.GRAY), // NOI18N
        GREEN("GREEN", Color.GREEN), // NOI18N
        LIGHT_GRAY("LIGHT_GRAY", Color.LIGHT_GRAY), // NOI18N
        MAGENTA("MAGENTA", Color.MAGENTA), // NOI18N
        ORANGE("ORANGE", Color.ORANGE), // NOI18N
        PINK("PINK", Color.PINK), // NOI18N
        RED("RED", Color.RED), // NOI18N
        WHITE("WHITE", Color.WHITE), // NOI18N
        YELLOW("YELLOW", Color.YELLOW), // NOI18N
        // lower
        black("black", Color.black), // NOI18N
        blue("blue", Color.blue), // NOI18N
        cyan("cyan", Color.cyan), // NOI18N
        darkGray("darkGray", Color.darkGray), // NOI18N
        gray("gray", Color.gray), // NOI18N
        green("green", Color.green), // NOI18N
        lightGray("lightGray", Color.lightGray), // NOI18N
        magenta("magenta", Color.magenta), // NOI18N
        orange("orange", Color.orange), // NOI18N
        pink("pink", Color.pink), // NOI18N
        red("red", Color.red), // NOI18N
        white("white", Color.white), // NOI18N
        yellow("yellow", Color.yellow), // NOI18N
        ;

        private final String colorName;
        private final Color color;

        public String getColorName() {
            return colorName;
        }

        public Color getColor() {
            return color;
        }

        public String asMethod() {
            return "Color." + colorName; // NOI18N
        }

        private StandardColor(String colorName, Color color) {
            this.colorName = colorName;
            this.color = color;
        }
    }

    private static final String COLOR_PREFIX = "Color."; // NOI18N
    private static final String NEW_COLOR_PREFIX = "new Color("; // NOI18N

    private static final int MAX_PRAMETER_SIZE = 5;
    private static final int RGB_COLOR_PARM_SIZE = 3;
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

    @NbBundle.Messages("JavaColorCodesProvider.description=Preview Java Color object colors. e.g. new Color(153, 255, 0), Color.CYAN")
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
        }
        return colorValues;
    }

    private boolean hasColorValue(String line) {
        return (line.contains(COLOR_PREFIX) || line.contains(NEW_COLOR_PREFIX))
                && !line.contains("import"); // NOI18N
    }

    private void collectStandardColors(String line, List<ColorValue> colorValues, int lineNumber) {
        int index = 0;
        while ((index = line.indexOf(COLOR_PREFIX, index + 1)) >= 0) {
            for (StandardColor stdColor : StandardColor.values()) {
                collectStandardColor(colorValues, stdColor, line, index, lineNumber);
            }
        }
    }

    private void collectStandardColor(List<ColorValue> colorValues, StandardColor stdColor, String line, int index, int lineNumber) {
        int start = index;
        index = index + 6;
        String sub = line.substring(index);
        if (sub.startsWith(stdColor.getColorName())) {
            colorValues.add(new JavaIntRGBColorValue(COLOR_PREFIX + stdColor.getColorName(), start, start + 6 + stdColor.getColorName().length(), lineNumber, stdColor.getColor()));
        }
    }

    private void collectRGBColors(String line, List<ColorValue> colorValues, int lineNumber) {
        int index = 0;
        while ((index = line.indexOf(NEW_COLOR_PREFIX, index + 1)) >= 0) {
            try {
                int start = index;
                index += NEW_COLOR_PREFIX.length();
                int end = line.indexOf(')', index);
                if (end == -1) {
                    // e.g. "new Color(";
                    break;
                }
                String parameters = line.substring(index, end);
                int params[] = new int[MAX_PRAMETER_SIZE];
                int count = 0;
                for (String parameter : parameters.split(",")) {
                    try {
                        params[count++] = Integer.parseInt(parameter.trim());
                        if (count >= MAX_PRAMETER_SIZE) {
                            break;
                        }
                    } catch (NumberFormatException ex) {
                        break;
                    }
                }
                if (count == RGB_COLOR_PARM_SIZE) {
                    colorValues.add(new JavaIntRGBColorValue(line.substring(start, end + 1), start, end + 1, lineNumber, new Color(params[0], params[1], params[2])));
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Java color parser exception.", e); // NOI18N
            }
        }
    }
}
