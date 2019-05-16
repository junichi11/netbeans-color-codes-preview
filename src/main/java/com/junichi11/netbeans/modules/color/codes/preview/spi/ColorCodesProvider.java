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
package com.junichi11.netbeans.modules.color.codes.preview.spi;

import java.util.List;
import java.util.Map;
import javax.swing.text.Document;

/**
 * @ServiceProvider add Language implementation to netbeans-color-codes-preview
 * plugin
 * @author junichi11, arsi
 * @since 0.10.0
 */
public interface ColorCodesProvider {

    /**
     * Get the id for the specific provider.
     *
     * @return the id for the specific provider
     */
    public String getId();

    /**
     * Get display name.
     *
     * @return display name
     */
    public String getDisplayName();

    /**
     * Get description.
     *
     * @return description
     */
    public String getDescription();

    /**
     * Is this provider enabled.
     *
     * @since 0.10.0
     * @param document
     * @return {@code true} if enabled, otherwise {@code false}
     */
    public boolean isProviderEnabled(Document document);

    /**
     * Parse the line then return color values.
     *
     * @since 0.10.0
     * @param document
     * @param line string to parse
     * @param lineNumber lineNumber to store to ColorValue
     * @param variableColorValues save colors of variables to this map
     * @return color values
     */
    public List<ColorValue> getColorValues(Document document, String line, int lineNumber, Map<String, List<ColorValue>> variableColorValues);

    /**
     * Get start position for parsing lines.
     *
     * @since 0.10.0
     * @param document
     * @param currentIndex
     * @return
     */
    public int getStartIndex(Document document, int currentIndex);

    /**
     * Get the panel for Options.
     *
     * @since 0.11.1
     * @return the panel for Options
     */
    public ColorCodesPreviewOptionsPanel getOptionsPanel();
}
