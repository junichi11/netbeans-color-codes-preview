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
package com.junichi11.netbeans.modules.color.codes.preview.utils;

import com.junichi11.netbeans.modules.color.codes.preview.colors.model.ColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.colors.model.impl.DefaultColorCodesProvider;
import java.awt.Color;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author junichi11
 */
public class ColorsUtilsTest {

    public ColorsUtilsTest() {
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

    // REGEX
    /**
     * Test of PERCENT_VALUE_FORMAT regex, of class ColorsUtils.
     */
    @Test
    public void testPercentValueRegex() {
        Pattern pattern = Pattern.compile(ColorType.PERCENT_VALUE_FORMAT);
        for (int i = 0; i < 100; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(i) + "%");
            Assert.assertTrue(matcher.matches());
        }

        Matcher matcher = pattern.matcher("-1%");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("101%");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("0");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("test");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("%");
        Assert.assertFalse(matcher.matches());
    }

    /**
     * Test of INT_RGB_VALUE_FORMAT regex, of class ColorsUtils.
     */
    @Test
    public void testCssIntRGBValueRegex() {
        Pattern pattern = Pattern.compile(String.format("(%s)", ColorType.INT_RGB_VALUE_FORMAT));
        for (int i = 0; i < 256; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(i));
            Assert.assertTrue(matcher.matches());
        }

        Matcher matcher = pattern.matcher("-1");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("256");
        Assert.assertFalse(matcher.matches());
    }

    /**
     * Test of ALPHA_VALUE_FORMAT regex, of class ColorsUtils.
     */
    @Test
    public void testAlphaValueRegex() {
        Pattern pattern = Pattern.compile(String.format("(%s)", ColorType.ALPHA_VALUE_FORMAT));
        // 0.1-0.9
        for (int i = 1; i < 10; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("0.").append(i);
            Matcher matcher = pattern.matcher(sb.toString());
            Assert.assertTrue(matcher.matches());
        }

        for (int i = 1; i < 10; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(".").append(i);
            Matcher matcher = pattern.matcher(sb.toString());
            Assert.assertTrue(matcher.matches());
        }

        // 0.01-0.99 without 0.10-0.90
        for (int i = 0; i < 10; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("0.").append(i);
            for (int j = 1; j < 10; j++) {
                Matcher matcher = pattern.matcher(sb.toString() + j);
                Assert.assertTrue(matcher.matches());
            }
        }

        for (int i = 0; i < 10; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(".").append(i);
            for (int j = 1; j < 10; j++) {
                Matcher matcher = pattern.matcher(sb.toString() + j);
                Assert.assertTrue(matcher.matches());
            }
        }

        Matcher matcher = pattern.matcher("1");
        Assert.assertTrue(matcher.matches());
        matcher = pattern.matcher("0");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("0.0");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("0.00");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("0.90");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("-1");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("1.0");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("1.1");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher(".0");
        Assert.assertFalse(matcher.matches());
    }

    /**
     * Test of INT_RGB_VALUE_FORMAT regex, of class ColorsUtils.
     */
    @Test
    public void testHueValueRegex() {
        Pattern pattern = Pattern.compile(ColorType.HUE_VALUE_FORMAT);
        for (int i = 0; i <= 360; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(i));
            Assert.assertTrue(matcher.matches());
        }

        Matcher matcher = pattern.matcher("-1");
        Assert.assertFalse(matcher.matches());
        matcher = pattern.matcher("361");
        Assert.assertFalse(matcher.matches());
    }

    // decode
    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeHex() {
        Color result = ColorsUtils.decode("#000000");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("#000");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("hex#ffffffhex");
        Assert.assertNull(result);
        result = ColorsUtils.decode("#1");
        Assert.assertNull(result);
        result = ColorsUtils.decode("#22");
        Assert.assertNull(result);
        result = ColorsUtils.decode("#4444");
        Assert.assertNull(result);
        result = ColorsUtils.decode("#55555");
        Assert.assertNull(result);
        result = ColorsUtils.decode("#7777777");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssIntRGB() {
        Color result = ColorsUtils.decode("rgb(0, 0, 0)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgb(100, 100, 100)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgb(255,255,255)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("rgb(-1, 0, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0, -1, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0, 0, -1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(256, 0, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0, 256, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(255,255,256)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(a,0,0)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssPercentRGB() {
        Color result = ColorsUtils.decode("rgb(0%, 0%, 0%)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgb(50%, 50%, 50%)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgb(100%,100%,100%)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("rgb(-1%, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0%, -1%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0%, 0%, -1%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(101%, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0%, 101%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(100%,100%,101%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(a,0%,0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0,0%,0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0%,100,0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgb(0%,100%,50)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssIntRGBA() {
        Color result = ColorsUtils.decode("rgba(0, 0, 0, 0)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(100, 100, 100, 0.5)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(255,255,255, 1)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(120,120,120, .9)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("rgba(0, 0, 0, 0.0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, 0, 0.50)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, 0, .50)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, 0, 1.0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, 0, -1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, 0, 1.1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(-1, 0, 0, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, -1, 0, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 0, -1, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(256, 0, 0, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0, 256, 0, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(255,255,256, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(a,0,0, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0,0,0, a)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("test rgba(0,0,0, 1)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssPercentRGBA() {
        Color result = ColorsUtils.decode("rgba(0%, 0%, 0%, 0)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(50%, 50%, 50%, 0.5)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(50%, 50%, 50%, .5)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("rgba(100%,100%,100%, 1)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("rgba(0%, 0%, 0%, 0.0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, 0%, 0.50)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, 0%, .50)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, 0%, 1.0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, 0%, -1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, 0%, 1.1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(-1%, 0%, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, -1%, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 0%, -1%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(101%, 0%, 0%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%, 101%, 0%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(100%,0%,101%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(a,0%,0%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("rgba(0%,0%,0%, a)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("test rgba(0%,0%,0%, 1)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssHSL() {
        Color result = ColorsUtils.decode("hsl(0, 0%, 0%)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("hsl(180, 50%, 50%)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("hsl(360,100%,100%)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("hsl(-1, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(361, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, -1%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 101%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 0%, -1%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 0%, 101%)");
        Assert.assertNull(result);

        result = ColorsUtils.decode("hsl(100%, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 0, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsl(0, 0%, 100%, 0.1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("test hsl(0, 100%, 50%)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeCssHSLA() {
        Color result = ColorsUtils.decode("hsla(0, 0%, 0%, 0)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("hsla(180, 50%, 50%, 0.5)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("hsla(180, 50%, 50%, .5)");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode("hsla(360,100%,100%, 1)");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("hsla(360,100%,100%, .10)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(-1, 0%, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(361, 0%, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, -1%, 0%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 101%, 0%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, -1%), 1");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, 101%, 1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, 100%, -0.1)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, 100%, 1.1)");
        Assert.assertNull(result);

        result = ColorsUtils.decode("hsla(100%, 0%, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0, 0%, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, 0, 0)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("hsla(0, 0%, 0%, 0%)");
        Assert.assertNull(result);
        result = ColorsUtils.decode("test hsla(0, 100%, 50%, 0.5)");
        Assert.assertNull(result);
    }

    /**
     * Test of decode method, of class ColorsUtils.
     */
    @Test
    public void testDecodeNamedColor() {
        Color result = ColorsUtils.decode(" black ");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(" RED ");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(":RED;");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(" RED;");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(":RED ");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(",RED ");
        Assert.assertNotNull(result);
        result = ColorsUtils.decode(" RED\"");
        Assert.assertNotNull(result);

        result = ColorsUtils.decode("test invalid");
        Assert.assertNull(result);
    }

    // Colors
    /**
     * Test of getHexColorCodes method, of class ColorsUtils.
     */
    @Test
    public void testGetHexColorCodesStrings() {
        List<String> result = ColorsUtils.getHexColorCodes("#000000");
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes("#000");
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes("hex#ffffffhex");
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes("hex#ffffff#000");
        Assert.assertEquals(2, result.size());

        result = ColorsUtils.getHexColorCodes("hex #ffffff #000 #1");
        Assert.assertEquals(2, result.size());

        result = ColorsUtils.getHexColorCodes("#1");
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes("#22");
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes("#4444");
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes("#55555");
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes("#7777777");
        Assert.assertEquals(0, result.size());
    }

    /**
     * Test of getHexColorCodes method, of class ColorsUtils.
     */
    @Test
    public void testGetHexColorCodesColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getHexColorCodes(provider, "#000000", 1);
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes(provider, "#000", 1);
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes(provider, "hex#ffffffhex", 1);
        Assert.assertEquals(1, result.size());

        result = ColorsUtils.getHexColorCodes(provider, "hex#ffffff#000", 1);
        Assert.assertEquals(2, result.size());

        result = ColorsUtils.getHexColorCodes(provider, "hex #ffffff #000 #1", 1);
        Assert.assertEquals(2, result.size());

        result = ColorsUtils.getHexColorCodes(provider, "#1", 1);
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes(provider, "#22", 1);
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes(provider, "#4444", 1);
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes(provider, "#55555", 1);
        Assert.assertEquals(0, result.size());
        result = ColorsUtils.getHexColorCodes(provider, "#7777777", 1);
        Assert.assertEquals(0, result.size());
    }

    /**
     * Test of getCssIntRGBs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssIntRGBStrings() {
        List<String> result = ColorsUtils.getCssIntRGBs("rgb(0, 0, 0)");
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBs("rgb(100 , 100 , 100)");
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBs("rgb(255, 255, 255)");
        assertEquals(1, result.size());

        result = ColorsUtils.getCssIntRGBs("rgb(255, 255, 255) rgb(0,0,0)");
        assertEquals(2, result.size());

        result = ColorsUtils.getCssIntRGBs("test");
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBs("rgb(-1,255,255)");
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssIntRGBs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssIntRGBColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssIntRGBs(provider, "rgb(0, 0, 0)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBs(provider, "rgb(100 , 100 , 100)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBs(provider, "rgb(255, 255, 255)", 1);
        assertEquals(1, result.size());

        result = ColorsUtils.getCssIntRGBs(provider, "rgb(255, 255, 255) rgb(0,0,0)", 1);
        assertEquals(2, result.size());

        result = ColorsUtils.getCssIntRGBs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBs(provider, "rgb(-1, 255, 255)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssPercentRGBs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssPercentRGBColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssPercentRGBs(provider, "rgb(0%, 0%, 0%)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssPercentRGBs(provider, "rgb(50% , 50% , 50%)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssPercentRGBs(provider, "rgb(100%, 100%, 100%)", 1);
        assertEquals(1, result.size());

        result = ColorsUtils.getCssPercentRGBs(provider, "rgb(100%, 100%, 100%) rgb(0%,0%,0%)", 1);
        assertEquals(2, result.size());

        result = ColorsUtils.getCssPercentRGBs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBs(provider, "rgb(-1%, 100%, 100%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBs(provider, "rgb(100, 100, 100)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBs(provider, "rgba(100%, 100%, 100%, 1)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssIntRGBAs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssIntRGBAStrings() {
        List<String> result = ColorsUtils.getCssIntRGBAs("rgba(0, 0, 0, 0)");
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBAs("rgba(100 , 100 , 100 , 0.5)");
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBAs("rgba(255, 255, 255, 1)");
        assertEquals(1, result.size());

        result = ColorsUtils.getCssIntRGBAs("rgba(255, 255, 255, 0.1) rgba(0,0,0, 0.8)");
        assertEquals(2, result.size());

        result = ColorsUtils.getCssIntRGBAs("rgba(255,255,255,1) test rgba(0,0,0, -1)");
        assertEquals(1, result.size());

        result = ColorsUtils.getCssIntRGBAs("test");
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs("rgba(-1, 255, 255, 1)");
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs("rgba(0, 0, 0, 1.5)");
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs("rgba(0, 0, 0, -1)");
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssIntRGBAs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssIntRGBAColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssIntRGBAs(provider, "rgba(0, 0, 0, 0)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(100, 100, 100, 0.5)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(255, 255, 255, 1)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(255, 255, 255, 1)", 1);
        assertEquals(1, result.size());

        // multiple values
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(255, 255, 255, 0.1) rgba(0,0,0, 0.8)", 1);
        assertEquals(2, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(255, 255, 255, 1) test rgba(0,0,0, -1)", 1);
        assertEquals(1, result.size());

        // no colors
        result = ColorsUtils.getCssIntRGBAs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(-1, 255, 255, 1)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(0, 0, 0, 1.5)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(0, 0, 0, -1)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(0, 0, 0, 0.0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssIntRGBAs(provider, "rgba(0, 0, 0, 0.50)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssPercentRGBs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssPercentRGBAColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0%, 0%, 0%, 0)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(100%, 100%, 100%, 0.5)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(100%, 100%, 100%, 1)", 1);
        assertEquals(1, result.size());

        // multiple values
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(100%, 100%, 100%, 0.1) rgba(0%,0%,0%, 0.8)", 1);
        assertEquals(2, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(100%, 100%, 100%, 1) test rgba(0%,0%,0%, -1)", 1);
        assertEquals(1, result.size());

        // no colors
        result = ColorsUtils.getCssPercentRGBAs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(-1%, 100%, 100%, 1)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0%, 0%, 0%, 1.5)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0%, 0%, 0%, -1)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0%, 0%, 0%, 0.0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0%, 0%, 0%, 0.50)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgba(0, 0, 0, 0.50)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssPercentRGBAs(provider, "rgb(0, 0, 0)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssHSLs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssHSLColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssHSLs(provider, "hsl(0, 0%, 0%)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(180, 50%, 50%)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(360, 100%, 100%)", 1);
        assertEquals(1, result.size());

        // multiple values
        result = ColorsUtils.getCssHSLs(provider, "hsl(0, 100%, 100%) hsl(0,0%,0%)", 1);
        assertEquals(2, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(-1, 100%, 100%) test hsl(0,0%,0%)", 1);
        assertEquals(1, result.size());

        // no colors
        result = ColorsUtils.getCssHSLs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(-1, 100%, 100%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(361, 100%, 100%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(0, -1%, 0%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(0, 101%, 0%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(0, 0%, -1%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsl(0, 0%, 101%)", 1);
        assertEquals(0, result.size());

        result = ColorsUtils.getCssHSLs(provider, "hsl(0, 0%, 0%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLs(provider, "hsla(0, 0%, 0%, 0)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getCssHSLAs method, of class ColorsUtils.
     */
    @Test
    public void testGetCssHSLAColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, 0%, 0)", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(180, 50%, 50%, 0.5)", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(360, 100%, 100%, 1)", 1);
        assertEquals(1, result.size());

        // multiple values
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 100%, 100%, 1) hsla(0,0%,0%, 0.8)", 1);
        assertEquals(2, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(180, 100%, 100%, -1) test hsla(0,0%,0%, 0)", 1);
        assertEquals(1, result.size());

        // no colors
        result = ColorsUtils.getCssHSLAs(provider, "test", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(-1, 100%, 100%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(361, 100%, 100%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, -1%, 0%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 101%, 0%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, -1%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, 101%, 0)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, 100%, -0.1)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, 100%, 1.1)", 1);
        assertEquals(0, result.size());

        result = ColorsUtils.getCssHSLAs(provider, "hsla(0, 0%, 0%)", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getCssHSLAs(provider, "hsl(0, 0%, 0%)", 1);
        assertEquals(0, result.size());
    }

    /**
     * Test of getNamedColors method, of class ColorsUtils.
     */
    @Test
    public void testGetNamedColorValues() {
        DefaultColorCodesProvider provider = new DefaultColorCodesProvider();
        List<ColorValue> result = ColorsUtils.getNamedColors(provider, " black ", -1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " BLACK ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " yellow ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " green ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " yellowgreen ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " greenyellow ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " orangered ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " red ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " orange ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " mediumslateblue ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " blue ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " mediumblue ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " aquamarine ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " aqua ", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, " aqua\"", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, ":red;", 1);
        assertEquals(1, result.size());
        result = ColorsUtils.getNamedColors(provider, ": red;", 1);
        assertEquals(1, result.size());

        result = ColorsUtils.getNamedColors(provider, " green, yellow ", 1);
        assertEquals(2, result.size());

        result = ColorsUtils.getNamedColors(provider, "invalidName", 1);
        assertEquals(0, result.size());
        result = ColorsUtils.getNamedColors(provider, "white-space", 1);
        assertEquals(0, result.size());
    }
}
