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
package com.junichi11.netbeans.modules.color.codes.preview.impl.colors;

import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.JavaColorType;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import java.awt.Color;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author junichi11
 */
public final class JavaColorCodeFormatter implements ColorCodeFormatter {

    private static final String RGB_VALUE_FORMAT = "Color(%s, %s, %s)"; // NOI18N
    private static final String RGBA_VALUE_FORMAT = "Color(%s, %s, %s, %s)"; // NOI18N
    private static final String DECODE_HEX_VALUE_FORMAT = "Color.decode(\"#%02x%02x%02x\")"; // NOI18N

    private final JavaColorType type;

    public JavaColorCodeFormatter(JavaColorType type) {
        this.type = type;
    }

    @Override
    public String format(Color color) {
        if (type == JavaColorType.JAVA_STANDARD_COLOR) {
            String standardJavaColor = getStandardJavaColor(color);
            if (standardJavaColor != null) {
                return standardJavaColor;
            }
        } else if (type == JavaColorType.DECODE) {
            return asDecodeHexValue(color);
        }
        return "new " + asRGBIntColorValue(color); // NOI18N
    }

    private String asRGBIntColorValue(Color color) {
        if (type == JavaColorType.JAVA_INT_RGBA || color.getAlpha() != 255) {
            return String.format(RGBA_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }
        return String.format(RGB_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    private String asDecodeHexValue(Color color) {
        return String.format(DECODE_HEX_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    @CheckForNull
    private String getStandardJavaColor(Color selectedColor) {
        for (JavaStandardColor value : JavaStandardColor.values()) {
            if (value.getColor().equals(selectedColor)) {
                return value.asMethod();
            }
        }
        return null;
    }

}
