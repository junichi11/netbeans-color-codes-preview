/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package com.junichi11.netbeans.modules.color.codes.preview.ui;

import com.junichi11.netbeans.modules.color.codes.preview.colors.ColorValue;
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
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.Utilities;
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
                        final int lineOffset = Utilities.getRowStartFromLineOffset(document, line);
                        try {
                            // replace a color value
                            NbDocument.runAtomicAsUser((StyledDocument) document, new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        int removeStart = lineOffset + startOffset;
                                        document.remove(removeStart, endOffset - startOffset);
                                        document.insertString(removeStart, ColorsUtils.toFormattedString(selectedColor, colorValue.getType()), null);
                                    } catch (BadLocationException ex) {
                                        LOGGER.log(Level.WARNING, ex.getMessage());
                                    }
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
