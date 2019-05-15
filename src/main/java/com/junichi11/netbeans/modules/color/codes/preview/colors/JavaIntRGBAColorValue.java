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
package com.junichi11.netbeans.modules.color.codes.preview.colors;

import com.junichi11.netbeans.modules.color.codes.preview.colors.api.OffsetRange;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.AbstractColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.utils.JavaColorType;
import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author junichi11
 */
public class JavaIntRGBAColorValue extends AbstractColorValue {

    private final int r;
    private final int g;
    private final int b;
    private final int a;
    private static final String GTK_LOOK_AND_FEEL_NAME = "GTK look and feel"; // NOI18N
    private static final String LOOK_AND_FEEL_NAME = UIManager.getLookAndFeel().getName();

    public JavaIntRGBAColorValue(String value, OffsetRange offsetRange, int line, Color color) {
        super(value, offsetRange, line);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
    }

    @Override
    public Color getColor() {
        return new Color(r, g, b, a);
    }

    public JavaColorType getType() {
        return JavaColorType.JAVA_INT_RGBA;
    }

    @Override
    public boolean isEditable() {
        // ColorChooser of GTK cannot change transparency
        return !GTK_LOOK_AND_FEEL_NAME.equals(LOOK_AND_FEEL_NAME);
    }

    @Override
    public ColorCodeFormatter getFormatter() {
        return new JavaColorCodeFormatter(getType());
    }

}
