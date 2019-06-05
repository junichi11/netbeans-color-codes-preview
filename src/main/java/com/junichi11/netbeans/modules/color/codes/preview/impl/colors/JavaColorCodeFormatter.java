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

    private static final String DECODE_HEX_VALUE_FORMAT = "Color.decode(\"#%02x%02x%02x\")"; // NOI18N

    private final JavaColorType type;
    private final RGBAIntTypes rgbaIntTypes;

    public JavaColorCodeFormatter(JavaColorType type, RGBAIntTypes rgbaIntTypes) {
        this.type = type;
        this.rgbaIntTypes = rgbaIntTypes;
    }

    public JavaColorCodeFormatter(JavaColorType type) {
        this(type, RGBAIntTypes.ALL_DECIMAL);
    }

    @Override
    public String format(Color color) {
        switch (type) {
            case JAVA_STANDARD_COLOR:
                String standardJavaColor = getStandardJavaColor(color);
                if (standardJavaColor != null) {
                    return standardJavaColor;
                }
                break;
            case DECODE:
                return asDecodeHexValue(color);
            case JAVA_INT_RGB:
                return asRGBIntColorValue(color);
            case JAVA_INT_RGBA:
                return asRGBAIntColorValue(color);
            default:
                break;
        }
        return "new " + asRGBsIntColorValue(color); // NOI18N
    }

    private String asRGBAIntColorValue(Color color) {
        if (IntType.Decimal == rgbaIntTypes.getRgba()) {
            return String.format(buildColorRGBAFormat(rgbaIntTypes, true), color.getRGB(), "true"); // NOI18N
        }
        return String.format(buildColorRGBAFormat(rgbaIntTypes, true), color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue(), "true"); // NOI18N
    }

    private String asRGBIntColorValue(Color color) {
        if (IntType.Decimal == rgbaIntTypes.getRgba()) {
            String hexString = String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()); // NOI18N
            return String.format(buildColorRGBAFormat(rgbaIntTypes, false), Integer.parseInt(hexString, IntType.Hex.getRadix()));
        }
        return String.format(buildColorRGBAFormat(rgbaIntTypes, false), color.getRed(), color.getGreen(), color.getBlue());
    }

    private String asRGBsIntColorValue(Color color) {
        assert rgbaIntTypes != null;
        if (type == JavaColorType.JAVA_INT_R_G_B_A || color.getAlpha() != 255) {
            return String.format(buildColorRGBAsFormat(rgbaIntTypes, true), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }
        return String.format(buildColorRGBAsFormat(rgbaIntTypes, false), color.getRed(), color.getGreen(), color.getBlue());
    }

    private String asDecodeHexValue(Color color) {
        return String.format(DECODE_HEX_VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue());
    }

    private static String buildColorRGBAFormat(RGBAIntTypes rgbaIntTypes, boolean hasAlpha) {
        StringBuilder sb = new StringBuilder();
        sb.append("new Color("); // NOI18N
        if (IntType.Hex == rgbaIntTypes.getRgba()) {
            sb.append("0x"); // NOI18N
            int rgbaCount = hasAlpha ? 4 : 3; // 0xffffffff or 0xffffff
            for (int i = 0; i < rgbaCount; i++) {
                sb.append(IntType.Hex.getFormatSpecifier());
            }
        } else {
            sb.append(rgbaIntTypes.getRgba().getFormatSpecifier());
        }

        if (hasAlpha) {
            sb.append(", %s"); // NOI18N
        }
        sb.append(")"); // NOI18N
        return sb.toString();
    }

    private static String buildColorRGBAsFormat(RGBAIntTypes rgbaIntTypes, boolean hasAlpha) {
        StringBuilder sb = new StringBuilder();
        sb.append("Color("); // NOI18N
        if (IntType.Hex == rgbaIntTypes.getRed()) {
            sb.append("0x"); // NOI18N
        }
        sb.append(rgbaIntTypes.getRed().getFormatSpecifier());
        sb.append(", "); // NOI18N
        if (IntType.Hex == rgbaIntTypes.getGreen()) {
            sb.append("0x"); // NOI18N
        }
        sb.append(rgbaIntTypes.getGreen().getFormatSpecifier());
        sb.append(", "); // NOI18N
        if (IntType.Hex == rgbaIntTypes.getBlue()) {
            sb.append("0x"); // NOI18N
        }
        sb.append(rgbaIntTypes.getBlue().getFormatSpecifier());
        if (hasAlpha) {
            sb.append(", "); // NOI18N
            IntType alpha = rgbaIntTypes.getAlpha();
            if (alpha == null) {
                alpha = rgbaIntTypes.getRed();
            }
            if (IntType.Hex == alpha) {
                sb.append("0x"); // NOI18N
            }
            sb.append(alpha.getFormatSpecifier());
        }
        sb.append(")"); // NOI18N
        return sb.toString();
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
