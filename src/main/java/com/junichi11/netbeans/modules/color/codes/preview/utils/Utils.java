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
package com.junichi11.netbeans.modules.color.codes.preview.utils;

import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author junichi11
 */
public final class Utils {

    private static final Comparator COLOR_VALUE_COMPARATOR = new ColorValueComparator();

    private Utils() {
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

    /**
     * Sort {@link ColorValue}s.
     *
     * @param colorValues
     */
    public static void sort(List<ColorValue> colorValues) {
        Collections.sort(colorValues, COLOR_VALUE_COMPARATOR);
    }

    /**
     * Compute a brightness value.
     * {@link http://www.w3.org/TR/AERT#color-contrast}
     *
     * @param color Color
     * @return a brightness value
     */
    private static int getBrightness(Color color) {
        return (color.getRed() * 299 + color.getGreen() * 578 + color.getBlue() * 114) / 1000;
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
