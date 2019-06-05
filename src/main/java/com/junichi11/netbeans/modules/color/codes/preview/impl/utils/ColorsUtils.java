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
package com.junichi11.netbeans.modules.color.codes.preview.impl.utils;

import com.junichi11.netbeans.modules.color.codes.preview.api.OffsetRange;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssHSLAColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssHSLColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssIntRGBAColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssIntRGBColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssPercentRGBAColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.CssPercentRGBColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.HexColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.IntType;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaIntRGBAColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaIntRGBAsColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaIntRGBColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaIntRGBsColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaStandardColor;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.JavaStandardColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.NamedColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.impl.colors.RGBAIntTypes;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Pair;

/**
 *
 * @author junichi11
 */
public final class ColorsUtils {

    private static final int SHORT_HEX_COLOR_CODE_LENGTH = 3;
    private static final int SHORT_HEX_COLOR_CODE_WITH_HASH_LENGTH = 4;
    private static final int HEX_COLOR_CODE_LENGTH = 6;
    private static final int HEX_COLOR_CODE_WITH_HASH_LENGTH = 7;

    private static final String HEX_VALUE_FORMAT = "#%02x%02x%02x"; // NOI18N
    private static final String RGB_VALUE_FORMAT = "rgb(%s, %s, %s)"; // NOI18N
    private static final String RGBA_VALUE_FORMAT = "rgba(%s, %s, %s, %s)"; // NOI18N
    private static final String HSL_VALUE_FORMAT = "hsl(%s, %s, %s)"; // NOI18N
    private static final String HSLA_VALUE_FORMAT = "hsla(%s, %s, %s, %s)"; // NOI18N
    // group names
    private static final String GROUP_CODENUMBER = "codenumber"; // NOI18N
    private static final String GROUP_CSS_RGB = "cssrgb"; // NOI18N
    private static final String GROUP_CSS_RGBA = "cssrgba"; // NOI18N
    private static final String GROUP_CSS_HSL = "csshsl"; // NOI18N
    private static final String GROUP_CSS_HSLA = "csshsla"; // NOI18N
    private static final String GROUP_JAVA_STANDARD = "javastandard";// NOI18N
    private static final String GROUP_JAVA_RGB = "javargb";// NOI18N
    private static final String GROUP_JAVA_RGBA = "javargba";// NOI18N
    private static final String GROUP_RED = "r"; // NOI18N
    private static final String GROUP_GREEN = "g"; // NOI18N
    private static final String GROUP_BLUE = "b"; // NOI18N
    private static final String GROUP_ALPHA = "a"; // NOI18N
    private static final String GROUP_RGBA = "intrgba"; // NOI18N
    private static final String GROUP_HEX_RED = "hexr"; // NOI18N
    private static final String GROUP_HEX_GREEN = "hexg"; // NOI18N
    private static final String GROUP_HEX_BLUE = "hexb"; // NOI18N
    private static final String GROUP_HEX_ALPHA = "hexa"; // NOI18N
    private static final String GROUP_HEX_RGBA = "hexrgba"; // NOI18N
    private static final String GROUP_HUE = "h"; // NOI18N
    private static final String GROUP_SATURATION = "s"; // NOI18N
    private static final String GROUP_LIGHTNESS = "l"; // NOI18N
    private static final String GROUP_BOOL = "bool"; // NOI18N
    private static final String GROUP_COLOR_NAME = "colorname"; // NOI18N

    private static final Map<String, String> NAMED_COLOR_TABLE = new HashMap<>();

    static {
        // red html color names
        NAMED_COLOR_TABLE.put("indianred", "#cd5c5c"); // NOI18N
        NAMED_COLOR_TABLE.put("lightcoral", "#f08080"); // NOI18N
        NAMED_COLOR_TABLE.put("salmon", "#fa8072"); // NOI18N
        NAMED_COLOR_TABLE.put("darksalmon", "#e9967a"); // NOI18N
        NAMED_COLOR_TABLE.put("lightsalmon", "#ffa07a"); // NOI18N
        NAMED_COLOR_TABLE.put("crimson", "#dc143c"); // NOI18N
        NAMED_COLOR_TABLE.put("red", "#ff0000"); // NOI18N
        NAMED_COLOR_TABLE.put("firebrick", "#b22222"); // NOI18N
        NAMED_COLOR_TABLE.put("darkred", "#8b0000"); // NOI18N

        // pink html color names
        NAMED_COLOR_TABLE.put("pink", "#ffc0cb"); // NOI18N
        NAMED_COLOR_TABLE.put("lightpink", "#ffb6c1"); // NOI18N
        NAMED_COLOR_TABLE.put("hotpink", "#ff69b4"); // NOI18N
        NAMED_COLOR_TABLE.put("deeppink", "#ff1493"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumvioletred", "#c71585"); // NOI18N
        NAMED_COLOR_TABLE.put("palevioletred", "#db7093"); // NOI18N

        // orange html color names
        NAMED_COLOR_TABLE.put("lightsalmon", "#ffa07a"); // NOI18N
        NAMED_COLOR_TABLE.put("coral", "#ff7f50"); // NOI18N
        NAMED_COLOR_TABLE.put("tomato", "#ff6347"); // NOI18N
        NAMED_COLOR_TABLE.put("orangered", "#ff4500"); // NOI18N
        NAMED_COLOR_TABLE.put("darkorange", "#ff8c00"); // NOI18N
        NAMED_COLOR_TABLE.put("orange", "#ffa500"); // NOI18N

        // yellow html color names
        NAMED_COLOR_TABLE.put("gold", "#ffd700"); // NOI18N
        NAMED_COLOR_TABLE.put("yellow", "#ffff00"); // NOI18N
        NAMED_COLOR_TABLE.put("lightyellow", "#ffffe0"); // NOI18N
        NAMED_COLOR_TABLE.put("lemonchiffon", "#fffacd"); // NOI18N
        NAMED_COLOR_TABLE.put("lightgoldenrodyellow", "#fafad2"); // NOI18N
        NAMED_COLOR_TABLE.put("papayawhip", "#ffefd5"); // NOI18N
        NAMED_COLOR_TABLE.put("moccasin", "#ffe4b5"); // NOI18N
        NAMED_COLOR_TABLE.put("peachpuff", "#ffdab9"); // NOI18N
        NAMED_COLOR_TABLE.put("palegoldenrod", "#eee8aa"); // NOI18N
        NAMED_COLOR_TABLE.put("khaki", "#f0e68c"); // NOI18N
        NAMED_COLOR_TABLE.put("darkkhaki", "#bdb76b"); // NOI18N

        // purple html color names
        NAMED_COLOR_TABLE.put("lavender", "#e6e6fa"); // NOI18N
        NAMED_COLOR_TABLE.put("thistle", "#d8bfd8"); // NOI18N
        NAMED_COLOR_TABLE.put("plum", "#dda0dd"); // NOI18N
        NAMED_COLOR_TABLE.put("violet", "#ee82ee"); // NOI18N
        NAMED_COLOR_TABLE.put("orchid", "#da70d6"); // NOI18N
        NAMED_COLOR_TABLE.put("fuchsia", "#ff00ff"); // NOI18N
        NAMED_COLOR_TABLE.put("magenta", "#ff00ff"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumorchid", "#ba55d3"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumpurple", "#9370db"); // NOI18N
        NAMED_COLOR_TABLE.put("rebeccapurple", "#663399"); // NOI18N
        NAMED_COLOR_TABLE.put("blueviolet", "#8a2be2"); // NOI18N
        NAMED_COLOR_TABLE.put("darkviolet", "#9400d3"); // NOI18N
        NAMED_COLOR_TABLE.put("darkorchid", "#9932cc"); // NOI18N
        NAMED_COLOR_TABLE.put("darkmagenta", "#8b008b"); // NOI18N
        NAMED_COLOR_TABLE.put("purple", "#800080"); // NOI18N
        NAMED_COLOR_TABLE.put("indigo", "#4b0082"); // NOI18N
        NAMED_COLOR_TABLE.put("slateblue", "#6a5acd"); // NOI18N
        NAMED_COLOR_TABLE.put("darkslateblue", "#483d8b"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumslateblue", "#7b68ee"); // NOI18N

        // green html color names
        NAMED_COLOR_TABLE.put("greenyellow", "#adff2f"); // NOI18N
        NAMED_COLOR_TABLE.put("chartreuse", "#7fff00"); // NOI18N
        NAMED_COLOR_TABLE.put("lawngreen", "#7cfc00"); // NOI18N
        NAMED_COLOR_TABLE.put("lime", "#00ff00"); // NOI18N
        NAMED_COLOR_TABLE.put("limegreen", "#32cd32"); // NOI18N
        NAMED_COLOR_TABLE.put("palegreen", "#98fb98"); // NOI18N
        NAMED_COLOR_TABLE.put("lightgreen", "#90ee90"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumspringgreen", "#00fa9a"); // NOI18N
        NAMED_COLOR_TABLE.put("springgreen", "#00ff7f"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumseagreen", "#3cb371"); // NOI18N
        NAMED_COLOR_TABLE.put("seagreen", "#2e8b57"); // NOI18N
        NAMED_COLOR_TABLE.put("forestgreen", "#228b22"); // NOI18N
        NAMED_COLOR_TABLE.put("green", "#008000"); // NOI18N
        NAMED_COLOR_TABLE.put("darkgreen", "#006400"); // NOI18N
        NAMED_COLOR_TABLE.put("yellowgreen", "#9acd32"); // NOI18N
        NAMED_COLOR_TABLE.put("olivedrab", "#6b8e23"); // NOI18N
        NAMED_COLOR_TABLE.put("olive", "#808000"); // NOI18N
        NAMED_COLOR_TABLE.put("darkolivegreen", "#556b2f"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumaquamarine", "#66cdaa"); // NOI18N
        NAMED_COLOR_TABLE.put("darkseagreen", "#8fbc8b"); // NOI18N
        NAMED_COLOR_TABLE.put("lightseagreen", "#20b2aa"); // NOI18N
        NAMED_COLOR_TABLE.put("darkcyan", "#008b8b"); // NOI18N
        NAMED_COLOR_TABLE.put("teal", "#008080"); // NOI18N

        // blue html color names
        NAMED_COLOR_TABLE.put("aqua", "#00ffff"); // NOI18N
        NAMED_COLOR_TABLE.put("cyan", "#00ffff"); // NOI18N
        NAMED_COLOR_TABLE.put("lightcyan", "#e0ffff"); // NOI18N
        NAMED_COLOR_TABLE.put("paleturquoise", "#afeeee"); // NOI18N
        NAMED_COLOR_TABLE.put("aquamarine", "#7fffd4"); // NOI18N
        NAMED_COLOR_TABLE.put("turquoise", "#40e0d0"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumturquoise", "#48d1cc"); // NOI18N
        NAMED_COLOR_TABLE.put("darkturquoise", "#00ced1"); // NOI18N
        NAMED_COLOR_TABLE.put("cadetblue", "#5f9ea0"); // NOI18N
        NAMED_COLOR_TABLE.put("steelblue", "#4682b4"); // NOI18N
        NAMED_COLOR_TABLE.put("lightsteelblue", "#b0c4de"); // NOI18N
        NAMED_COLOR_TABLE.put("powderblue", "#b0e0e6"); // NOI18N
        NAMED_COLOR_TABLE.put("lightblue", "#add8e6"); // NOI18N
        NAMED_COLOR_TABLE.put("skyblue", "#87ceeb"); // NOI18N
        NAMED_COLOR_TABLE.put("lightskyblue", "#87cefa"); // NOI18N
        NAMED_COLOR_TABLE.put("deepskyblue", "#00bfff"); // NOI18N
        NAMED_COLOR_TABLE.put("dodgerblue", "#1e90ff"); // NOI18N
        NAMED_COLOR_TABLE.put("cornflowerblue", "#6495ed"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumslateblue", "#7b68ee"); // NOI18N
        NAMED_COLOR_TABLE.put("royalblue", "#4169e1"); // NOI18N
        NAMED_COLOR_TABLE.put("blue", "#0000ff"); // NOI18N
        NAMED_COLOR_TABLE.put("mediumblue", "#0000cd"); // NOI18N
        NAMED_COLOR_TABLE.put("darkblue", "#00008b"); // NOI18N
        NAMED_COLOR_TABLE.put("navy", "#000080"); // NOI18N
        NAMED_COLOR_TABLE.put("midnightblue", "#191970"); // NOI18N

        // brown html color names
        NAMED_COLOR_TABLE.put("cornsilk", "#fff8dc"); // NOI18N
        NAMED_COLOR_TABLE.put("blanchedalmond", "#ffebcd"); // NOI18N
        NAMED_COLOR_TABLE.put("bisque", "#ffe4c4"); // NOI18N
        NAMED_COLOR_TABLE.put("navajowhite", "#ffdead"); // NOI18N
        NAMED_COLOR_TABLE.put("wheat", "#f5deb3"); // NOI18N
        NAMED_COLOR_TABLE.put("burlywood", "#deb887"); // NOI18N
        NAMED_COLOR_TABLE.put("tan", "#d2b48c"); // NOI18N
        NAMED_COLOR_TABLE.put("rosybrown", "#bc8f8f"); // NOI18N
        NAMED_COLOR_TABLE.put("sandybrown", "#f4a460"); // NOI18N
        NAMED_COLOR_TABLE.put("goldenrod", "#daa520"); // NOI18N
        NAMED_COLOR_TABLE.put("darkgoldenrod", "#b8860b"); // NOI18N
        NAMED_COLOR_TABLE.put("peru", "#cd853f"); // NOI18N
        NAMED_COLOR_TABLE.put("chocolate", "#d2691e"); // NOI18N
        NAMED_COLOR_TABLE.put("saddlebrown", "#8b4513"); // NOI18N
        NAMED_COLOR_TABLE.put("sienna", "#a0522d"); // NOI18N
        NAMED_COLOR_TABLE.put("brown", "#a52a2a"); // NOI18N
        NAMED_COLOR_TABLE.put("maroon", "#800000"); // NOI18N

        // white html color names
        NAMED_COLOR_TABLE.put("white", "#ffffff"); // NOI18N
        NAMED_COLOR_TABLE.put("snow", "#fffafa"); // NOI18N
        NAMED_COLOR_TABLE.put("honeydew", "#f0fff0"); // NOI18N
        NAMED_COLOR_TABLE.put("mintcream", "#f5fffa"); // NOI18N
        NAMED_COLOR_TABLE.put("azure", "#f0ffff"); // NOI18N
        NAMED_COLOR_TABLE.put("aliceblue", "#f0f8ff"); // NOI18N
        NAMED_COLOR_TABLE.put("ghostwhite", "#f8f8ff"); // NOI18N
        NAMED_COLOR_TABLE.put("whitesmoke", "#f5f5f5"); // NOI18N
        NAMED_COLOR_TABLE.put("seashell", "#fff5ee"); // NOI18N
        NAMED_COLOR_TABLE.put("beige", "#f5f5dc"); // NOI18N
        NAMED_COLOR_TABLE.put("oldlace", "#fdf5e6"); // NOI18N
        NAMED_COLOR_TABLE.put("floralwhite", "#fffaf0"); // NOI18N
        NAMED_COLOR_TABLE.put("ivory", "#fffff0"); // NOI18N
        NAMED_COLOR_TABLE.put("antiquewhite", "#faebd7"); // NOI18N
        NAMED_COLOR_TABLE.put("linen", "#faf0e6"); // NOI18N
        NAMED_COLOR_TABLE.put("lavenderblush", "#fff0f5"); // NOI18N
        NAMED_COLOR_TABLE.put("mistyrose", "#ffe4e1"); // NOI18N

        // gray html color names
        NAMED_COLOR_TABLE.put("gainsboro", "#dcdcdc"); // NOI18N
        NAMED_COLOR_TABLE.put("lightgray", "#d3d3d3"); // NOI18N
        NAMED_COLOR_TABLE.put("silver", "#c0c0c0"); // NOI18N
        NAMED_COLOR_TABLE.put("darkgray", "#a9a9a9"); // NOI18N
        NAMED_COLOR_TABLE.put("gray", "#808080"); // NOI18N
        NAMED_COLOR_TABLE.put("dimgray", "#696969"); // NOI18N
        NAMED_COLOR_TABLE.put("lightslategray", "#778899"); // NOI18N
        NAMED_COLOR_TABLE.put("slategray", "#708090"); // NOI18N
        NAMED_COLOR_TABLE.put("darkslategray", "#2f4f4f"); // NOI18N
        NAMED_COLOR_TABLE.put("black", "#000000"); // NOI18N
    }

    private ColorsUtils() {
    }

    /**
     * Get hex color codes. (e.g. #ffffff)
     *
     * @param line target text
     * @return hex color codes
     */
    public static List<String> getHexColorCodes(String line) {
        Matcher matcher = getColorMatcher(line, HexCssColorType.HEX);
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_CODENUMBER);
            int length = colorCode.length();
            String hexCode = colorCode;
            if (length == SHORT_HEX_COLOR_CODE_LENGTH) {
                hexCode = convertToRRGGBB(colorCode);
            }

            if (hexCode.length() == HEX_COLOR_CODE_LENGTH) {
                colorCodes.add(String.format("#%s", hexCode)); // NOI18N
            }
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
        Matcher matcher = getColorMatcher(line, HexCssColorType.HEX);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_CODENUMBER);
            int length = colorCode.length();
            String hexCode = colorCode;
            if (length == SHORT_HEX_COLOR_CODE_LENGTH) {
                hexCode = convertToRRGGBB(colorCode);
            }

            if (hexCode.length() == HEX_COLOR_CODE_LENGTH) {
                ColorValue colorValue = new HexColorValue(String.format("#%s", hexCode), new OffsetRange(matcher.start(), matcher.end()), lineNumber); // NOI18N
                colorValues.add(colorValue);
            }

        }
        return colorValues;
    }

    /**
     * Get named colors. (e.g. red)
     *
     * @param line target text
     * @return named colors
     */
    public static List<ColorValue> getNamedColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, HexCssColorType.NAMED_COLORS);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String namedColor = matcher.group(GROUP_COLOR_NAME);
            ColorValue colorValue = new NamedColorValue(namedColor, new OffsetRange(matcher.start() + 1, matcher.end() - 1), lineNumber);
            colorValues.add(colorValue);
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
        Matcher matcher = getColorMatcher(line, HexCssColorType.CSS_INT_RGB);
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_CSS_RGB);
            colorCodes.add(colorCode);
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
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_INT_RGB);
    }

    /**
     * Get % RGB color values for a line. (e.g. rgb(100%, 100%, 100%))
     *
     * @param line a line
     * @return RGB color values
     */
    public static List<ColorValue> getCssPercentRGBs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_PERCENT_RGB);
    }

    /**
     * Get int RGBA colors for a line. (e.g. rgba(0, 0, 0, 0.1))
     *
     * @param line a line
     * @return RGBA color values
     */
    public static List<String> getCssIntRGBAs(String line) {
        Matcher matcher = getColorMatcher(line, HexCssColorType.CSS_INT_RGBA);
        ArrayList<String> colorCodes = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_CSS_RGBA);
            colorCodes.add(colorCode);
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
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_INT_RGBA);
    }

    /**
     * Get % RGBA color values for a line. (e.g. rgba(0%, 0%, 0%, 0.1))
     *
     * @param line a line
     * @return RGBA color values
     */
    public static List<ColorValue> getCssPercentRGBAs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_PERCENT_RGBA);
    }

    /**
     * Get HSL color values for a line. (e.g. hsl(0, 100%, 100%))
     *
     * @param line a line
     * @return HSL ColorValues
     */
    public static List<ColorValue> getCssHSLs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_HSL);
    }

    /**
     * Get HSLA color values for a line. (e.g. hsla(0, 100%, 100%, 0.1))
     *
     * @param line a line
     * @return HSLA ColorValues
     */
    public static List<ColorValue> getCssHSLAs(String line, int lineNumber) {
        return getCssColorValues(line, lineNumber, HexCssColorType.CSS_HSLA);
    }

    /**
     * Get css ColorValues.
     *
     * @param line line text
     * @param lineNumber line number
     * @param type HexCssColorType
     * @return ColorValues
     */
    private static List<ColorValue> getCssColorValues(String line, int lineNumber, HexCssColorType type) {
        Matcher matcher = getColorMatcher(line, type);
        ArrayList<ColorValue> colorCodes = new ArrayList<>();
        String groupName = getCssColorGroupName(type);
        while (matcher.find()) {
            final String colorCode = matcher.group(groupName);
            ColorValue colorValue = createCssColorValue(colorCode, new OffsetRange(matcher.start(), matcher.end()), lineNumber, type);
            if (colorValue != null) {
                colorCodes.add(colorValue);
            }
        }
        return colorCodes;
    }

    /**
     * Get Java standard colors.
     *
     * @param line the line text
     * @param lineNumber the line number
     * @return ColorValues
     */
    public static List<ColorValue> getJavaStandardColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, JavaColorType.JAVA_STANDARD_COLOR);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorName = matcher.group(GROUP_COLOR_NAME);
            JavaStandardColor stdColor = JavaStandardColor.valueOf(colorName);
            ColorValue colorValue = new JavaStandardColorValue(matcher.group(GROUP_JAVA_STANDARD), new OffsetRange(matcher.start(), matcher.end()), lineNumber, stdColor.getColor());
            colorValues.add(colorValue);
        }
        return colorValues;
    }

    /**
     * Get Java int RGB colors.
     *
     * @param line the line text
     * @param lineNumber the line number
     * @return ColorValues
     */
    public static List<ColorValue> getJavaIntRGBColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, JavaColorType.JAVA_INT_RGB);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_JAVA_RGB);
            Pair<String, IntType> rgbaPair = getJavaIntRGBAValue(matcher, GROUP_RGBA);
            // prevent NubmerFormatException
            int rgba = (int) Long.parseLong(rgbaPair.first(), rgbaPair.second().getRadix());
            Color color = new Color(rgba);
            RGBAIntTypes rgbaIntTypes = new RGBAIntTypes(rgbaPair.second());
            ColorValue colorValue = new JavaIntRGBColorValue(colorCode, new OffsetRange(matcher.start(), matcher.end()), lineNumber, color, rgbaIntTypes);
            colorValues.add(colorValue);
        }
        return colorValues;
    }

    /**
     * Get Java int RGBA colors.
     *
     * @param line the line text
     * @param lineNumber the line number
     * @return ColorValues
     */
    public static List<ColorValue> getJavaIntRGBAColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, JavaColorType.JAVA_INT_RGBA);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_JAVA_RGBA);
            Pair<String, IntType> rgbaPair = getJavaIntRGBAValue(matcher, GROUP_RGBA);
            // prevent NubmerFormatException
            int rgba = (int) Long.parseLong(rgbaPair.first(), rgbaPair.second().getRadix());
            String bool = matcher.group(GROUP_BOOL);
            boolean hasAlpha = Boolean.parseBoolean(bool);
            Color color = new Color(rgba, hasAlpha);
            RGBAIntTypes rgbaIntTypes = new RGBAIntTypes(rgbaPair.second());
            ColorValue colorValue = new JavaIntRGBAColorValue(colorCode, new OffsetRange(matcher.start(), matcher.end()), lineNumber, color, hasAlpha, rgbaIntTypes);
            colorValues.add(colorValue);
        }
        return colorValues;
    }

    /**
     * Get Java int RGB colors.
     *
     * @param line the line text
     * @param lineNumber the line number
     * @return ColorValues
     */
    public static List<ColorValue> getJavaIntRGBsColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, JavaColorType.JAVA_INT_R_G_B);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_JAVA_RGB);
            Pair<String, IntType> red = getJavaIntRGBAValue(matcher, GROUP_RED);
            Pair<String, IntType> green = getJavaIntRGBAValue(matcher, GROUP_GREEN);
            Pair<String, IntType> blue = getJavaIntRGBAValue(matcher, GROUP_BLUE);
            int r = Integer.parseInt(red.first(), red.second().getRadix());
            int g = Integer.parseInt(green.first(), green.second().getRadix());
            int b = Integer.parseInt(blue.first(), blue.second().getRadix());
            Color color = new Color(r, g, b);
            RGBAIntTypes rgbaIntTypes = new RGBAIntTypes(red.second(), green.second(), blue.second());
            ColorValue colorValue = new JavaIntRGBsColorValue(colorCode, new OffsetRange(matcher.start(), matcher.end()), lineNumber, color, rgbaIntTypes);
            colorValues.add(colorValue);
        }
        return colorValues;
    }

    /**
     * Get Java int RGBA colors.
     *
     * @param line the line text
     * @param lineNumber the line number
     * @return ColorValues
     */
    public static List<ColorValue> getJavaIntRGBAsColors(String line, int lineNumber) {
        Matcher matcher = getColorMatcher(line, JavaColorType.JAVA_INT_R_G_B_A);
        ArrayList<ColorValue> colorValues = new ArrayList<>();
        while (matcher.find()) {
            final String colorCode = matcher.group(GROUP_JAVA_RGBA);
            Pair<String, IntType> red = getJavaIntRGBAValue(matcher, GROUP_RED);
            Pair<String, IntType> green = getJavaIntRGBAValue(matcher, GROUP_GREEN);
            Pair<String, IntType> blue = getJavaIntRGBAValue(matcher, GROUP_BLUE);
            Pair<String, IntType> alpha = getJavaIntRGBAValue(matcher, GROUP_ALPHA);
            int r = Integer.parseInt(red.first(), red.second().getRadix());
            int g = Integer.parseInt(green.first(), green.second().getRadix());
            int b = Integer.parseInt(blue.first(), blue.second().getRadix());
            int a = Integer.parseInt(alpha.first(), alpha.second().getRadix());
            Color color = new Color(r, g, b, a);
            RGBAIntTypes rgbaIntTypes = new RGBAIntTypes(red.second(), green.second(), blue.second(), alpha.second());
            ColorValue colorValue = new JavaIntRGBAsColorValue(colorCode, new OffsetRange(matcher.start(), matcher.end()), lineNumber, color, rgbaIntTypes);
            colorValues.add(colorValue);
        }
        return colorValues;
    }

    private static Pair<String, IntType> getJavaIntRGBAValue(Matcher matcher, String decimalIntGroup) {
        String value = matcher.group(decimalIntGroup);
        IntType intType = IntType.Decimal;
        if (value == null || value.isEmpty()) {
            switch (decimalIntGroup) {
                case GROUP_RED:
                    value = matcher.group(GROUP_HEX_RED);
                    break;
                case GROUP_GREEN:
                    value = matcher.group(GROUP_HEX_GREEN);
                    break;
                case GROUP_BLUE:
                    value = matcher.group(GROUP_HEX_BLUE);
                    break;
                case GROUP_ALPHA:
                    value = matcher.group(GROUP_HEX_ALPHA);
                    break;
                case GROUP_RGBA:
                    value = matcher.group(GROUP_HEX_RGBA);
                    break;
                default:
                    throw new AssertionError();
            }
            intType = IntType.Hex;
        }
        assert value != null;
        return Pair.of(value, intType);
    }

    private static Matcher getColorMatcher(String line, ColorType type) {
        return type.getPattern().matcher(line);
    }

    private static String getCssColorGroupName(HexCssColorType type) {
        switch (type) {
            case CSS_INT_RGB:
                return GROUP_CSS_RGB;
            case CSS_INT_RGBA:
                return GROUP_CSS_RGBA;
            case CSS_PERCENT_RGB:
                return GROUP_CSS_RGB;
            case CSS_PERCENT_RGBA:
                return GROUP_CSS_RGBA;
            case CSS_HSL:
                return GROUP_CSS_HSL;
            case CSS_HSLA:
                return GROUP_CSS_HSLA;
            default:
                throw new AssertionError();
        }
    }

    private static ColorValue createCssColorValue(String value, OffsetRange offsetRange, int lineNumber, HexCssColorType type) {
        switch (type) {
            case CSS_INT_RGB:
                return new CssIntRGBColorValue(value, offsetRange, lineNumber);
            case CSS_INT_RGBA:
                return new CssIntRGBAColorValue(value, offsetRange, lineNumber);
            case CSS_PERCENT_RGB:
                return new CssPercentRGBColorValue(value, offsetRange, lineNumber);
            case CSS_PERCENT_RGBA:
                return new CssPercentRGBAColorValue(value, offsetRange, lineNumber);
            case CSS_HSL:
                return new CssHSLColorValue(value, offsetRange, lineNumber);
            case CSS_HSLA:
                return new CssHSLAColorValue(value, offsetRange, lineNumber);
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
        for (HexCssColorType value : HexCssColorType.values()) {
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
    public static Color decode(String code, HexCssColorType type) {
        if (code == null) {
            return null;
        }

        switch (type) {
            case HEX:
                return decodeHexColorCode(code);
            case NAMED_COLORS:
                return decodeNamedColor(code);
            case CSS_INT_RGB:
                return decodeCssIntRGB(code);
            case CSS_PERCENT_RGB:
                return decodeCssPercentRGB(code);
            case CSS_INT_RGBA:
                return decodeCssIntRGBA(code);
            case CSS_PERCENT_RGBA:
                return decodeCssPercentRGBA(code);
            case CSS_HSL:
                return decodeCssHSL(code);
            case CSS_HSLA:
                return decodeCssHSLA(code);
            default:
                throw new AssertionError();
        }
    }

    @CheckForNull
    private static Color decodeHexColorCode(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.HEX);
        if (matcher.matches()) {
            int length = code.length();
            if (length == SHORT_HEX_COLOR_CODE_WITH_HASH_LENGTH) {
                return Color.decode(convertToRRGGBB(code));
            }
            if (length == HEX_COLOR_CODE_WITH_HASH_LENGTH) {
                return Color.decode(code);
            }
        }
        return null;
    }

    @CheckForNull
    private static Color decodeNamedColor(String code) {
        // Add white spaces to the top and the end to parse with the regex for named colors
        String namedColorCode = String.format(" %s ", code); // NOI18N
        Matcher matcher = getColorMatcher(namedColorCode, HexCssColorType.NAMED_COLORS);
        if (matcher.matches()) {
            String hexColorCode = NAMED_COLOR_TABLE.get(matcher.group(GROUP_COLOR_NAME).toLowerCase());
            return decodeHexColorCode(hexColorCode);
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssIntRGB(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_INT_RGB);
        if (matcher.matches()) {
            return new Color(Integer.parseInt(matcher.group(GROUP_RED)), Integer.parseInt(matcher.group(GROUP_GREEN)), Integer.parseInt(matcher.group(GROUP_BLUE)));
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssPercentRGB(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_PERCENT_RGB);
        if (matcher.matches()) {
            String red = matcher.group(GROUP_RED).replace("%", ""); // NOI18N
            String green = matcher.group(GROUP_GREEN).replace("%", ""); // NOI18N
            String blue = matcher.group(GROUP_BLUE).replace("%", ""); // NOI18N
            return new Color((float) (Float.parseFloat(red) * 0.01f), (float) (Float.parseFloat(green) * 0.01f), (float) (Float.parseFloat(blue) * 0.01f));
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssIntRGBA(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_INT_RGBA);
        if (matcher.matches()) {
            Float alpha = Float.valueOf(matcher.group(GROUP_ALPHA));
            int intAlpha = (int) (255 * alpha);
            return new Color(Integer.parseInt(matcher.group(GROUP_RED)), Integer.parseInt(matcher.group(GROUP_GREEN)), Integer.parseInt(matcher.group(GROUP_BLUE)), intAlpha);
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssPercentRGBA(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_PERCENT_RGBA);
        if (matcher.matches()) {
            Float alpha = Float.valueOf(matcher.group(GROUP_ALPHA));
            String red = matcher.group(GROUP_RED).replace("%", ""); // NOI18N
            String green = matcher.group(GROUP_GREEN).replace("%", ""); // NOI18N
            String blue = matcher.group(GROUP_BLUE).replace("%", ""); // NOI18N
            return new Color((float) (Float.parseFloat(red) * 0.01f), (float) (Float.parseFloat(green) * 0.01f), (float) (Float.parseFloat(blue) * 0.01f), alpha);
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssHSL(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_HSL);
        if (matcher.matches()) {
            String hue = matcher.group(GROUP_HUE);
            String saturation = matcher.group(GROUP_SATURATION).replace("%", ""); // NOI18N
            String lightness = matcher.group(GROUP_LIGHTNESS).replace("%", ""); // NOI18N
            float huef = (float) (Float.parseFloat(hue) / 360.0f);
            return getHSLColor(huef, (float) (Float.parseFloat(saturation) * 0.01f), (float) (Float.parseFloat(lightness) * 0.01f));
        }
        return null;
    }

    @CheckForNull
    private static Color decodeCssHSLA(String code) {
        Matcher matcher = getColorMatcher(code, HexCssColorType.CSS_HSLA);
        if (matcher.matches()) {
            String hue = matcher.group(GROUP_HUE);
            String saturation = matcher.group(GROUP_SATURATION).replace("%", ""); // NOI18N
            String lightness = matcher.group(GROUP_LIGHTNESS).replace("%", ""); // NOI18N
            Float alpha = Float.valueOf(matcher.group(GROUP_ALPHA));
            int intAlpha = (int) (255 * alpha);
            float huef = (float) (Float.parseFloat(hue) / 360.0f);
            Color hslColor = getHSLColor(huef, (float) (Float.parseFloat(saturation) * 0.01f), (float) (Float.parseFloat(lightness) * 0.01f));
            return new Color(hslColor.getRed(), hslColor.getGreen(), hslColor.getBlue(), intAlpha);
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
     * Get an HSL Color.
     * {@link http://www.w3.org/TR/2011/REC-css3-color-20110607/#hsl-color}
     *
     * @param h a hue value [0,1]
     * @param s a saturation value [0,1]
     * @param l a lightness value [0,1]
     * @return Color
     */
    public static Color getHSLColor(float h, float s, float l) {
        float[] rgb = hslToRgb(h, s, l);
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Get an HSLA Color.
     *
     * @param h a hue value [0,1]
     * @param s a saturation value [0,1]
     * @param l a lightness value [0,1]
     * @param a an alpha value [0,1]
     * @return Color
     */
    public static Color getHSLAColor(float h, float s, float l, float a) {
        float[] rgb = hslToRgb(h, s, l);
        return new Color(rgb[0], rgb[1], rgb[2], a);
    }

    private static float[] hslToRgb(float h, float s, float l) {
        if (s == 0.0f) {
            return new float[]{l, l, l};
        }
        float m2;
        if (l <= 0.5f) {
            m2 = l * (s + 1.0f);
        } else {
            m2 = l + s - l * s;
        }

        float m1 = l * 2.0f - m2;
        float r = hueToRgb(m1, m2, h + 1.0f / 3.0f);
        float g = hueToRgb(m1, m2, h);
        float b = hueToRgb(m1, m2, h - 1.0f / 3.0f);
        return new float[]{r, g, b};
    }

    private static float hueToRgb(float m1, float m2, float h) {
        if (h < 0.0f) {
            h = h + 1.0f;
        }
        if (h > 1.0f) {
            h = h - 1.0f;
        }
        if (h * 6.0f < 1.0f) {
            return m1 + (m2 - m1) * h * 6.0f;
        }
        if (h * 2.0f < 1.0f) {
            return m2;
        }
        if (h * 3.0f < 2.0f) {
            return m1 + (m2 - m1) * (2.0f / 3.0f - h) * 6.0f;
        }
        return m1;
    }

    /**
     * RGB to HSL
     *
     * @param red the red value [0,255]
     * @param green the green value [0,255]
     * @param blue the blue value [0,255]
     * @return HSL values as an array. h:[0,360], s:[0,1], l[0,1]
     */
    public static float[] rgbToHsl(int red, int green, int blue) {
        float r = (float) red / 255.0f;
        float g = (float) green / 255.0f;
        float b = (float) blue / 255.0f;
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float d = max - min;
        float l = (max + min) / 2.0f;
        float h;
        float s;
        if (max == min) {
            h = s = 0.0f;
        } else {
            if (max == r) {
                h = 60.0f * (((g - b) / d) % 6.0f);
                if (h < 0) {
                    h += 360.0f;
                }
            } else if (max == g) {
                h = 60.0f * (((b - r) / d) + 2.0f);
            } else {
                h = 60.0f * (((r - g) / d) + 4.0f);
            }
            s = l < 0.5f ? d / (max + min) : d / (2.0f - max - min);
        }
        return new float[]{h, s, l};
    }

    /**
     * A specific color to a hex color code string.(e.g #999999)
     *
     * @param color a Color
     * @return hex color code
     */
    public static String hexValueString(Color color) {
        return String.format(HEX_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Convert a specific color to a css rgb color int value string. (e.g.
     * rgb(0, 0, 0))
     *
     * @param color a Color
     * @return a rgb color value string
     */
    public static String RGBIntValueString(Color color) {
        return String.format(RGB_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Convert a specific color to a css rgb color percent value string. (e.g.
     * rgb(50%, 10%, 0%))
     *
     * @param color a Color
     * @return a rgb color value string
     */
    public static String RGBPercentValueString(Color color) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        String r = percentFormat.format((double) color.getRed() / 255.0d);
        String g = percentFormat.format((double) color.getGreen() / 255.0d);
        String b = percentFormat.format((double) color.getBlue() / 255.0d);
        return String.format(RGB_VALUE_FORMAT, r, g, b);
    }

    /**
     * Convert a specific color to a css rgba color int value string. (e.g.
     * rgba(0, 0, 0, 0))
     *
     * @param color a Color
     * @return a rgba color value string
     */
    public static String RGBAIntValueString(Color color) {
        String a = alphaValueString(color.getAlpha());
        return String.format(RGBA_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue(), a);
    }

    /**
     * Convert a specific color to a css rgba color percent value string. (e.g.
     * rgba(50%, 10%, 0%, 0.5))
     *
     * @param color a Color
     * @return a rgba color value string
     */
    public static String RGBAPercentValueString(Color color) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        String r = percentFormat.format((double) color.getRed() / 255.0d);
        String g = percentFormat.format((double) color.getGreen() / 255.0d);
        String b = percentFormat.format((double) color.getBlue() / 255.0d);
        String a = alphaValueString(color.getAlpha());
        return String.format(RGBA_VALUE_FORMAT, r, g, b, a);
    }

    private static String alphaValueString(int alpha) {
        double a = (double) alpha / 255.0d;
        BigDecimal bd = new BigDecimal(a);
        BigDecimal scaledBd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        a = scaledBd.doubleValue();
        return a == (int) a ? String.format("%d", (int) a) : String.format("%s", a); // NOI18N
    }

    /**
     * Convert a specific color to a css hsl color value string. (e.g. hsl(120,
     * 100%, 50%))
     *
     * @param color a Color
     * @return hsl color value string
     */
    public static String HSLValueString(Color color) {
        float[] hsl = rgbToHsl(color.getRed(), color.getGreen(), color.getBlue());
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        BigDecimal hbd = new BigDecimal(hsl[0]);
        BigDecimal scaled = hbd.setScale(0, BigDecimal.ROUND_HALF_UP);
        String h = scaled.toString();
        String s = percentFormat.format(hsl[1]);
        String l = percentFormat.format(hsl[2]);
        return String.format(HSL_VALUE_FORMAT, h, s, l);
    }

    /**
     * Convert a specific color to a css hsl color value string. (e.g. hsl(120,
     * 100%, 50%))
     *
     * @param color a Color
     * @return
     */
    public static String HSLAValueString(Color color) {
        float[] hsl = rgbToHsl(color.getRed(), color.getGreen(), color.getBlue());
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        BigDecimal hbd = new BigDecimal(hsl[0]);
        BigDecimal scaled = hbd.setScale(0, BigDecimal.ROUND_HALF_UP);
        String h = scaled.toString();
        String s = percentFormat.format(hsl[1]);
        String l = percentFormat.format(hsl[2]);
        String a = alphaValueString(color.getAlpha());
        return String.format(HSLA_VALUE_FORMAT, h, s, l, a);
    }

    /**
     * Convert a specified color to a formatted string for a ColorType. Hex
     * color code is returned as default.
     *
     * @param color a Color
     * @param type HexCssColorType
     * @return formatted string
     */
    public static String toFormattedString(Color color, HexCssColorType type) {
        switch (type) {
            case HEX:
                return hexValueString(color);
            case CSS_INT_RGB:
                return RGBIntValueString(color);
            case CSS_PERCENT_RGB:
                return RGBPercentValueString(color);
            case CSS_INT_RGBA:
                return RGBAIntValueString(color);
            case CSS_PERCENT_RGBA:
                return RGBAPercentValueString(color);
            case CSS_HSL:
                return HSLValueString(color);
            case CSS_HSLA:
                return HSLAValueString(color);
            default:
                return hexValueString(color);
        }
    }
}
