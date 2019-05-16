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

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

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
        private static final long serialVersionUID = 3772795247048065205L;

        @Override
        public void load() {
        }

        @Override
        public void store() {
        }

        @Override
        public boolean valid() {
            return true;
        }

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public void addChangeListener(ChangeListener listener) {
        }

        @Override
        public void removeChangeListener(ChangeListener listener) {
        }

    };
    private static final long serialVersionUID = -6323395294472234402L;

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

    /**
     * Check whether form is consistent and complete.
     *
     * @since 0.11.1
     * @return {@code true} if the form is consistend and complete, otherwise
     * {@code false}
     */
    public abstract boolean valid();

    /**
     * Get the error message.
     *
     * @since 0.11.1
     * @return the error message
     */
    public abstract String getErrorMessage();

    /**
     * Add ChangeListener.
     *
     * @since 0.11.1
     * @param listener the listener
     */
    public abstract void addChangeListener(ChangeListener listener);

    /**
     * Remove ChangeListener.
     *
     * @since 0.11.1
     * @param listener the listener
     */
    public abstract void removeChangeListener(ChangeListener listener);
}
