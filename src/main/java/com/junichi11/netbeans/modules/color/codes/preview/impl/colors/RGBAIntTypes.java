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

import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author junichi11
 */
public final class RGBAIntTypes {

    private final IntType r;
    private final IntType g;
    private final IntType b;
    private final IntType a;
    private final IntType rgba;
    private static final IntType DEFAULT_TYPE = IntType.Decimal;
    public static final RGBAIntTypes ALL_DECIMAL = new RGBAIntTypes(IntType.Decimal, IntType.Decimal, IntType.Decimal, IntType.Decimal, IntType.Decimal);
    public static final RGBAIntTypes ALL_HEX = new RGBAIntTypes(IntType.Hex, IntType.Hex, IntType.Hex, IntType.Hex, IntType.Hex);

    private RGBAIntTypes(IntType r, IntType g, IntType b, IntType a, IntType rgba) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rgba = rgba;
    }

    public RGBAIntTypes(IntType r, IntType g, IntType b, IntType a) {
        this(r, g, b, a, null);
    }

    public RGBAIntTypes(IntType r, IntType g, IntType b) {
        this(r, g, b, null, null);

    }

    public RGBAIntTypes(IntType rgba) {
        this(null, null, null, null, rgba);
    }

    @CheckForNull
    public IntType getRed() {
        return r;
    }

    @CheckForNull
    public IntType getGreen() {
        return g;
    }

    @CheckForNull
    public IntType getBlue() {
        return b;
    }

    @CheckForNull
    public IntType getAlpha() {
        return a;
    }

    @CheckForNull
    public IntType getRgba() {
        return rgba;
    }

    @Override
    public String toString() {
        return "RGBAIntTypes{" + "r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + ", rgba=" + rgba + '}'; // NOI18N
    }

}
