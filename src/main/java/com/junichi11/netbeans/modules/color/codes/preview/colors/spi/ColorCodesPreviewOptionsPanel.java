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
package com.junichi11.netbeans.modules.color.codes.preview.colors.spi;

import javax.swing.JPanel;

/**
 * ColorCodesPreviewOptionsPanel.
 *
 * @since 0.11.1
 * @author junichi11
 */
public abstract class ColorCodesPreviewOptionsPanel extends JPanel {

    /**
     * Empty panel.
     *
     * @since 0.11.1
     */
    public static ColorCodesPreviewOptionsPanel EMPTY_PANEL = new ColorCodesPreviewOptionsPanel() {
        private static final long serialVersionUID = 7805207170547644887L;

        @Override
        public void load() {
        }

        @Override
        public void store() {
        }

    };
    private static final long serialVersionUID = 679165987508129100L;

    /**
     * Load settings.
     *
     * @since 0.11.1
     */
    public abstract void load();

    /**
     * Store settings.
     *
     * @since 0.11.1
     */
    public abstract void store();

}
