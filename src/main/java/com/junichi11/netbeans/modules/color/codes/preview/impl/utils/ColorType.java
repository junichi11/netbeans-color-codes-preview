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
public interface ColorType {

    static final String DECIMAL_INT_R_G_B_VALUE_REGEX = "25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9]"; // NOI18N
    static final String DECIMAL_INT_RGBA_VALUE_REGEX = "(\\+|\\-)?[0-9]{1,9}"; // NOI18N
    static final String HEX_INT_R_G_B_VALUE_REGEX = "[0-9a-fA-F]{1,2}"; // NOI18N
    static final String HEX_INT_RGBA_VALUE_REGEX = "[0-9a-fA-F]{6}|[0-9a-fA-F]{8}"; // NOI18N
    static final String BOOL_VALUE_REGEX = "true|false"; // NOI18N

    Pattern getPattern();
}
