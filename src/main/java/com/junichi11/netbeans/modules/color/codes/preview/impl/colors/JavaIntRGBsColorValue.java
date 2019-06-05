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

import com.junichi11.netbeans.modules.color.codes.preview.api.OffsetRange;
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.JavaColorType;
import com.junichi11.netbeans.modules.color.codes.preview.spi.AbstractColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import java.awt.Color;

/**
 * Represent new Color(int r, int g, int b).
 *
 * @author arsi
 */
public class JavaIntRGBsColorValue extends AbstractColorValue {

    private final int r;
    private final int g;
    private final int b;
    private final RGBAIntTypes rgbaIntTypes;

    public JavaIntRGBsColorValue(String value, OffsetRange offsetRange, int line, Color color, RGBAIntTypes rgbaIntTypes) {
        super(value, offsetRange, line);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.rgbaIntTypes = rgbaIntTypes;
    }

    @Override
    public Color getColor() {
        return new Color(r, g, b);
    }

    public JavaColorType getType() {
        return JavaColorType.JAVA_INT_R_G_B;
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public ColorCodeFormatter getFormatter() {
        return new JavaColorCodeFormatter(getType(), getRGBAIntTypes());
    }

    public RGBAIntTypes getRGBAIntTypes() {
        return rgbaIntTypes;
    }

    @Override
    public String toString() {
        return super.toString() + " JavaIntRGBsColorValue{" + "r=" + r + ", g=" + g + ", b=" + b + ", rgbaIntTypes=" + rgbaIntTypes + '}'; // NOI18N
    }

}
