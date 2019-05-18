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

import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesPreviewOptionsPanel;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesProvider;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

final class ColorCodesPreviewPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 844920036564457720L;

    private final ColorCodesPreviewOptionsPanelController controller;
    private final Map<String, JCheckBox> enabledCheckBoxes = new HashMap<>();
    private final Map<ColorCodesProvider, ColorCodesPreviewOptionsPanel> providerOptionsPanels = new HashMap<>();
    private ColorCodesPreviewOptionsPanel currentSelectedPanel = ColorCodesPreviewOptionsPanel.createEmptyPanel();
    private JCheckBox enabledCheckBox = new JCheckBox(Bundle.ColorCodesPreviewPanel_enabled());

    ColorCodesPreviewPanel(ColorCodesPreviewOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        setEnabledCheckBoxes();
        init();
        // TODO listen to changes in form fields and call controller.changed()
    }

    @NbBundle.Messages("ColorCodesPreviewPanel.enabled=Enabled")
    private void init() {
        descriptionLabel.setText(""); // NOI18N
        providersComboBox.setRenderer(new ProvidersCellRenderer(providersComboBox.getRenderer()));
        for (ColorCodesProvider provider : getProviders()) {
            ColorCodesPreviewOptionsPanel panel = provider.getOptionsPanel();
            if (panel == null) {
                panel = ColorCodesPreviewOptionsPanel.createEmptyPanel();
            }
            panel.addChangeListener(controller);
            providerOptionsPanels.put(provider, panel);
            providersComboBox.addItem(provider);
        }
        setDescription();
        setProviderPanels();
        errorLabel.setForeground(UIManager.getColor("nb.errorForeground")); // NOI18N
        setErrorMessage(null);
    }

    private void setEnabledCheckBoxes() {
        for (ColorCodesProvider provider : getProviders()) {
            JCheckBox checkBox = new JCheckBox(Bundle.ColorCodesPreviewPanel_enabled());
            checkBox.setToolTipText(provider.getDescription());
            enabledCheckBoxes.put(provider.getId(), checkBox);
        }
    }

    private Collection<? extends ColorCodesProvider> getProviders() {
        return Lookup.getDefault().lookupAll(ColorCodesProvider.class);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        providersComboBox = new javax.swing.JComboBox<>();
        providerOptionsPanel = new javax.swing.JPanel();
        enabledPanel = new javax.swing.JPanel();
        errorLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();

        providersComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                providersComboBoxItemStateChanged(evt);
            }
        });

        providerOptionsPanel.setLayout(new java.awt.BorderLayout());

        enabledPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(errorLabel, "ERROR"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, "DESCRIPTION"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(providerOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enabledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(errorLabel)
                    .addComponent(descriptionLabel))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(providersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enabledPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(providerOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorLabel))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void providersComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_providersComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            setDescription();
            setProviderPanels();
        }
    }//GEN-LAST:event_providersComboBoxItemStateChanged

    private void setProviderPanels() {
        ColorCodesProvider selectedProvider = (ColorCodesProvider) providersComboBox.getSelectedItem();
        setEnabledPanel(selectedProvider);
        setOptionsPanel(selectedProvider);
    }

    private void setDescription() {
        ColorCodesProvider selectedProvider = (ColorCodesProvider) providersComboBox.getSelectedItem();
        descriptionLabel.setText(selectedProvider.getDescription());
    }

    private void setEnabledPanel(ColorCodesProvider selectedProvider) {
        JCheckBox checkBox = enabledCheckBoxes.get(selectedProvider.getId());
        enabledPanel.remove(enabledCheckBox);
        enabledPanel.add(checkBox);
        enabledCheckBox = checkBox;
        enabledPanel.revalidate();
        enabledPanel.repaint();
    }

    private void setOptionsPanel(ColorCodesProvider selectedProvider) {
        providerOptionsPanel.remove(currentSelectedPanel);
        providerOptionsPanel.add(providerOptionsPanels.get(selectedProvider));
        currentSelectedPanel = providerOptionsPanels.get(selectedProvider);
        providerOptionsPanel.revalidate();
        providerOptionsPanel.repaint();
    }

    void load() {
        providerOptionsPanels.forEach((provider, panel) -> panel.load());
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        enabledCheckBoxes.forEach((id, checkBox) -> {
            checkBox.setSelected(options.isEnabled(id));
        });
    }

    void store() {
        providerOptionsPanels.forEach((provider, panel) -> panel.store());
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        enabledCheckBoxes.forEach((id, checkBox) -> {
            options.setEnabled(id, checkBox.isSelected());
        });
    }

    boolean valid() {
        for (ColorCodesPreviewOptionsPanel panel : providerOptionsPanels.values()) {
            if (!panel.valid()) {
                setErrorMessage(panel.getErrorMessage());
                return false;
            }
        }
        setErrorMessage(null);
        return true;
    }

    private void setErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            errorMessage = "<html>" + errorMessage.replaceAll("\n", "<br>"); // NOI18N
        }
        errorLabel.setText(errorMessage);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JPanel enabledPanel;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JPanel providerOptionsPanel;
    private javax.swing.JComboBox<ColorCodesProvider> providersComboBox;
    // End of variables declaration//GEN-END:variables
}
