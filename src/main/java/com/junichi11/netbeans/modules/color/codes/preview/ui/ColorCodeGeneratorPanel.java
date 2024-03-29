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
package com.junichi11.netbeans.modules.color.codes.preview.ui;

import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeGeneratorItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

/**
 *
 * @author junichi11
 */
public final class ColorCodeGeneratorPanel extends JPanel {

    private static ColorCodeGeneratorItem LAST_SELECTED_ITEM = null;
    private static Color LAST_SELECTED_COLOR = ColorCodesPreviewOptions.getInstance().getLastSelectedColor();
    private static boolean LAST_SELECTED_APPEND_SEMICOLON = ColorCodesPreviewOptions.getInstance().isLastAppendSemicolonSelected();

    private static final long serialVersionUID = 2893569100900202502L;
    private final List<ColorCodeGeneratorItem> generatorItems;

    private ColorCodeGeneratorPanel() {
        this.generatorItems = null;
    }

    /**
     * Creates new form ColorCodeGeneratorPanel
     */
    public ColorCodeGeneratorPanel(List<ColorCodeGeneratorItem> generatorItems) {
        this.generatorItems = generatorItems;
        initComponents();
        init();
    }

    private void init() {
        generatorItemComboBox.setRenderer(new ListCellRendererImpl(generatorItemComboBox.getRenderer()));
        for (ColorCodeGeneratorItem generatorItem : generatorItems) {
            generatorItemComboBox.addItem(generatorItem);
        }
        setLastSelectedGeneratorItem();
        setAppendSemicolonCheckBox();
        setLastSelectedColor();

        // #48
        ColorSelectionModel colorSelectionModel = generatorColorChooser.getSelectionModel();
        colorSelectionModel.addChangeListener((ChangeEvent e) -> updatePreview());
        updatePreview();
    }

    private void updatePreview() {
        assert EventQueue.isDispatchThread();
        ColorCodeGeneratorItem selectedItem = getSelectedGeneratorItem();
        ColorCodeFormatter formatter = selectedItem.getFormatter();
        String formattedColor = ""; // NOI18N
        if (formatter != null) {
            formattedColor = formatter.format(getSelectedColor());
        }
        previewLabel.setText(formattedColor);
    }

    private void setLastSelectedGeneratorItem() {
        if (LAST_SELECTED_ITEM != null) {
            for (ColorCodeGeneratorItem generatorItem : generatorItems) {
                if (LAST_SELECTED_ITEM == generatorItem) {
                    generatorItemComboBox.setSelectedItem(generatorItem);
                    break;
                }
                if (LAST_SELECTED_ITEM.getDisplayName().equals(generatorItem.getDisplayName())) {
                    String lastTooltipText = LAST_SELECTED_ITEM.getTooltipText();
                    String tooltipText = generatorItem.getTooltipText();
                    if (lastTooltipText != null && lastTooltipText.equals(tooltipText)) {
                        generatorItemComboBox.setSelectedItem(generatorItem);
                        break;
                    }
                    if (lastTooltipText == null && tooltipText == null) {
                        generatorItemComboBox.setSelectedItem(generatorItem);
                        break;
                    }
                }
            }
        }
    }

    private void setAppendSemicolonCheckBox() {
        appendSemicolonCheckBox.setSelected(LAST_SELECTED_APPEND_SEMICOLON);
    }

    private void setLastSelectedColor() {
        if (LAST_SELECTED_COLOR != null) {
            generatorColorChooser.setColor(LAST_SELECTED_COLOR);
        }
    }

    public void setLastSelectedValues() {
        LAST_SELECTED_ITEM = (ColorCodeGeneratorItem) generatorItemComboBox.getSelectedItem();
        LAST_SELECTED_APPEND_SEMICOLON = appendSemicolonCheckBox.isSelected();
        LAST_SELECTED_COLOR = getSelectedColor();
    }

    public Color getSelectedColor() {
        return generatorColorChooser.getSelectionModel().getSelectedColor();
    }

    public static Color getLastSelectedColor() {
        return LAST_SELECTED_COLOR;
    }

    public static boolean isLastAppendSemicolonSelected() {
        return LAST_SELECTED_APPEND_SEMICOLON;
    }

    public ColorCodeGeneratorItem getSelectedGeneratorItem() {
        ColorCodeGeneratorItem selectedGeneratorItem = (ColorCodeGeneratorItem) generatorItemComboBox.getSelectedItem();
        return selectedGeneratorItem;
    }

    public boolean appendSemicolon() {
        return appendSemicolonCheckBox.isSelected();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generatorItemComboBox = new JComboBox<>();
        generatorColorChooser = new JColorChooser();
        formatLabel = new JLabel();
        appendSemicolonCheckBox = new JCheckBox();
        previewLabel = new JLabel();

        generatorItemComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generatorItemComboBoxActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(formatLabel, NbBundle.getMessage(ColorCodeGeneratorPanel.class, "ColorCodeGeneratorPanel.formatLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(appendSemicolonCheckBox, NbBundle.getMessage(ColorCodeGeneratorPanel.class, "ColorCodeGeneratorPanel.appendSemicolonCheckBox.text")); // NOI18N

        Mnemonics.setLocalizedText(previewLabel, "PREVIEW"); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(generatorColorChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(formatLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(generatorItemComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(previewLabel))
                    .addComponent(appendSemicolonCheckBox))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(generatorItemComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(formatLabel)
                    .addComponent(previewLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(appendSemicolonCheckBox)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generatorColorChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void generatorItemComboBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_generatorItemComboBoxActionPerformed
        updatePreview();
    }//GEN-LAST:event_generatorItemComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox appendSemicolonCheckBox;
    private JLabel formatLabel;
    private JColorChooser generatorColorChooser;
    private JComboBox<ColorCodeGeneratorItem> generatorItemComboBox;
    private JLabel previewLabel;
    // End of variables declaration//GEN-END:variables

    private static class ListCellRendererImpl implements ListCellRenderer<ColorCodeGeneratorItem> {

        private final ListCellRenderer<? super ColorCodeGeneratorItem> defaultDenderer;

        public ListCellRendererImpl(ListCellRenderer<? super ColorCodeGeneratorItem> defaultDenderer) {
            this.defaultDenderer = defaultDenderer;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ColorCodeGeneratorItem> list, ColorCodeGeneratorItem value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) defaultDenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText(value.getDisplayName());
            label.setToolTipText(value.getTooltipText());
            return label;
        }
    }
}
