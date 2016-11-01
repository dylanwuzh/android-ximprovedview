# android\-ximprovedview

对部分系统自带的控件增加了一些属性、方法，更方便使用。

## Gradle

[![](https://www.jitpack.io/v/wuzhendev/android-ximprovedview.svg)](https://www.jitpack.io/#wuzhendev/android-ximprovedview)

``` groovy
repositories {
    maven {
        url "https://www.jitpack.io"
    }
}

dependencies {
    compile 'com.github.wuzhendev:android-ximprovedview:x.y.z'
}
```

## Usage

### 1\. XScrollView

#### 特性：

1. 支持设置最大高度。
2. 支持回弹效果。

#### Attrs：

``` xml
<declare-styleable name="XScrollView">
    <!-- ScrollView 控件的最大高度 -->
    <attr name="android:maxHeight" />

    <!-- 是否支持回弹效果 -->
    <attr name="xsv_bounceEnabled" format="boolean" />

    <!-- 回弹效果可以拖动的最大距离 -->
    <attr name="xsv_bounceMaxScroll" format="dimension|reference" />
</declare-styleable>
```

## Sample

[Sample sources][1]

[Sample APK][2]

## License

```
Copyright 2016 wuzhen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Why X

X-Men, Strong Powerful and I like them

[1]: ./samples
[2]: ./assets/XImprovedView_Samples_v1.0.0.apk
