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
package com.junichi11.netbeans.modules.color.codes.preview.impl.colors;

import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.JavaColorType;
import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author junichi11
 */
public class JavaColorCodeFormatterTest {

    public JavaColorCodeFormatterTest() {
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
     * Test of format method, of class JavaColorCodeFormatter.
     */
    @Test
    public void testStandardColorFormat() {
        JavaColorCodeFormatter formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_STANDARD_COLOR);
        String result = formatter.format(Color.BLACK);
        assertEquals("Color.BLACK", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("new Color(0, 100, 200)", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("new Color(0, 100, 200, 100)", result);
    }

    @Test
    public void testIntRGBsFormat() {
        // decimal
        JavaColorCodeFormatter formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B);
        String result = formatter.format(Color.BLACK);
        assertEquals("new Color(0, 0, 0)", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("new Color(0, 100, 200)", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("new Color(0, 100, 200, 100)", result);

        // hex
        formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B, new RGBAIntTypes(IntType.Hex, IntType.Hex, IntType.Hex));
        result = formatter.format(Color.BLACK);
        assertEquals("new Color(0x00, 0x00, 0x00)", result);
        result = formatter.format(new Color(0, 0x50, 0xa0));
        assertEquals("new Color(0x00, 0x50, 0xa0)", result);
        result = formatter.format(new Color(0, 0x07, 0x50, 0x77));
        assertEquals("new Color(0x00, 0x07, 0x50, 0x77)", result);

        // mixed
        formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B, new RGBAIntTypes(IntType.Hex, IntType.Decimal, IntType.Hex));
        result = formatter.format(Color.BLACK);
        assertEquals("new Color(0x00, 0, 0x00)", result);
        result = formatter.format(new Color(0, 100, 0xa0));
        assertEquals("new Color(0x00, 100, 0xa0)", result);
        result = formatter.format(new Color(0, 255, 0xff, 0x77));
        assertEquals("new Color(0x00, 255, 0xff, 0x77)", result);
    }

    @Test
    public void testIntRGBAsFormat() {
        // decimal
        JavaColorCodeFormatter formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B_A);
        String result = formatter.format(Color.BLACK);
        assertEquals("new Color(0, 0, 0, 255)", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("new Color(0, 100, 200, 255)", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("new Color(0, 100, 200, 100)", result);

        // hex
        formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B_A, RGBAIntTypes.ALL_HEX);
        result = formatter.format(Color.BLACK);
        assertEquals("new Color(0x00, 0x00, 0x00, 0xff)", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("new Color(0x00, 0x64, 0xc8, 0xff)", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("new Color(0x00, 0x64, 0xc8, 0x64)", result);

        // mixed
        formatter = new JavaColorCodeFormatter(JavaColorType.JAVA_INT_R_G_B_A, new RGBAIntTypes(IntType.Hex, IntType.Decimal, IntType.Hex, IntType.Decimal));
        result = formatter.format(Color.BLACK);
        assertEquals("new Color(0x00, 0, 0x00, 255)", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("new Color(0x00, 100, 0xc8, 255)", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("new Color(0x00, 100, 0xc8, 100)", result);
    }

    @Test
    public void testDecodeFormat() {
        JavaColorCodeFormatter formatter = new JavaColorCodeFormatter(JavaColorType.DECODE);
        String result = formatter.format(Color.BLACK);
        assertEquals("Color.decode(\"#000000\")", result);
        result = formatter.format(new Color(0, 100, 200));
        assertEquals("Color.decode(\"#0064c8\")", result);
        result = formatter.format(new Color(0, 100, 200, 100));
        assertEquals("Color.decode(\"#0064c8\")", result);
    }

}
