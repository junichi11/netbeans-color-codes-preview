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
    JAVA_FLOAT_R_G_B(String.format(JavaColorType.JAVA_FLOAT_R_G_B_FORMAT,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX
    )),
    JAVA_FLOAT_R_G_B_A(String.format(JavaColorType.JAVA_FLOAT_R_G_B_A_FORMAT,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX,
            ColorType.FLOAT_R_G_B_A_VALUE_REGEX
    )),
    JAVA_INT_R_G_B(String.format(JavaColorType.JAVA_INT_R_G_B_FORMAT,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX
    )),
    JAVA_INT_R_G_B_A(String.format(JavaColorType.JAVA_INT_R_G_B_A_FORMAT,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX,
            ColorType.DECIMAL_INT_R_G_B_VALUE_REGEX, ColorType.HEX_INT_R_G_B_VALUE_REGEX
    )),
    JAVA_INT_RGB(String.format(JavaColorType.JAVA_INT_RGB_FORMAT,
            ColorType.DECIMAL_INT_RGBA_VALUE_REGEX, ColorType.HEX_INT_RGBA_VALUE_REGEX
    )),
    JAVA_INT_RGBA(String.format(JavaColorType.JAVA_INT_RGBA_FORMAT,
            ColorType.DECIMAL_INT_RGBA_VALUE_REGEX, ColorType.HEX_INT_RGBA_VALUE_REGEX,
            ColorType.BOOL_VALUE_REGEX
    )),
    DECODE(JavaColorType.JAVA_COLOR_DECODE_REGEX);

    private static final String JAVA_STANDARD_COLORS_REGEX = "black|BLACK|blue|BLUE|cyan|CYAN|darkGray|DARK_GRAY|gray|GRAY|green|GREEN|lightGray|LIGHT_GRAY|magenta|MAGENTA|orange|ORANGE|pink|PINK|red|RED|white|WHITE|yellow|YELLOW"; // NOI18N
    private static final String JAVA_STANDARD_COLOR_FORMAT = "(?<javastandard>Color\\.(?<colorname>%s))"; // NOI18N
    private static final String JAVA_FLOAT_R_G_B_FORMAT = "(?<javargb>new Color\\((?<codenumber> *(?<r>%s)f *, *(?<g>%s)f *, *(?<b>%s)f *)\\))"; // NOI18N
    private static final String JAVA_FLOAT_R_G_B_A_FORMAT = "(?<javargba>new Color\\((?<codenumber> *(?<r>%s)f *, *(?<g>%s)f *, *(?<b>%s)f *, *(?<a>%s)f *)\\))"; // NOI18N
    private static final String JAVA_INT_R_G_B_FORMAT = "(?<javargb>new Color\\((?<codenumber> *((?<r>%s)|0x(?<hexr>%s)) *, *((?<g>%s)|0x(?<hexg>%s)) *, *((?<b>%s)|0x(?<hexb>%s)) *)\\))"; // NOI18N
    private static final String JAVA_INT_R_G_B_A_FORMAT = "(?<javargba>new Color\\((?<codenumber> *((?<r>%s)|0x(?<hexr>%s)) *, *((?<g>%s)|0x(?<hexg>%s)) *, *((?<b>%s)|0x(?<hexb>%s)) *, *((?<a>%s)|0x(?<hexa>%s)) *)\\))"; // NOI18N
    private static final String JAVA_INT_RGB_FORMAT = "(?<javargb>new Color\\((?<codenumber> *((?<intrgba>%s)|0x(?<hexrgba>%s)) *)\\))"; // NOI18N
    private static final String JAVA_INT_RGBA_FORMAT = "(?<javargba>new Color\\((?<codenumber> *((?<intrgba>%s)|0x(?<hexrgba>%s)) *, * (?<bool>%s) *)\\))"; // NOI18N
    private static final String JAVA_COLOR_DECODE_REGEX = "(?<javadecode>Color\\.decode\\(\"(#(?<codenumber>[0-9a-fA-F]{6,}|[0-9a-fA-F]{3,})\")\\))"; // NOI18N

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
