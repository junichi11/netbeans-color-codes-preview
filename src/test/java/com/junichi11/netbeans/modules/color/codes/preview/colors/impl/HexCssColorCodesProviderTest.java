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
package com.junichi11.netbeans.modules.color.codes.preview.colors.impl;

import com.junichi11.netbeans.modules.color.codes.preview.colors.HexColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.api.OffsetRange;
import com.junichi11.netbeans.modules.color.codes.preview.colors.spi.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.options.ColorCodesPreviewOptions;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.text.BadLocationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.editor.BaseDocument;

/**
 *
 * @author junichi11
 */
public class HexCssColorCodesProviderTest {

    public HexCssColorCodesProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetColorValuesHex() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: #000000  ;", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(Color.decode("#000000"), result.get(0).getColor());
        assertEquals("#000000", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(14, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: #00;", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesCssIntRGB() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: rgb(100, 100, 100);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(100, 100, 100), result.get(0).getColor());
        assertEquals("rgb(100, 100, 100)", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(25, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: rgb(-100, 100, 100);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesCssPercentRGB() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: rgb(100%,100%,100%);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(255, 255, 255), result.get(0).getColor());
        assertEquals("rgb(100%,100%,100%)", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(26, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: rgb(100,100%,100%);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesCssPercentRGBA() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: rgba(100%,  100%,100%, 0);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(255, 255, 255, 0), result.get(0).getColor());
        assertEquals("rgba(100%,  100%,100%, 0)", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(32, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: rgba(100%,  100%,100%, 0%);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesCssHSL() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: hsl(360,100%,100%);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(255, 255, 255), result.get(0).getColor());
        assertEquals("hsl(360,100%,100%)", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(25, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: hsl(370,100%,100%);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesCssHSLA() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(document, "color: hsla(0, 0%, 0%, 0);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(0, 0, 0, 0), result.get(0).getColor());
        assertEquals("hsla(0, 0%, 0%, 0)", result.get(0).getValue());
        assertEquals(7, result.get(0).getStartOffset());
        assertEquals(25, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: hsla(-10, 0%, 0%, 0);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesNamedColor() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        ColorCodesPreviewOptions.getInstance().setNamedColors(true);
        List<ColorValue> result = provider.getColorValues(document, "color: black;", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(Color.black, result.get(0).getColor());
        assertEquals(" black;", result.get(0).getValue());
        assertEquals(6, result.get(0).getStartOffset());
        assertEquals(13, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(document, "color: hoge", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesVariableColor() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/less");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider provider = new HexCssColorCodesProvider();
        ColorCodesPreviewOptions.getInstance().setNamedColors(false);
        HashMap<String, List<ColorValue>> variablesMap = new HashMap<>();
        variablesMap.put("$black", Arrays.asList(new HexColorValue("#000000", new OffsetRange(5, 12), 0)));
        List<ColorValue> result = provider.getColorValues(document, "color: $black;", 0, variablesMap);
        assertEquals(1, result.size());
        assertEquals(Color.black, result.get(0).getColor());
        assertEquals("#000000", result.get(0).getValue());
        assertEquals(5, result.get(0).getStartOffset());
        assertEquals(12, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());
    }

    @Test
    public void testGetStartIndex() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/javascript");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider instance = new HexCssColorCodesProvider();
        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(true);
        int result = instance.getStartIndex(document, 5);
        assertEquals(5, result);

        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(false);
        result = instance.getStartIndex(document, 5);
        assertEquals(5, result);
    }

    @Test
    public void testGetStartIndexLess() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/less");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider instance = new HexCssColorCodesProvider();
        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(true);
        int result = instance.getStartIndex(document, 5);
        assertEquals(0, result);

        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(false);
        result = instance.getStartIndex(document, 5);
        assertEquals(5, result);
    }

    @Test
    public void testGetStartIndexScss() throws BadLocationException {
        BaseDocument document = new BaseDocument(false, "text/scss");
        document.insertString(0, "test", null);
        HexCssColorCodesProvider instance = new HexCssColorCodesProvider();
        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(true);
        int result = instance.getStartIndex(document, 10);
        assertEquals(0, result);

        ColorCodesPreviewOptions.getInstance().setResolveCssVariables(false);
        result = instance.getStartIndex(document, 10);
        assertEquals(10, result);
    }

}
