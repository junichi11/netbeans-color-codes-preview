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
package com.junichi11.netbeans.modules.color.codes.preview.utils;

import java.util.regex.Pattern;

/**
 *
 * @author junichi11
 */
public enum HexCssColorType {
    HEX("#(?<codenumber>[0-9a-fA-F]{6,}|[0-9a-fA-F]{3,})"), // NOI18N
    CSS_INT_RGB(String.format(HexCssColorType.CSS_RGB_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT)),
    CSS_PERCENT_RGB(String.format(HexCssColorType.CSS_RGB_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT)),
    CSS_INT_RGBA(String.format(HexCssColorType.CSS_RGBA_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT, HexCssColorType.INT_RGB_VALUE_FORMAT, HexCssColorType.ALPHA_VALUE_FORMAT)),
    CSS_PERCENT_RGBA(String.format(HexCssColorType.CSS_RGBA_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.ALPHA_VALUE_FORMAT)),
    CSS_HSL(String.format(HexCssColorType.CSS_HSL_FORMAT, HexCssColorType.HUE_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT)),
    CSS_HSLA(String.format(HexCssColorType.CSS_HSLA_FORMAT, HexCssColorType.HUE_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.PERCENT_VALUE_FORMAT, HexCssColorType.ALPHA_VALUE_FORMAT)),
    NAMED_COLORS(HexCssColorType.NAMED_COLORS_REGEX);
    static final String PERCENT_VALUE_FORMAT = "(100|[1-9]?[0-9])%"; // NOI18N
    // To prevent unexpected matches, check suffix and prefix
    static final String NAMED_COLORS_REGEX = "[ :,\"](" // NOI18N
            + "indianred|lightcoral|salmon|darksalmon|lightsalmon|crimson|red|firebrick|darkred|" // NOI18N
            + "pink|lightpink|hotpink|deeppink|mediumvioletred|palevioletred|" // NOI18N
            + "palevioletred|coral|tomato|orangered|darkorange|orange|" // NOI18N
            + "gold|lightyellow|lemonchiffon|lightgoldenrodyellow|papayawhip|moccasin|peachpuff|palegoldenrod|khaki|darkkhaki|" // NOI18N
            + "lavender|thistle|plum|violet|orchid|fuchsia|magenta|mediumorchid|mediumpurple|rebeccapurple|blueviolet|darkviolet|darkorchid|darkmagenta|purple|indigo|slateblue|darkslateblue|mediumslateblue|" // NOI18N
            + "greenyellow|chartreuse|lawngreen|lime|limegreen|palegreen|lightgreen|mediumspringgreen|springgreen|mediumseagreen|seagreen|forestgreen|green|darkgreen|yellowgreen|olivedrab|olive|darkolivegreen|mediumaquamarine|darkseagreen|lightseagreen|darkcyan|teal|yellow|" // NOI18N
            + "aqua|cyan|lightcyan|paleturquoise|aquamarine|turquoise|mediumturquoise|darkturquoise|cadetblue|steelblue|lightsteelblue|powderblue|lightblue|skyblue|lightskyblue|deepskyblue|dodgerblue|cornflowerblue|mediumslateblue|royalblue|blue|mediumblue|darkblue|navy|midnightblue|" // NOI18N
            + "cornsilk|blanchedalmond|bisque|navajowhite|wheat|burlywood|tan|rosybrown|sandybrown|goldenrod|darkgoldenrod|peru|chocolate|saddlebrown|sienna|brown|maroon|" // NOI18N
            + "white|snow|honeydew|mintcream|azure|aliceblue|ghostwhite|whitesmoke|seashell|beige|oldlace|floralwhite|ivory|antiquewhite|linen|lavenderblush|mistyrose|" // NOI18N
            + "gainsboro|lightgray|silver|darkgray|gray|dimgray|lightslategray|slategray|darkslategray|black" // NOI18N
            + ")[ ;,\"]"; // NOI18N
    static final String INT_RGB_VALUE_FORMAT = "25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9]"; // NOI18N
    static final String CSS_RGB_FORMAT = "(?<cssrgb>rgb\\((?<codenumber>(?<r>%s) *, *(?<g>%s) *, *(?<b>%s))\\))"; // NOI18N
    static final String CSS_RGBA_FORMAT = "(?<cssrgba>rgba\\((?<codenumber>(?<r>%s) *, *(?<g>%s) *, *(?<b>%s) *, *(?<a>%s))\\))"; // NOI18N
    static final String HUE_VALUE_FORMAT = "(360|3[0-5][0-9]|[1-2][0-9][0-9]|[1-9]?[0-9])"; // NOI18N
    static final String CSS_HSLA_FORMAT = "(?<csshsla>hsla\\((?<codenumber>(?<h>%s) *, *(?<s>%s) *, *(?<l>%s) *, *(?<a>%s))\\))"; // NOI18N
    static final String ALPHA_VALUE_FORMAT = "0|1|0?\\.[1-9]{1,2}|0?\\.0?[1-9]"; // NOI18N
    static final String CSS_HSL_FORMAT = "(?<csshsl>hsl\\((?<codenumber>(?<h>%s) *, *(?<s>%s) *, *(?<l>%s))\\))"; // NOI18N
    private final Pattern pattern;

    private HexCssColorType(String regex) {
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Pattern getPattern() {
        return pattern;
    }

}
