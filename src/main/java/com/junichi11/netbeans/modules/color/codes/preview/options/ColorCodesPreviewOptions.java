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
package com.junichi11.netbeans.modules.color.codes.preview.options;

import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author junichi11
 */
public final class ColorCodesPreviewOptions {

    private static final String DEFAULT_MIME_TYPE_REGEX = "^text/(x-)?(css|less|sass|scss)$"; // NOI18N
    public static final String MIME_TYPE_REGEX = "color.codes.preview.mimetype.regex"; // NOI18N
    public static final String NAMED_COLORS = "color.codes.preview.color.types.named"; // NOI18N
    private static final ColorCodesPreviewOptions INSTANCE = new ColorCodesPreviewOptions();

    private ColorCodesPreviewOptions() {
    }

    public static ColorCodesPreviewOptions getInstance() {
        return INSTANCE;
    }

    public String getMimeTypeRegex() {
        return getPreferences().get(MIME_TYPE_REGEX, DEFAULT_MIME_TYPE_REGEX);
    }

    public void setMimeTypeRegex(String regex) {
        getPreferences().put(MIME_TYPE_REGEX, regex);
    }

    public boolean useNamedColors() {
        return getPreferences().getBoolean(NAMED_COLORS, false);
    }

    public void setNamedColors(boolean use) {
        getPreferences().putBoolean(NAMED_COLORS, use);
    }

    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        getPreferences().addPreferenceChangeListener(listener);
    }

    public void removePreferenceChangeListener(PreferenceChangeListener listener) {
        getPreferences().removePreferenceChangeListener(listener);
    }

    private Preferences getPreferences() {
        return NbPreferences.forModule(ColorCodesPreviewOptions.class);
    }
}
