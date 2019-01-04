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

import com.junichi11.netbeans.modules.color.codes.preview.colors.model.AbstractColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.utils.JavaColorType;
import java.awt.Color;
import org.netbeans.api.annotations.common.NonNull;

/**
 *
 * @author arsi
 */
public class JavaIntRGBColorValue extends AbstractColorValue {

    private int r;
    private int g;
    private int b;

    public JavaIntRGBColorValue(@NonNull ColorCodesProvider colorCodesProvider, String value, int startOffset, int endOffset, int line, int r, int g, int b) {
        super(colorCodesProvider, value, startOffset, endOffset, line);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public Color getColor() {
        return new Color(r, g, b);
    }

    @Override
    public JavaColorType getType() {
        return JavaColorType.JAVA_INT_RGB;
    }

}
