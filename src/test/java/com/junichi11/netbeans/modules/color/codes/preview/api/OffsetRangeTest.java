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
package com.junichi11.netbeans.modules.color.codes.preview.api;

import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author junichi11
 */
public class OffsetRangeTest {

    public OffsetRangeTest() {
    }

    @Test
    public void testOffsetRange() {
        try {
            OffsetRange offsetRange = new OffsetRange(20, 20);
        } catch (IllegalArgumentException ex) {
            fail("IllegalArgumentException must not occur.");
        }

        try {
            OffsetRange offsetRange = new OffsetRange(20, 21);
        } catch (IllegalArgumentException ex) {
            fail("IllegalArgumentException must not occur.");
        }

        try {
            OffsetRange offsetRange = new OffsetRange(20, 0);
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException must occur.");
    }
}
