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
package com.junichi11.netbeans.modules.color.codes.preview.colors.model;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.text.Document;

/**
 * @ServiceProvider add Language implementation to netbeans-color-codes-preview
 * plugin
 * @author arsi
 */
public interface ColorCodesProvider {

    /**
     * is Mime Type supported by this provider
     *
     * @param mimeType Mime Type
     * @return true if supported
     */
    public boolean isMimeTypeSupported(String mimeType);

    /**
     * is this provider enabled
     *
     * @return true if enabled
     */
    public boolean isProviderEnabled();

    /**
     * can provider resolve variables for the Mime Type
     *
     * @param mimeType Mime Type
     * @return true if supported
     */
    public boolean isResolveVariablesSupported(String mimeType);

    /**
     * parse variables
     *
     * @param document current document, e.g.
     * CompletionUtil.getPrimaryFile(Document)
     * @param mimeType Mime Type
     * @param line string to parse
     * @param variables lineNumber to store to ColorValue
     * @param colorValues List to add created ColorValue. Before adding new
     * ColorValue, check if list containt ColorValue on same line, startoffset
     * and endoffset
     */
    public void checkVariables(Document document, String mimeType, String line, Map<String, List<ColorValue>> variables, List<ColorValue> colorValues);

    /**
     * Parse line and add parsed colors to List colorValues
     *
     * @param document current document, e.g.
     * CompletionUtil.getPrimaryFile(Document)
     * @param mimeType Mime Type
     * @param line string to parse
     * @param lineNumber lineNumber to store to ColorValue
     * @param colorValues List to add created ColorValue. Before adding new
     * ColorValue, check if list containt ColorValue on same line, startoffset
     * and endoffset
     */
    public void addAllColorValues(Document document, String mimeType, String line, int lineNumber, List<ColorValue> colorValues);

    /**
     * Return text for new color
     *
     * @param newColor Color selected by user
     * @param originalColor original ColorValue
     * @return text for new color
     */
    public String toFormattedString(Color newColor, ColorValue originalColor);

}
