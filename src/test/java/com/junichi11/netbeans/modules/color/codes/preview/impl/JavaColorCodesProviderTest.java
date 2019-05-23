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
package com.junichi11.netbeans.modules.color.codes.preview.impl;

import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author junichi11
 */
public class JavaColorCodesProviderTest {

    public JavaColorCodesProviderTest() {
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

    /**
     * Test of getColorValues method, of class JavaColorCodesProvider.
     */
    @Test
    public void testGetColorValuesStandardColor() {
        JavaColorCodesProvider provider = new JavaColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(null, "  Color.black;  // black", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(Color.black, result.get(0).getColor());
        assertEquals("Color.black", result.get(0).getValue());
        assertEquals(2, result.get(0).getStartOffset());
        assertEquals(13, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        result = provider.getColorValues(null, "  Color.Black;  // black", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesRGBColor() {
        JavaColorCodesProvider provider = new JavaColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(null, "Color color = new Color( 0, 0, 255);", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(0, 0, 255), result.get(0).getColor());
        assertEquals("new Color( 0, 0, 255)", result.get(0).getValue());
        assertEquals(14, result.get(0).getStartOffset());
        assertEquals(35, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        try {
            result = provider.getColorValues(null, "Color color = new Color(256, 0, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(-1, 0, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 256, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, -256, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, 256);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, -1);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // unsupported patterns
        result = provider.getColorValues(null, "Color color = new Color(0, true);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
        result = provider.getColorValues(null, "Color color = new Color(ColorSpace.getInstance(ColorSpace.CS_GRAY), components, 0);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
        result = provider.getColorValues(null, "Color color = new Color(1.0f, 1.0f, 1.0f);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
        result = provider.getColorValues(null, "Color color = new Color(0.0f, 0.1f, 0.5f, 0.0f);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
        result = provider.getColorValues(null, "Color color = new Color(0);", 0, Collections.emptyMap());
        assertEquals(0, result.size());
        result = provider.getColorValues(null, "\"new Color(\";", 0, Collections.emptyMap());
        assertEquals(0, result.size());
    }

    @Test
    public void testGetColorValuesRGBAColor() {
        JavaColorCodesProvider provider = new JavaColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(null, "Color color = new Color( 255, 0, 255, 100 );", 0, Collections.emptyMap());
        assertEquals(1, result.size());
        assertEquals(new Color(255, 0, 255, 100), result.get(0).getColor());
        assertEquals("new Color( 255, 0, 255, 100 )", result.get(0).getValue());
        assertEquals(14, result.get(0).getStartOffset());
        assertEquals(43, result.get(0).getEndOffset());
        assertEquals(true, result.get(0).isEditable());

        try {
            result = provider.getColorValues(null, "Color color = new Color(256, 0, 0, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(-1, 0, 0, 100);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 256, 0, 10);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, -256, 0, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, 256, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, -1, 0);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, 255, 256);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
            result = provider.getColorValues(null, "Color color = new Color(0, 0, 1, -1);", 0, Collections.emptyMap());
            assertEquals(0, result.size());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetColorValuesArray() {
        JavaColorCodesProvider provider = new JavaColorCodesProvider();
        List<ColorValue> result = provider.getColorValues(null, "new Color[]{Color.blue, new Color(153, 255, 0)};", 0, Collections.emptyMap());
        assertEquals(2, result.size());
        assertEquals(Color.blue, result.get(0).getColor());
        assertEquals(new Color(153, 255, 0), result.get(1).getColor());
        assertEquals("Color.blue", result.get(0).getValue());
        assertEquals("new Color(153, 255, 0)", result.get(1).getValue());
        assertEquals(12, result.get(0).getStartOffset());
        assertEquals(22, result.get(0).getEndOffset());
        assertEquals(24, result.get(1).getStartOffset());
        assertEquals(46, result.get(1).getEndOffset());
    }

}
