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
package com.junichi11.netbeans.modules.color.codes.preview.ui;

import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.editor.document.LineDocumentUtils;
import org.netbeans.editor.BaseDocument;
import org.openide.text.NbDocument;
import org.openide.util.NbBundle;

/**
 *
 * @author junichi11
 */
public class ColorCodesPanel extends JComponent {

    private static final long serialVersionUID = -1386991598331115862L;
    private static final Logger LOGGER = Logger.getLogger(ColorCodesPanel.class.getName());

    @NbBundle.Messages({
        "ColorCodesPanel.colorChooser.title=Select a Color"
    })
    public ColorCodesPanel(List<ColorValue> colorValues) {
        setLayout(new GridLayout(0, 1));
        for (final ColorValue colorValue : colorValues) {
            JLabel label = new JLabel(colorValue.getValue());
            Font font = label.getFont();
            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, font.getSize()));
            label.setOpaque(true);
            final Color backgroundColor = colorValue.getColor();
            Color foregroundColor = ColorsUtils.getForeground(backgroundColor);
            label.setBackground(backgroundColor);
            label.setForeground(foregroundColor);
            label.setBorder(new EmptyBorder(3, 5, 3, 5));
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // show dialog
                    if (!colorValue.isEditable()) {
                        return;
                    }
                    final Color selectedColor = JColorChooser.showDialog(
                            ColorCodesPanel.this,
                            Bundle.ColorCodesPanel_colorChooser_title(),
                            backgroundColor
                    );
                    if (selectedColor == null) {
                        return;
                    }
                    if (!selectedColor.equals(backgroundColor)) {
                        JTextComponent editor = EditorRegistry.lastFocusedComponent();
                        if (editor == null) {
                            return;
                        }
                        final BaseDocument document = (BaseDocument) editor.getDocument();
                        if (document == null) {
                            return;
                        }
                        final int startOffset = colorValue.getStartOffset();
                        final int endOffset = colorValue.getEndOffset();
                        int line = colorValue.getLine();
                        if (line < 0) {
                            return;
                        }
                        final int lineOffset = LineDocumentUtils.getLineStartFromIndex(document, line);
                        try {
                            // replace a color value
                            NbDocument.runAtomicAsUser((StyledDocument) document, () -> {
                                try {
                                    int removeStart = lineOffset + startOffset;
                                    document.remove(removeStart, endOffset - startOffset);
                                    document.insertString(removeStart, colorValue.getFormatter().format(selectedColor), null);
                                } catch (BadLocationException ex) {
                                    LOGGER.log(Level.WARNING, ex.getMessage());
                                }
                            });
                        } catch (BadLocationException ex) {
                            LOGGER.log(Level.WARNING, ex.getMessage());
                        }
                    }
                }
            });
            add(label);
        }
    }

}
