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

import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorCodesProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import org.openide.util.Lookup;

final class ColorCodesPreviewPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 4484917962418103588L;

    private final ColorCodesPreviewOptionsPanelController controller;
    private final Map<String, JCheckBox> enabledCheckBoxes = new HashMap<>();

    ColorCodesPreviewPanel(ColorCodesPreviewOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        init();
        // TODO listen to changes in form fields and call controller.changed()
    }

    private void init() {
        for (ColorCodesProvider provider : getProviders()) {
            JCheckBox checkBox = new JCheckBox(provider.getDisplayName());
            checkBox.setToolTipText(provider.getDescription());
            enabledCheckBoxes.put(provider.getId(), checkBox);
            enabledPanel.add(checkBox);
        }
    }

    private Collection<? extends ColorCodesProvider> getProviders() {
        return Lookup.getDefault().lookupAll(ColorCodesProvider.class);
    }

    public String getMimeTypeRegex() {
        return mimeTypeRegexTextField.getText().trim();
    }

    private void setMimeTypeRegex(String regex) {
        mimeTypeRegexTextField.setText(regex);
    }

    public boolean useNamedColors() {
        return namedColorsCheckBox.isSelected();
    }

    private void setNamedColors(boolean use) {
        namedColorsCheckBox.setSelected(use);
    }

    public boolean resolveCssVariables() {
        return resolveCssVariablesCheckBox.isSelected();
    }

    private void setResolveCssVariables(boolean resolve) {
        resolveCssVariablesCheckBox.setSelected(resolve);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileTypesLabel = new javax.swing.JLabel();
        mimeTypeRegexTextField = new javax.swing.JTextField();
        colorTypesLabel = new javax.swing.JLabel();
        namedColorsCheckBox = new javax.swing.JCheckBox();
        resolveCssVariablesCheckBox = new javax.swing.JCheckBox();
        hexCssLabel = new javax.swing.JLabel();
        hexCssSeparator = new javax.swing.JSeparator();
        enabledPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(fileTypesLabel, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.fileTypesLabel.text")); // NOI18N

        mimeTypeRegexTextField.setText(org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.mimeTypeRegexTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(colorTypesLabel, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.colorTypesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(namedColorsCheckBox, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.namedColorsCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(resolveCssVariablesCheckBox, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.resolveCssVariablesCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(hexCssLabel, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.hexCssLabel.text")); // NOI18N

        enabledPanel.setLayout(new javax.swing.BoxLayout(enabledPanel, javax.swing.BoxLayout.LINE_AXIS));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ColorCodesPreviewPanel.class, "ColorCodesPreviewPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(namedColorsCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileTypesLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mimeTypeRegexTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(colorTypesLabel)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resolveCssVariablesCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(enabledPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(hexCssLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hexCssSeparator))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enabledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hexCssLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hexCssSeparator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileTypesLabel)
                    .addComponent(mimeTypeRegexTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resolveCssVariablesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorTypesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namedColorsCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        setMimeTypeRegex(options.getMimeTypeRegex());
        setNamedColors(options.useNamedColors());
        setResolveCssVariables(options.resolveCssVariables());
        enabledCheckBoxes.forEach((id, checkBox) -> {
            checkBox.setSelected(options.isEnabled(id));
        });
    }

    void store() {
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        options.setMimeTypeRegex(getMimeTypeRegex());
        options.setNamedColors(useNamedColors());
        options.setResolveCssVariables(resolveCssVariables());
        enabledCheckBoxes.forEach((id, checkBox) -> {
            options.setEnabled(id, checkBox.isSelected());
        });
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel colorTypesLabel;
    private javax.swing.JPanel enabledPanel;
    private javax.swing.JLabel fileTypesLabel;
    private javax.swing.JLabel hexCssLabel;
    private javax.swing.JSeparator hexCssSeparator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField mimeTypeRegexTextField;
    private javax.swing.JCheckBox namedColorsCheckBox;
    private javax.swing.JCheckBox resolveCssVariablesCheckBox;
    // End of variables declaration//GEN-END:variables
}
