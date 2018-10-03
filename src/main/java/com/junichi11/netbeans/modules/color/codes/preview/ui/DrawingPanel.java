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

import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import com.junichi11.netbeans.modules.color.codes.preview.colors.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.utils.ColorsUtils;
import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;
import org.netbeans.api.editor.document.LineDocumentUtils;
import org.netbeans.api.editor.fold.FoldHierarchy;
import org.netbeans.api.editor.fold.FoldHierarchyEvent;
import org.netbeans.api.editor.fold.FoldHierarchyListener;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.editor.settings.FontColorNames;
import org.netbeans.api.editor.settings.FontColorSettings;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.BaseTextUI;
import org.netbeans.editor.Coloring;
import org.netbeans.editor.EditorUI;
import org.netbeans.editor.Utilities;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Mutex;
import org.openide.util.WeakListeners;

/**
 *
 * @author junichi11
 */
public final class DrawingPanel extends JPanel implements DocumentListener, PreferenceChangeListener, ComponentListener, FoldHierarchyListener {

    private Color backgroundColor = Color.WHITE;
    private boolean enabled;
    private final FoldHierarchy foldHierarchy;
    private final JTextComponent textComponent;
    private final BaseDocument document;
    private final Preferences prefs;
    private final LookupListener lookupListener;

    private static final int DEFAULT_WIDTH = 16;
    private static final Logger LOGGER = Logger.getLogger(DrawingPanel.class.getName());
    // check sass and less variables e.g. $green: #0f0;, @green: #0f0;
    private static final Pattern CSS_VARIABLE_PATTERN = Pattern.compile("(?<var>[\\$@][^ ]+)\\s*:\\s*(?<value>).+\\s*;"); // NOI18N
    private static final long serialVersionUID = 8161755434377410789L;

    public DrawingPanel(JTextComponent editor) {
        super(new BorderLayout());
        this.textComponent = editor;
        this.document = (BaseDocument) editor.getDocument();
        this.foldHierarchy = FoldHierarchy.get(editor);
        updateColors();

        prefs = MimeLookup.getLookup(MimePath.EMPTY).lookup(Preferences.class);
        prefs.addPreferenceChangeListener(WeakListeners.create(PreferenceChangeListener.class, this, prefs));
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        options.addPreferenceChangeListener(WeakListeners.create(PreferenceChangeListener.class, this, prefs));
        // lookup listener
        Lookup.Result<FontColorSettings> lookupResult = MimeLookup.getLookup(MimePath.get(NbEditorUtilities.getMimeType(textComponent))).lookupResult(FontColorSettings.class);
        lookupListener = (LookupEvent le) -> updateColors();
        lookupResult.addLookupListener(WeakListeners.create(LookupListener.class, lookupListener, lookupResult));
        preferenceChange(null);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dim = textComponent.getSize();
        dim.width = DEFAULT_WIDTH;
        return dim;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        if (!enabled) {
            return;
        }
        super.paintComponent(g);
        Utilities.runViewHierarchyTransaction(textComponent, true, () -> paintComponentUnderLock(g));
    }

    private void paintComponentUnderLock(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle clip = g2d.getClipBounds();

        g2d.setColor(backgroundColor());
        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);

        JTextComponent component = textComponent;
        TextUI textUI = component.getUI();
        EditorUI editorUI = Utilities.getEditorUI(component);
        if (editorUI == null) {
            return;
        }
        View rootView = Utilities.getDocumentView(component);
        if (rootView == null) {
            return;
        }
        try {
            drawColorRect(component, textUI, clip, rootView, g2d);
        } catch (BadLocationException ex) {
            LOGGER.log(Level.WARNING, "Incorrect offset : {0}", ex.offsetRequested()); // NOI18N
        }
    }

    private void drawColorRect(JTextComponent component, TextUI textUI, Rectangle clip, View rootView, Graphics2D g2d) throws BadLocationException {
        int startPos = getPosFromY(component, textUI, clip.y);
        int startViewIndex = rootView.getViewIndex(startPos, Position.Bias.Forward);
        int rootViewCount = rootView.getViewCount();

        if (startViewIndex >= 0 && startViewIndex < rootViewCount) {
            Map<String, List<ColorValue>> cssVariables = new HashMap<>();
            int clipEndY = clip.y + clip.height;
            int start = resolveCssVariables() ? 0 : startViewIndex;
            for (int i = start; i < rootViewCount; i++) {
                View view = rootView.getView(i);
                if (view == null) {
                    break;
                }

                // for zoom-in or zoom-out
                Rectangle rec1 = component.modelToView(view.getStartOffset());
                Rectangle rec2 = component.modelToView(view.getEndOffset() - 1);
                if (rec2 == null || rec1 == null) {
                    break;
                }

                int y = rec1.y;
                double lineHeight = (rec2.getY() + rec2.getHeight() - rec1.getY());
                if (document != null) {
                    String line = getLineText((BaseDocument) document, view);
                    int indexOfLF = line.indexOf("\n"); // NOI18N
                    if (indexOfLF != -1) {
                        line = line.substring(0, indexOfLF);
                    }

                    // get color values
                    List<ColorValue> colorValues = getAllColorValues(line, -1); // XXX
                    ColorsUtils.sort(colorValues);

                    // for sass and less variables
                    checkCssVariables(line, cssVariables, colorValues);
                    if (i < startViewIndex) {
                        continue;
                    }
                    drawColorRect(colorValues, g2d, lineHeight, y);
                }
                y += lineHeight;
                if (y >= clipEndY) {
                    break;
                }
            }
        }
    }

    private void drawColorRect(List<ColorValue> colorValues, Graphics2D g2d, double lineHeight, int y) {
        for (ColorValue colorValue : colorValues) {
            g2d.setColor(colorValue.getColor());
            int margin = 2;
            int recHeight = (int) lineHeight > 8 ? (int) lineHeight - margin * 2 : (int) lineHeight;
            int recWidth = DEFAULT_WIDTH - margin * 2;

            if (colorValues.size() == 1) {
                g2d.fillRect(margin, (int) y + margin, recWidth, recHeight);
            } else if (colorValues.size() >= 2) {
                // show the second color if mulitiple color values exist
                g2d.fillRect(margin, (int) y + margin, recWidth / 2, recHeight);
                ColorValue secondColorValue = colorValues.get(1);
                g2d.setColor(secondColorValue.getColor());
                g2d.fillRect(margin + recWidth / 2, y + margin, recWidth / 2, recHeight);
            }

            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(margin, (int) y + margin, DEFAULT_WIDTH - margin * 2, recHeight);
            break;
        }
    }

    private void checkCssVariables(String line, Map<String, List<ColorValue>> cssVariables, List<ColorValue> colorValues) {
        if (!isLessOrSass()) {
            return;
        }

        Matcher matcher = CSS_VARIABLE_PATTERN.matcher(line);
        if (matcher.find()) {
            String variable = matcher.group("var"); // NOI18N
            if (variable != null) {
                cssVariables.put(variable, colorValues);
            }
        } else {
            final String l = line;
            Map<Integer, String> map = new HashMap<>();
            cssVariables.forEach((String var, List<ColorValue> colors) -> {
                if (l.contains(var)) {
                    int indexOfVar = l.indexOf(var);
                    int offsetBehindVariableName = indexOfVar + var.length();
                    if (offsetBehindVariableName < l.length()) {
                        // e.g. when searche $green, ignore $green1, $green2,...
                        char c = l.charAt(offsetBehindVariableName);
                        if (c == ' ' || c == ';') {
                            map.put(indexOfVar, var);
                        }
                    }
                }
            });
            List<Integer> offsetNumbers = new ArrayList<>(map.keySet());
            Collections.sort(offsetNumbers);
            offsetNumbers.forEach(offset -> colorValues.addAll(cssVariables.get(map.get(offset))));
        }
    }

    private List<ColorValue> getAllColorValues(String line, int lineNumber) {
        List<ColorValue> hexColorValues = ColorsUtils.getHexColorCodes(line, lineNumber);
        List<ColorValue> colorValues = new ArrayList<>(hexColorValues);
        colorValues.addAll(ColorsUtils.getCssIntRGBs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssIntRGBAs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssPercentRGBAs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLs(line, lineNumber));
        colorValues.addAll(ColorsUtils.getCssHSLAs(line, lineNumber));
        ColorCodesPreviewOptions options = ColorCodesPreviewOptions.getInstance();
        if (options.useNamedColors()) {
            colorValues.addAll(ColorsUtils.getNamedColors(line, lineNumber));
        }
        return colorValues;
    }

    /* from versioning.ui DiffSidebar */
    private int getPosFromY(JTextComponent component, TextUI textUI, int y) throws BadLocationException {
        if (textUI instanceof BaseTextUI) {
            return ((BaseTextUI) textUI).getPosFromY(y);
        } else {
            // fallback to ( less otimized than ((BaseTextUI) textUI).getPosFromY(y) )
            return textUI.modelToView(component, textUI.viewToModel(component, new Point(0, y))).y;
        }
    }

    private Color backgroundColor() {
        return backgroundColor;
    }

    /**
     * Update colors.
     */
    public void updateColors() {
        EditorUI editorUI = Utilities.getEditorUI(textComponent);
        if (editorUI == null) {
            return;
        }
        String mimeType = NbEditorUtilities.getMimeType(textComponent);
        FontColorSettings fontColorSettings = MimeLookup.getLookup(MimePath.get(mimeType)).lookup(FontColorSettings.class);
        Coloring lineColoring = Coloring.fromAttributeSet(fontColorSettings.getFontColors(FontColorNames.LINE_NUMBER_COLORING));
        Coloring defaultColoring = Coloring.fromAttributeSet(fontColorSettings.getFontColors(FontColorNames.DEFAULT_COLORING));

        if (lineColoring == null) {
            return;
        }

        // use the same color as GlyphGutter
        final Color backColor = lineColoring.getBackColor();
        // set to white by o.n.swing.plaf/src/org/netbeans/swing/plaf/aqua/AquaLFCustoms
        if (org.openide.util.Utilities.isMac()) {
            backgroundColor = backColor;
        } else {
            backgroundColor = UIManager.getColor("NbEditorGlyphGutter.background"); //NOI18N
        }
        if (null == backgroundColor) {
            if (backColor != null) {
                backgroundColor = backColor;
            } else {
                backgroundColor = defaultColoring.getBackColor();
            }
        }
    }

    private void initialize() {
        document.addDocumentListener(this);
        textComponent.addComponentListener(this);
        foldHierarchy.addFoldHierarchyListener(this);
    }

    private void release() {
        document.removeDocumentListener(this);
        textComponent.removeComponentListener(this);
        foldHierarchy.removeFoldHierarchyListener(this);
    }

    private boolean resolveCssVariables() {
        return isLessOrSass()
                && ColorCodesPreviewOptions.getInstance().resolveCssVariables();
    }

    private boolean isLessOrSass() {
        String mimeType = NbEditorUtilities.getMimeType(textComponent);
        return "text/less".equals(mimeType) || "text/scss".equals(mimeType); // NOI18N
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt == null
                || ColorsSideBarFactory.KEY_COLORS.equals(evt.getKey())
                || ColorCodesPreviewOptions.MIME_TYPE_REGEX.equals(evt.getKey())) {
            enabled = prefs.getBoolean(ColorsSideBarFactory.KEY_COLORS, ColorsSideBarFactory.DEFAULT_COLORS)
                    && isMimeTypeEnabled();
            setVisible(enabled);
            if (!enabled) {
                release();
            } else {
                release();
                initialize();
            }
        }
    }

    private boolean isMimeTypeEnabled() {
        String mimeType = NbEditorUtilities.getMimeType(textComponent);
        if (mimeType != null) {
            return mimeType.matches(ColorCodesPreviewOptions.getInstance().getMimeTypeRegex());
        }
        return false;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED || e.isPopupTrigger()) {
            onClick(e);
        } else {
            super.processMouseEvent(e);
        }
    }

    private void onClick(MouseEvent e) {
        Point point = new Point(e.getPoint());
        SwingUtilities.convertPointToScreen(point, this);
        List<ColorValue> colorValues = getColorValuesAt(e);
        ColorsUtils.sort(colorValues);
        if (colorValues.isEmpty()) {
            return;
        }
        PopupWindow popup = new PopupWindow(this, colorValues);
        popup.show(new Point(point.x + DEFAULT_WIDTH, point.y));
    }

    private List<ColorValue> getColorValuesAt(MouseEvent e) {
        int line = getLineFromMouseEvent(e);
        if (line == -1) {
            return Collections.emptyList();
        }
        String lineText = getLineText((BaseDocument) textComponent.getDocument(), line);
        if (lineText == null || lineText.isEmpty()) {
            return Collections.emptyList();
        }
        return getAllColorValues(lineText, line);
    }

    private int getLineFromMouseEvent(MouseEvent e) {
        int line = -1;
        EditorUI editorUI = Utilities.getEditorUI(textComponent);
        if (document == null) {
            return line;
        }
        if (editorUI != null) {
            try {
                JTextComponent component = editorUI.getComponent();
                if (component != null) {
                    TextUI textUI = component.getUI();
                    int clickOffset = textUI.viewToModel(component, new Point(0, e.getY()));
                    line = LineDocumentUtils.getLineIndex(document, clickOffset);
                }
            } catch (BadLocationException ex) {
                LOGGER.log(Level.WARNING, "getLineFromMouseEvent", ex); // NOI18N
            }
        }
        return line;
    }

    /**
     * Get text for a specified line.
     *
     * @param document document
     * @param line line number
     * @return line text
     */
    private String getLineText(BaseDocument document, int line) {
        if (document == null || line < 0) {
            return ""; // NOI18N
        }
        int startOffset = LineDocumentUtils.getLineStartFromIndex(document, line);
        if (startOffset == -1) {
            return ""; // NOI18N
        }
        try {
            int endOffset = LineDocumentUtils.getLineEnd(document, startOffset);
            if (endOffset == -1) {
                return ""; // NOI18N
            }
            return document.getText(startOffset, endOffset - startOffset);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return ""; // NOI18N
    }

    private String getLineText(Document document, View view) {
        if (document == null || view == null) {
            return ""; // NOI18N
        }
        int startOffset = view.getStartOffset();
        int endOffset = view.getEndOffset();
        try {
            return document.getText(startOffset, endOffset - startOffset);
        } catch (BadLocationException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        return ""; // NOI18N
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Mutex.EVENT.readAccess(() -> revalidate());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void foldHierarchyChanged(FoldHierarchyEvent e) {
        Mutex.EVENT.readAccess(() -> {
            repaint(textComponent.getVisibleRect());
            // XXX
            // sleep for a little time because the repaint method run
            // before the view is changed
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage());
            }
        });
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        refresh();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        refresh();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        refresh();
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> repaint());
    }
}
