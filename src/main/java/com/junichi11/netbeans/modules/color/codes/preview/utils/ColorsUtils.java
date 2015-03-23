/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package com.junichi11.netbeans.modules.color.codes.preview.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author junichi11
 */
public final class ColorsUtils {

    public enum ColorType {

        HEX("#(?<codenumber>[0-9a-fA-F]{6,}|[0-9a-fA-F]{3,})"),
        CSS_INT_RGB(String.format(CSS_RGB_FORMAT, INT_RGB_VALUE_FORMAT, INT_RGB_VALUE_FORMAT, INT_RGB_VALUE_FORMAT)),
        CSS_PERCENT_RGB(String.format(CSS_RGB_FORMAT, PERCENT_VALUE_FORMAT, PERCENT_VALUE_FORMAT, PERCENT_VALUE_FORMAT)),
        CSS_INT_RGBA(String.format(CSS_RGBA_FORMAT, INT_RGB_VALUE_FORMAT, INT_RGB_VALUE_FORMAT, INT_RGB_VALUE_FORMAT, ALPHA_VALUE_FORMAT)),
        CSS_PERCENT_RGBA(String.format(CSS_RGBA_FORMAT, PERCENT_VALUE_FORMAT, PERCENT_VALUE_FORMAT, PERCENT_VALUE_FORMAT, ALPHA_VALUE_FORMAT));
        private final Pattern pattern;

        private ColorType(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public Pattern getPattern() {
            return pattern;
        }
    }

    static final String PERCENT_VALUE_FORMAT = "(100|[1-9]?[0-9])%"; // NOI18N
    static final String ALPHA_VALUE_FORMAT = "0|1|0\\.[1-9]{1,2}|0\\.0?[1-9]"; // NOI18N
    static final String INT_RGB_VALUE_FORMAT = "25[0-5]|2[0-4][0-9]|200|1[0-9][0-9]|100|[1-9]?[0-9]|[0-9]"; // NOI18N
    private static final String CSS_RGB_FORMAT = "(?<cssrgb>rgb\\((?<codenumber>(?<r>%s) *, *(?<g>%s) *, *(?<b>%s))\\))"; // NOI18N
    private static final String CSS_RGBA_FORMAT = "(?<cssrgba>rgba\\((?<codenumber>(?<r>%s) *, *(?<g>%s) *, *(?<b>%s) *, *(?<a>%s))\\))"; // NOI18N

    // group names
    private static final String GROUP_CODENUMBER = "codenumber"; // NOI18N
    private static final String GROUP_CSS_RGB = "cssrgb"; // NOI18N
    private static final String GROUP_CSS_RGBA = "cssrgba"; // NOI18N
    private static final String GROUP_RED = "r"; // NOI18N
    private static final String GROUP_GREEN = "g"; // NOI18N
    private static final String GROUP_BLUE = "b"; // NOI18N
    private static final String GROUP_ALPHA = "a"; // NOI18N

    private static final Comparator COLOR_VALUE_COMPARATOR = new ColorValueComparator();

    private ColorsUtils() {
    }

    /**
     * Get hex color codes. (e.g. #ffffff)
     *
     * @param line target text
     * @return hex color codes
     */
    public static List<String> getHexColorCodes(String line) {
        Matcher matcher = getColorMatcher(line, ColorType.HEX);
        int startPosition = 0;
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find(startPosition)) {
            final String colorCode = matcher.group(GROUP_CODENUMBER);
            int length = colorCode.length();
            String hexCode = colorCode;
            if (length == 3) {
                hexCode = convertToRRGGBB(colorCode);
            }

            if (hexCode.length() == 6) {
                colorCodes.add(String.format("#%s", hexCode)); // NOI18N
            }

            int indexOf = line.indexOf(colorCode, startPosition);
            startPosition = indexOf + length;
        }
        return colorCodes;
    }

    /**
     * Get hex color codes. (e.g. #ffffff)
     *
     * @param line target text
     * @return hex color codes
     */
    public static List<ColorValue> getHexColorCodes(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, ColorType.HEX);
        int startPosition = 0;
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find(startPosition)) {
            final String colorCode = matcher.group(GROUP_CODENUMBER);
            int length = colorCode.length();
            String hexCode = colorCode;
            if (length == 3) {
                hexCode = convertToRRGGBB(colorCode);
            }

            int indexOf = line.indexOf(colorCode, startPosition);
            startPosition = indexOf + length;
            if (hexCode.length() == 6) {
                ColorValue colorValue = new HexColorValue(String.format("#%s", hexCode), indexOf, startPosition, lineNumber); // NOI18N
                colorValues.add(colorValue);
            }

        }
        return colorValues;
    }

    /**
     * Get RGBs for int values for a line. (e.g. rgb(0, 0, 0))
     *
     * @param line a line
     * @return RGB codes
     */
    public static List<String> getCssIntRGBs(String line) {
        Matcher matcher = getColorMatcher(line, ColorType.CSS_INT_RGB);
        int startPosition = 0;
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find(startPosition)) {
            final String colorCode = matcher.group(GROUP_CSS_RGB);
            colorCodes.add(colorCode);

            int indexOf = line.indexOf(colorCode, startPosition);
            startPosition = indexOf + colorCode.length();
        }
        return colorCodes;
    }

    /**
     * Get int RGB color values for a line. (e.g. rgb(0, 0, 0))
     *
     * @param line a line
     * @return RGB codes
     */
    public static List<ColorValue> getCssIntRGBs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, ColorType.CSS_INT_RGB);
    }

    /**
     * Get % RGB color values for a line. (e.g. rgb(100%, 100%, 100%))
     *
     * @param line a line
     * @return RGB color values
     */
    public static List<ColorValue> getCssPercentRGBs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, ColorType.CSS_PERCENT_RGB);
    }

    /**
     * Get int RGBA colors for a line. (e.g. rgba(0, 0, 0, 0.1))
     *
     * @param line a line
     * @return RGBA color values
     */
    public static List<String> getCssIntRGBAs(String line) {
        Matcher matcher = getColorMatcher(line, ColorType.CSS_INT_RGBA);
        int startPosition = 0;
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find(startPosition)) {
            final String colorCode = matcher.group(GROUP_CSS_RGBA);
            colorCodes.add(colorCode);

            int indexOf = line.indexOf(colorCode, startPosition);
            startPosition = indexOf + colorCode.length();
        }
        return colorCodes;
    }

    /**
     * Get int RGBA color values for a line. (e.g. rgba(0, 0, 0, 0.1))
     *
     * @param line a line
     * @return RGBA color values
     */
    public static List<ColorValue> getCssIntRGBAs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, ColorType.CSS_INT_RGBA);
    }

    /**
     * Get % RGBA color values for a line. (e.g. rgba(0%, 0%, 0%, 0.1))
     *
     * @param line a line
     * @return RGBA color values
     */
    public static List<ColorValue> getCssPercentRGBAs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, ColorType.CSS_PERCENT_RGBA);
    }

    private static List<ColorValue> getCssColorValues(String line, int lineNumber, ColorType type) {
        Matcher matcher = getColorMatcher(line, type);
        ArrayList<ColorValue> colorCodes = new ArrayList<>();
        String groupName = getCssColorGroupName(type);
        while (matcher.find()) {
            final String colorCode = matcher.group(groupName);
            ColorValue colorValue = createCssColorValue(colorCode, matcher.start(), matcher.end(), lineNumber, type);
            if (colorValue != null) {
                colorCodes.add(colorValue);
            }
        }
        return colorCodes;
    }

    private static Matcher getColorMatcher(String line, ColorType type) {
        return type.getPattern().matcher(line);
    }

    private static String getCssColorGroupName(ColorType type) {
        switch (type) {
            case CSS_INT_RGB:
                return GROUP_CSS_RGB;
            case CSS_INT_RGBA:
                return GROUP_CSS_RGBA;
            case CSS_PERCENT_RGB:
                return GROUP_CSS_RGB;
            case CSS_PERCENT_RGBA:
                return GROUP_CSS_RGBA;
            default:
                throw new AssertionError();
        }
    }

    private static ColorValue createCssColorValue(String value, int startOffset, int endOffset, int lineNumber, ColorType type) {
        switch (type) {
            case CSS_INT_RGB:
                return new CssIntRGBColorValue(value, startOffset, endOffset, lineNumber);
            case CSS_INT_RGBA:
                return new CssIntRGBAColorValue(value, startOffset, endOffset, lineNumber);
            case CSS_PERCENT_RGB:
                return new CssPercentRGBColorValue(value, startOffset, endOffset, lineNumber);
            case CSS_PERCENT_RGBA:
                return new CssPercentRGBAColorValue(value, startOffset, endOffset, lineNumber);
            default:
                throw new AssertionError();
        }
    }

    /**
     * Convert a String to a Color. Hex color code and css rgb code are
     * available.
     *
     * @param code a color code
     * @return {@link Color} if specified color string matches supported
     * patterns, otherwise {@code null}
     */
    @CheckForNull
    public static Color decode(String code) {
        for (ColorType value : ColorType.values()) {
            Color color = decode(code, value);
            if (color != null) {
                return color;
            }
        }
        return null;
    }

    /**
     * Convert a String to a Color. Hex color code and css rgb code are
     * available.
     *
     * @param code
     * @param type
     * @return
     */
    @CheckForNull
    public static Color decode(String code, ColorType type) {
        if (code == null) {
            return null;
        }

        switch (type) {
            case HEX:
                return decodeHexColorCode(code);
            case CSS_INT_RGB:
                return decodeCssIntRGB(code);
            case CSS_PERCENT_RGB:
                return decodeCssPercentRGB(code);
            case CSS_INT_RGBA:
                return decodeCssIntRGBA(code);
            case CSS_PERCENT_RGBA:
                return decodeCssPercentRGBA(code);
            default:
                throw new AssertionError();
        }
    }

    @CheckForNull
    private static Color decodeHexColorCode(String code) {
        Matcher matcher = getColorMatcher(code, ColorType.HEX);
        if (matcher.matches()) {
            int length = code.length();
            if (length == 4) {
                return Color.decode(convertToRRGGBB(code));
            }
            if (length == 7) {
                return Color.decode(code);
            }
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssIntRGB(String code) {
        Matcher matcher = getColorMatcher(code, ColorType.CSS_INT_RGB);
        if (matcher.matches()) {
            return new Color(Integer.parseInt(matcher.group(GROUP_RED)), Integer.parseInt(matcher.group(GROUP_GREEN)), Integer.parseInt(matcher.group(GROUP_BLUE)));
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssPercentRGB(String code) {
        Matcher matcher = getColorMatcher(code, ColorType.CSS_PERCENT_RGB);
        if (matcher.matches()) {
            String red = matcher.group(GROUP_RED).replace("%", ""); // NOI18N
            String green = matcher.group(GROUP_GREEN).replace("%", ""); // NOI18N
            String blue = matcher.group(GROUP_BLUE).replace("%", ""); // NOI18N
            return new Color((float) (Float.parseFloat(red) * 0.01), (float) (Float.parseFloat(green) * 0.01), (float) (Float.parseFloat(blue) * 0.01));
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssIntRGBA(String code) {
        Matcher matcher = getColorMatcher(code, ColorType.CSS_INT_RGBA);
        if (matcher.matches()) {
            Float alpha = Float.valueOf(matcher.group(GROUP_ALPHA));
            int intAlpha = (int) (255 * alpha);
            return new Color(Integer.parseInt(matcher.group(GROUP_RED)), Integer.parseInt(matcher.group(GROUP_GREEN)), Integer.parseInt(matcher.group(GROUP_BLUE)), intAlpha);
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssPercentRGBA(String code) {
        Matcher matcher = getColorMatcher(code, ColorType.CSS_PERCENT_RGBA);
        if (matcher.matches()) {
            Float alpha = Float.valueOf(matcher.group(GROUP_ALPHA));
            String red = matcher.group(GROUP_RED).replace("%", ""); // NOI18N
            String green = matcher.group(GROUP_GREEN).replace("%", ""); // NOI18N
            String blue = matcher.group(GROUP_BLUE).replace("%", ""); // NOI18N
            return new Color((float) (Float.parseFloat(red) * 0.01), (float) (Float.parseFloat(green) * 0.01), (float) (Float.parseFloat(blue) * 0.01), alpha);
        }
        return null;
    }

    private static String convertToRRGGBB(String rgb) {
        boolean hasHashTag = rgb.startsWith("#"); // NOI18N
        StringBuilder sb = new StringBuilder();
        for (char c : rgb.toCharArray()) {
            sb.append(c).append(c);
        }
        if (hasHashTag) {
            return sb.substring(1);
        }
        return sb.toString();
    }

    /**
     * Get a foreground color for a background color.
     *
     * @param bgColor
     * @return {@code Color.WHITE} or {@code Color.BLACK}
     */
    public static Color getForeground(Color bgColor) {
        int brightness = getBrightness(bgColor);
        return brightness < 130 ? Color.WHITE : Color.BLACK;
    }

    private static int getBrightness(Color color) {
        return (color.getRed() * 299 + color.getGreen() * 578 + color.getBlue() * 114) / 1000;
    }

    /**
     * Sort {@link ColorValue}s.
     *
     * @param colorValues
     */
    public static void sort(List<ColorValue> colorValues) {
        Collections.sort(colorValues, COLOR_VALUE_COMPARATOR);
    }

    private static class ColorValueComparator implements Comparator<ColorValue> {

        public ColorValueComparator() {
        }

        @Override
        public int compare(ColorValue c1, ColorValue c2) {
            int line1 = c1.getLine();
            int line2 = c2.getLine();
            if (line1 == line2) {
                int startOffset1 = c1.getStartOffset();
                int startOffset2 = c2.getStartOffset();
                return startOffset1 - startOffset2;
            } else {
                return line1 - line2;
            }
        }
    }
}
