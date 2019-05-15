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
package com.junichi11.netbeans.modules.color.codes.preview.options;

import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;
import org.openide.util.WeakListeners;

/**
 *
 * @author junichi11
 */
public final class ColorCodesPreviewOptions {

    public static final String ENABLED_PREFIX = "color.codes.preview.enabled."; // NOI18N
    public static final String HEX_CSS_MIME_TYPE_REGEX = "color.codes.preview.mimetype.regex"; // NOI18N
    private static final String HEX_CSS_DEFAULT_MIME_TYPE_REGEX = "^text/(x-)?(css|less|sass|scss)$"; // NOI18N
    private static final String HEX_CSS_NAMED_COLORS = "color.codes.preview.color.types.named"; // NOI18N
    private static final String HEX_CSS_RESOLVE_CSS_VARIABLES = "color.codes.preview.resolve.css.variables"; // NOI18N
    private static final String ENABLED = ENABLED_PREFIX + "%s"; // NOI18N

    private static final ColorCodesPreviewOptions INSTANCE = new ColorCodesPreviewOptions();

    private ColorCodesPreviewOptions() {
    }

    public static ColorCodesPreviewOptions getInstance() {
        return INSTANCE;
    }

    public String getMimeTypeRegex() {
        return getPreferences().get(HEX_CSS_MIME_TYPE_REGEX, HEX_CSS_DEFAULT_MIME_TYPE_REGEX);
    }

    public void setMimeTypeRegex(String regex) {
        getPreferences().put(HEX_CSS_MIME_TYPE_REGEX, regex);
    }

    public boolean useNamedColors() {
        return getPreferences().getBoolean(HEX_CSS_NAMED_COLORS, false);
    }

    public void setNamedColors(boolean use) {
        getPreferences().putBoolean(HEX_CSS_NAMED_COLORS, use);
    }

    public boolean resolveCssVariables() {
        return getPreferences().getBoolean(HEX_CSS_RESOLVE_CSS_VARIABLES, false);
    }

    public void setResolveCssVariables(boolean resolve) {
        getPreferences().putBoolean(HEX_CSS_RESOLVE_CSS_VARIABLES, resolve);
    }

    public boolean isEnabled(String providerId) {
        return getPreferences().getBoolean(String.format(ENABLED, providerId), true);
    }

    public void setEnabled(String providerId, boolean enabled) {
        getPreferences().putBoolean(String.format(ENABLED, providerId), enabled);
    }

    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        Preferences preferences = getPreferences();
        preferences.addPreferenceChangeListener(WeakListeners.create(PreferenceChangeListener.class, listener, preferences));
    }

    public void removePreferenceChangeListener(PreferenceChangeListener listener) {
        getPreferences().removePreferenceChangeListener(listener);
    }

    private Preferences getPreferences() {
        return NbPreferences.forModule(ColorCodesPreviewOptions.class);
    }
}
