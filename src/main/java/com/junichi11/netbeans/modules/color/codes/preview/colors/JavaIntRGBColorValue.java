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
package com.junichi11.netbeans.modules.color.codes.preview.colors;

import com.junichi11.netbeans.modules.color.codes.preview.colors.impl.JavaColorCodesProvider.StandardColor;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.AbstractColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.utils.JavaColorType;
import java.awt.Color;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author arsi
 */
public class JavaIntRGBColorValue extends AbstractColorValue {

    private final int r;
    private final int g;
    private final int b;

    public JavaIntRGBColorValue(String value, int startOffset, int endOffset, int line, Color color) {
        super(value, startOffset, endOffset, line);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }

    @Override
    public Color getColor() {
        return new Color(r, g, b);
    }

    public JavaColorType getType() {
        return JavaColorType.JAVA_INT_RGB;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public ColorCodeFormatter getFormatter() {
        return new JavaColorCodeFormatter();
    }

    private static final class JavaColorCodeFormatter implements ColorCodeFormatter {

        private static final String RGB_VALUE_FORMAT = "Color(%s, %s, %s)"; // NOI18N

        @Override
        public String format(Color color) {
            String standardJavaColor = getStandardJavaColor(color);
            if (standardJavaColor != null) {
                return standardJavaColor;
            }
            return "new " + asRGBIntColorValue(color); // NOI18N
        }

        private static String asRGBIntColorValue(Color color) {
            return String.format(RGB_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
        }

        @CheckForNull
        private static String getStandardJavaColor(Color selectedColor) {
            StandardColor.values();
            for (StandardColor value : StandardColor.values()) {
                if (value.getColor().equals(selectedColor)) {
                    return value.asMethod();
                }
            }
            return null;
        }

    }
}
