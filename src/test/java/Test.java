
import com.junichi11.netbeans.modules.color.codes.preview.impl.utils.ColorsUtils;
import com.junichi11.netbeans.modules.color.codes.preview.spi.ColorValue;
import java.util.List;

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

/**
 *
 * @author INCH Team
 */
public class Test {
    
    public static void main(String[] args) {
        ColorsUtils.getJavaIntRGBHexColors("new Color(0xDFDFDF);", 0);
        ColorsUtils.getJavaIntRGBAHexColors("new Color(0xDFDFDF, true);", 0);
        ColorsUtils.getJavaFloatRGBColors("new Color(0.1f, 1f, 0f);", 0);
        ColorsUtils.getJavaFloatRGBAColors("new Color(3.0f, 3f, 0.3f);", 0);
        int i = Integer.parseInt("3BAFFF", 16);
    }
    
}
