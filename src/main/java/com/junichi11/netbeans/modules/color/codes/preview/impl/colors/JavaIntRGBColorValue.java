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
 * Represent new Color(int rgb).
 *
 * @author junichi11
 */
public class JavaIntRGBColorValue extends AbstractColorValue {

    private final int rgb;
    private final RGBAIntTypes rgbaIntTypes;

    public JavaIntRGBColorValue(String value, OffsetRange offsetRange, int line, Color color, RGBAIntTypes rgbaIntTypes) {
        super(value, offsetRange, line);
        this.rgb = color.getRGB();
        this.rgbaIntTypes = rgbaIntTypes;
    }

    @Override
    public Color getColor() {
        return new Color(rgb);
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public ColorCodeFormatter getFormatter() {
        return new JavaColorCodeFormatter(JavaColorType.JAVA_INT_RGB, rgbaIntTypes);
    }

}
