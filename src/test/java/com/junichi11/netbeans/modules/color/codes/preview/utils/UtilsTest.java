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
package com.junichi11.netbeans.modules.color.codes.preview.utils;

import com.junichi11.netbeans.modules.color.codes.preview.api.OffsetRange;
import com.junichi11.netbeans.modules.color.codes.preview.spi.AbstractColorValue;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorCodeFormatter;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
public class UtilsTest {

    public UtilsTest() {
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
     * Test of getForeground method, of class Utils.
     */
    @Test
    public void testGetForeground() {
        assertEquals(Color.WHITE, Utils.getForeground(Color.BLACK));
        assertEquals(Color.BLACK, Utils.getForeground(Color.WHITE));
    }

    /**
     * Test of sort method, of class Utils.
     */
    @Test
    public void testSort() {
        List<ColorValue> colorValues = new ArrayList<>();
        colorValues.add(new ColorValueImpl("test", new OffsetRange(3, 5), 1)); // 2
        colorValues.add(new ColorValueImpl("test", new OffsetRange(11, 22), 13)); // 7
        colorValues.add(new ColorValueImpl("test", new OffsetRange(1, 2), 1)); // 1
        colorValues.add(new ColorValueImpl("test", new OffsetRange(1, 2), 13)); // 6
        colorValues.add(new ColorValueImpl("test", new OffsetRange(1, 2), 10)); //5
        colorValues.add(new ColorValueImpl("test", new OffsetRange(11, 23), 1)); // 4
        colorValues.add(new ColorValueImpl("test", new OffsetRange(8, 10), 1)); // 3
        Utils.sort(colorValues);
        assertEquals(1, colorValues.get(0).getLine());
        assertEquals(1, colorValues.get(0).getStartOffset());
        assertEquals(2, colorValues.get(0).getEndOffset());

        assertEquals(1, colorValues.get(1).getLine());
        assertEquals(3, colorValues.get(1).getStartOffset());
        assertEquals(5, colorValues.get(1).getEndOffset());

        assertEquals(1, colorValues.get(2).getLine());
        assertEquals(8, colorValues.get(2).getStartOffset());
        assertEquals(10, colorValues.get(2).getEndOffset());

        assertEquals(1, colorValues.get(3).getLine());
        assertEquals(11, colorValues.get(3).getStartOffset());
        assertEquals(23, colorValues.get(3).getEndOffset());

        assertEquals(10, colorValues.get(4).getLine());
        assertEquals(1, colorValues.get(4).getStartOffset());
        assertEquals(2, colorValues.get(4).getEndOffset());

        assertEquals(13, colorValues.get(5).getLine());
        assertEquals(1, colorValues.get(5).getStartOffset());
        assertEquals(2, colorValues.get(5).getEndOffset());

        assertEquals(13, colorValues.get(6).getLine());
        assertEquals(11, colorValues.get(6).getStartOffset());
        assertEquals(22, colorValues.get(6).getEndOffset());
    }

    private static class ColorValueImpl extends AbstractColorValue {

        public ColorValueImpl(String value, OffsetRange offsetRange, int line) {
            super(value, offsetRange, line);
        }

        @Override
        public Color getColor() {
            return Color.BLACK;
        }

        @Override
        public boolean isEditable() {
            return false;
        }

        @Override
        public ColorCodeFormatter getFormatter() {
            return null;
        }

    }

}
