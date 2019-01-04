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
package com.junichi11.netbeans.modules.color.codes.preview.colors.model.impl;

import com.junichi11.netbeans.modules.color.codes.preview.colors.JavaIntRGBColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorValue;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.openide.util.lookup.ServiceProvider;

/**
 * Supported colors: new Color[]{Color.blue, new Color(153, 255, 0)}; new
 * Color(0, 0, 0); Color.CYAN;
 *
 * @author arsi
 */
@ServiceProvider(service = ColorCodesProvider.class)
public class JavaColorCodesProvider implements ColorCodesProvider {

    private static final String RGB_VALUE_FORMAT = "Color(%s, %s, %s)"; // NOI18N
    private static final Logger LOGGER = Logger.getLogger(JavaColorCodesProvider.class.getName());

    @Override
    public boolean isMimeTypeSupported(String mimeType) {
        return "text/x-java".equals(mimeType);
    }

    @Override
    public boolean isProviderEnabled() {
        return true;
    }

    @Override
    public boolean isResolveVariablesSupported(String mimeType) {
        return false;
    }

    @Override
    public void checkVariables(Document document, String mimeType, String line, Map<String, List<ColorValue>> variables, List<ColorValue> colorValues) {
    }

    @Override
    public void addAllColorValues(Document document, String mimeType, String line, int lineNumber, List<ColorValue> colorValues) {
        if ((line.contains("Color.") || line.contains("new Color(")) && !line.contains("import")) {
            int index = 0;
            while ((index = line.indexOf("Color.", index + 1)) >= 0) {
                findStdColor(colorValues, "BLACK", Color.BLACK, line, index, lineNumber);
                findStdColor(colorValues, "BLUE", Color.BLUE, line, index, lineNumber);
                findStdColor(colorValues, "CYAN", Color.CYAN, line, index, lineNumber);
                findStdColor(colorValues, "DARK_GRAY", Color.DARK_GRAY, line, index, lineNumber);
                findStdColor(colorValues, "GRAY", Color.GRAY, line, index, lineNumber);
                findStdColor(colorValues, "GREEN", Color.GREEN, line, index, lineNumber);
                findStdColor(colorValues, "LIGHT_GRAY", Color.LIGHT_GRAY, line, index, lineNumber);
                findStdColor(colorValues, "MAGENTA", Color.MAGENTA, line, index, lineNumber);
                findStdColor(colorValues, "ORANGE", Color.ORANGE, line, index, lineNumber);
                findStdColor(colorValues, "PINK", Color.PINK, line, index, lineNumber);
                findStdColor(colorValues, "RED", Color.RED, line, index, lineNumber);
                findStdColor(colorValues, "WHITE", Color.WHITE, line, index, lineNumber);
                findStdColor(colorValues, "YELLOW", Color.YELLOW, line, index, lineNumber);

                findStdColor(colorValues, "black", Color.black, line, index, lineNumber);
                findStdColor(colorValues, "blue", Color.blue, line, index, lineNumber);
                findStdColor(colorValues, "cyan", Color.cyan, line, index, lineNumber);
                findStdColor(colorValues, "darkGray", Color.darkGray, line, index, lineNumber);
                findStdColor(colorValues, "gray", Color.gray, line, index, lineNumber);
                findStdColor(colorValues, "green", Color.green, line, index, lineNumber);
                findStdColor(colorValues, "lightGray", Color.lightGray, line, index, lineNumber);
                findStdColor(colorValues, "magenta", Color.magenta, line, index, lineNumber);
                findStdColor(colorValues, "orange", Color.orange, line, index, lineNumber);
                findStdColor(colorValues, "pink", Color.pink, line, index, lineNumber);
                findStdColor(colorValues, "red", Color.red, line, index, lineNumber);
                findStdColor(colorValues, "white", Color.white, line, index, lineNumber);
                findStdColor(colorValues, "yellow", Color.yellow, line, index, lineNumber);
            }
            index = 0;
            while ((index = line.indexOf("new Color(", index + 1)) >= 0) {
                try {
                    int start = index;
                    index += "new Color(".length();
                    int end = line.indexOf(')', index);
                    String data = line.substring(index, end);
                    Integer tmp[] = new Integer[5];
                    int cnt = 0;
                    for (StringTokenizer stringTokenizer = new StringTokenizer(data, ",", false); stringTokenizer.hasMoreTokens();) {
                        String token = stringTokenizer.nextToken();
                        try {
                            tmp[cnt++] = new Integer(token.replaceAll(" ", ""));
                        } catch (NumberFormatException numberFormatException) {
                            numberFormatException.printStackTrace();
                        }
                    }
                    if (cnt == 3) {
                        colorValues.add(new JavaIntRGBColorValue(this, line.substring(start, end + 1), start, end + 1, lineNumber, tmp[0], tmp[1], tmp[2]));
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Java color parser exception.", e); // NOI18N
                }
            }

        }
    }

    private void findStdColor(List<ColorValue> colorValues, String color, Color cCode, String line, int index, int lineNumber) {
        int start = index;
        index = index + 6;
        String sub = line.substring(index);
        if (sub.startsWith(color)) {
            colorValues.add(new JavaIntRGBColorValue(this, "Color." + color, start, start + 6 + color.length(), lineNumber, cCode.getRed(), cCode.getGreen(), cCode.getBlue()));
        }

    }

    public static String RGBIntValueString(Color color) {
        return String.format(RGB_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public String toFormattedString(Color newColor, ColorValue originalColor) {
        String standardJavaColor = getStandardJavaColor(newColor);
        if (standardJavaColor != null) {
            return standardJavaColor;
        }
        return "new " + RGBIntValueString(newColor);
    }

    private String getStandardJavaColor(Color selectedColor) {
        if (Color.black.equals(selectedColor)) {
            return "Color.black";
        } else if (Color.blue.equals(selectedColor)) {
            return "Color.blue";
        } else if (Color.cyan.equals(selectedColor)) {
            return "Color.cyan";
        } else if (Color.darkGray.equals(selectedColor)) {
            return "Color.darkGray";
        } else if (Color.gray.equals(selectedColor)) {
            return "Color.gray";
        } else if (Color.green.equals(selectedColor)) {
            return "Color.green";
        } else if (Color.lightGray.equals(selectedColor)) {
            return "Color.lightGray";
        } else if (Color.magenta.equals(selectedColor)) {
            return "Color.magenta";
        } else if (Color.orange.equals(selectedColor)) {
            return "Color.orange";
        } else if (Color.pink.equals(selectedColor)) {
            return "Color.pink";
        } else if (Color.red.equals(selectedColor)) {
            return "Color.red";
        } else if (Color.white.equals(selectedColor)) {
            return "Color.white";
        } else if (Color.yellow.equals(selectedColor)) {
            return "Color.yellow";
        }
        return null;
    }

}
