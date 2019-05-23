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
package com.junichi11.netbeans.modules.color.codes.preview.gen;

import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeGeneratorItem;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodesProvider;
import com.junichi11.netbeans.modules.color.codes.preview.ui.ColorCodeGeneratorPanel;
import java.awt.Color;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.spi.editor.codegen.CodeGenerator;
import org.netbeans.spi.editor.codegen.CodeGeneratorContextProvider;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.text.NbDocument;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class ColorCodeGenerator implements CodeGenerator {

    private final JTextComponent textComponent;
    private final List<ColorCodeGeneratorItem> generatorItems;
    private static final Logger LOGGER = Logger.getLogger(ColorCodeGenerator.class.getName());

    /**
     *
     * @param context containing JTextComponent and possibly other items
     * registered by {@link CodeGeneratorContextProvider}
     * @param generatorItems
     */
    private ColorCodeGenerator(Lookup context, List<ColorCodeGeneratorItem> generatorItems) {
        textComponent = context.lookup(JTextComponent.class);
        this.generatorItems = generatorItems;
    }

    /**
     * The name which will be inserted inside Insert Code dialog
     */
    @NbBundle.Messages("ColorCodeGenerator.displayName=Color...")
    @Override
    public String getDisplayName() {
        return Bundle.ColorCodeGenerator_displayName();
    }

    /**
     * This will be invoked when user chooses this Generator from Insert Code
     * dialog
     */
    @NbBundle.Messages("ColorCodeGenerator.panel.title=Generate Color Code")
    @Override
    public void invoke() {
        assert SwingUtilities.isEventDispatchThread();
        ColorCodeGeneratorPanel panel = new ColorCodeGeneratorPanel(generatorItems);
        DialogDescriptor descriptor = new DialogDescriptor(panel, Bundle.ColorCodeGenerator_panel_title());
        Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setVisible(true);
        if (descriptor.getValue() == DialogDescriptor.OK_OPTION) {
            final ColorCodeGeneratorItem selectedGeneratorItem = panel.getSelectedGeneratorItem();
            final Color selectedColor = panel.getSelectedColor();
            final BaseDocument document = (BaseDocument) textComponent.getDocument();
            if (document == null) {
                return;
            }
            try {
                NbDocument.runAtomicAsUser((StyledDocument) document, () -> {
                    int caretPosition = textComponent.getCaretPosition();
                    ColorCodeFormatter formatter = selectedGeneratorItem.getFormatter();
                    if (formatter != null) {
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append(formatter.format(selectedColor));
                            if (panel.appendSemicolon()) {
                                sb.append(";"); // NOI18N
                            }
                            document.insertString(caretPosition, sb.toString(), null);
                        } catch (BadLocationException ex) {
                            LOGGER.log(Level.WARNING, ex.getMessage());
                        }
                    }
                });
            } catch (BadLocationException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage());
            }
            panel.setLastSelectedValues();
        }
        dialog.dispose();
    }

    @MimeRegistration(mimeType = "", service = CodeGenerator.Factory.class)
    public static class Factory implements CodeGenerator.Factory {

        @Override
        public List<? extends CodeGenerator> create(Lookup context) {
            JTextComponent textComponent = context.lookup(JTextComponent.class);
            if (textComponent == null) {
                return Collections.emptyList();
            }
            List<ColorCodeGeneratorItem> generators = getGeneratorItems(NbEditorUtilities.getMimeType(textComponent));
            if (generators.isEmpty()) {
                return Collections.emptyList();
            }
            return Collections.singletonList(new ColorCodeGenerator(context, generators));
        }

        private List<ColorCodeGeneratorItem> getGeneratorItems(String mimeType) {
            Collection<? extends ColorCodesProvider> providers = Lookup.getDefault().lookupAll(ColorCodesProvider.class);
            List<ColorCodeGeneratorItem> allGenerators = new ArrayList<>();
            for (ColorCodesProvider provider : providers) {
                if (provider.canGenerateColorCode()) {
                    List<ColorCodeGeneratorItem> generators = provider.getColorCodeGeneratorItems(mimeType);
                    if (generators != null) {
                        allGenerators.addAll(generators);
                    }
                }
            }
            return allGenerators;
        }
    }

}
