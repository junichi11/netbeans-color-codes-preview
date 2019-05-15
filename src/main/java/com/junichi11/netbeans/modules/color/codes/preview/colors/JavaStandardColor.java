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

import java.awt.Color;

/**
 *
 * @author junichi11
 */
public enum JavaStandardColor {
    // upper
    BLACK("BLACK", Color.BLACK), // NOI18N
    BLUE("BLUE", Color.BLUE), // NOI18N
    CYAN("CYAN", Color.CYAN), // NOI18N
    DARK_GRAY("DARK_GRAY", Color.DARK_GRAY), // NOI18N
    GRAY("GRAY", Color.GRAY), // NOI18N
    GREEN("GREEN", Color.GREEN), // NOI18N
    LIGHT_GRAY("LIGHT_GRAY", Color.LIGHT_GRAY), // NOI18N
    MAGENTA("MAGENTA", Color.MAGENTA), // NOI18N
    ORANGE("ORANGE", Color.ORANGE), // NOI18N
    PINK("PINK", Color.PINK), // NOI18N
    RED("RED", Color.RED), // NOI18N
    WHITE("WHITE", Color.WHITE), // NOI18N
    YELLOW("YELLOW", Color.YELLOW), // NOI18N
    // lower
    black("black", Color.black), // NOI18N
    blue("blue", Color.blue), // NOI18N
    cyan("cyan", Color.cyan), // NOI18N
    darkGray("darkGray", Color.darkGray), // NOI18N
    gray("gray", Color.gray), // NOI18N
    green("green", Color.green), // NOI18N
    lightGray("lightGray", Color.lightGray), // NOI18N
    magenta("magenta", Color.magenta), // NOI18N
    orange("orange", Color.orange), // NOI18N
    pink("pink", Color.pink), // NOI18N
    red("red", Color.red), // NOI18N
    white("white", Color.white), // NOI18N
    yellow("yellow", Color.yellow);
    private final String colorName;
    private final Color color;

    public String getColorName() {
        return colorName;
    }

    public Color getColor() {
        return color;
    }

    public String asMethod() {
        return "Color." + colorName;
    }

    private JavaStandardColor(String colorName, Color color) {
        this.colorName = colorName;
        this.color = color;
    }

}
