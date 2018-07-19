/*
 * Copyright 2018 junichi11.
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

import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils;
import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils.ColorType;
import java.awt.Color;
import org.netbeans.api.annotations.common.NonNull;

/**
 *
 * @author junichi11
 */
public class ColorValueImpl implements ColorValue {

    private final int line;
    private final int startOffset;
    private final int endOffset;
    private final String value;

    public ColorValueImpl(@NonNull String value, int startOffset, int endOffset, int line) {
        this.value = value;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.line = line;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getStartOffset() {
        return startOffset;
    }

    @Override
    public int getEndOffset() {
        return endOffset;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Color getColor() {
        return ColorsUtils.decode(value);
    }

    @Override
    public ColorType getType() {
        return ColorType.HEX;
    }

}
