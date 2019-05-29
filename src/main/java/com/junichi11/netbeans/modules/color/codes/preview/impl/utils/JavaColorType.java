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

import java.util.regex.Pattern;

/**
 *
 * @author junichi11
 */
public enum JavaColorType implements ColorType {
    JAVA_STANDARD_COLOR(String.format(JavaColorType.JAVA_STANDARD_COLOR_FORMAT, JavaColorType.JAVA_STANDARD_COLORS_REGEX)),
    JAVA_INT_RGB(String.format(JavaColorType.JAVA_RGB_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT)),
    JAVA_INT_RGBA(String.format(JavaColorType.JAVA_RGBA_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT, ColorType.BYTE_RGB_VALUE_FORMAT)),
    JAVA_INT(String.format(JavaColorType.JAVA_INT_FORMAT, ColorType.INT_RGB_VALUE_FORMAT)),
    JAVA_INTA(String.format(JavaColorType.JAVA_INTA_FORMAT, ColorType.INT_RGB_VALUE_FORMAT, ColorType.BOOL_VALUE_FORMAT)),
    JAVA_HEXINT(String.format(JavaColorType.JAVA_HEXINTRGB_FORMAT, ColorType.HEX_RGB_VALUE_FORMAT)),
    JAVA_HEXINTA(String.format(JavaColorType.JAVA_HEXINTRGBA_FORMAT, ColorType.HEX_RGB_VALUE_FORMAT, ColorType.BOOL_VALUE_FORMAT)),
    JAVA_FLOAT_RGB(String.format(JavaColorType.JAVA_FRGB_FORMAT, ColorType.FLOAT_VALUE_FORMAT, ColorType.FLOAT_VALUE_FORMAT, ColorType.FLOAT_VALUE_FORMAT)),
    JAVA_FLOAT_RGBA(String.format(JavaColorType.JAVA_FRGBA_FORMAT, ColorType.FLOAT_VALUE_FORMAT, ColorType.FLOAT_VALUE_FORMAT, ColorType.FLOAT_VALUE_FORMAT, ColorType.FLOAT_VALUE_FORMAT)),
    DECODE(JavaColorType.JAVA_COLOR_DECODE_REGEX);

    private static final String JAVA_STANDARD_COLORS_REGEX = "black|BLACK|blue|BLUE|cyan|CYAN|darkGray|DARK_GRAY|gray|GRAY|green|GREEN|lightGray|LIGHT_GRAY|magenta|MAGENTA|orange|ORANGE|pink|PINK|red|RED|white|WHITE|yellow|YELLOW"; // NOI18N
    private static final String JAVA_STANDARD_COLOR_FORMAT = "(?<javastandard>Color\\.(?<colorname>%s))"; // NOI18N
    private static final String JAVA_RGB_FORMAT = "(?<javargb>new Color\\((?<codenumber> *(?<r>%s) *, *(?<g>%s) *, *(?<b>%s) *)\\))"; // NOI18N
    private static final String JAVA_RGBA_FORMAT = "(?<javargba>new Color\\((?<codenumber> *(?<r>%s) *, *(?<g>%s) *, *(?<b>%s) *, *(?<a>%s) *)\\))"; // NOI18N
    private static final String JAVA_COLOR_DECODE_REGEX = "(?<javadecode>Color\\.decode\\(\"(#(?<codenumber>[0-9a-fA-F]{6,}|[0-9a-fA-F]{3,})\")\\))"; // NOI18N
    private static final String JAVA_INT_FORMAT = "(?<javaintrgb>new Color\\((?<codenumber> *(?<rgb>%s)*)\\))"; // NOI18N
    private static final String JAVA_INTA_FORMAT = "(?<javaintrgba>new Color\\((?<codenumber> *(?<rgba>%s)*, *(?<bool>%s) *)\\))"; // NOI18N
    private static final String JAVA_HEXINTRGB_FORMAT = "(?<javahexrgb>new Color\\((?<codenumber> *0x(?<rgb>%s)*)\\))"; // NOI18N
    private static final String JAVA_HEXINTRGBA_FORMAT = "(?<javahexrgba>new Color\\((?<codenumber> *0x(?<rgba>%s)*, *(?<bool>%s) *)\\))"; // NOI18N
    private static final String JAVA_FRGB_FORMAT = "(?<javafrgb>new Color\\((?<codenumber> *(?<r>%s)f *, *(?<g>%s)f *, *(?<b>%s)f *)\\))"; // NOI18N
    private static final String JAVA_FRGBA_FORMAT = "(?<javafrgba>new Color\\((?<codenumber> *(?<r>%s)f *, *(?<g>%s)f *, *(?<b>%s)f *, *(?<a>%s)f *)\\))"; // NOI18N

    private final Pattern pattern;

    private JavaColorType(String regex) {
        if (regex.equals(String.format(JavaColorType.JAVA_STANDARD_COLOR_FORMAT, JavaColorType.JAVA_STANDARD_COLORS_REGEX))) {
            this.pattern = Pattern.compile(regex);
        } else {
            this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
