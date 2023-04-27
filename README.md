# AZ-EditText
Customizable EditText for Android

[![](https://jitpack.io/v/omarzer0/az-edittext.svg)](https://jitpack.io/#omarzer0/az-edittext)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


## Preview

## Setup

#### Add this in your root build.gradle file
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
#### Add this to your module's build.gradle file
```gradle
dependencies {
      implementation 'com.github.omarzer0:az-edittext:0.1.2'
}
```

## Usage
* 

```xml
  
```

## Properties
#### use "app:" before custom attr's name

|Xml Attribute|Type|In Kotlin Code|Description|
|:---:|:---:|:---:|:---:|
|text|String|`text`|Text of the EditText|
|hint|String|`hint`|Hint to display when text is empty|
|errorText|String|`errotText`|Error text displayed under the EditText. If this attr is not null or blank the EditText will be in the error state (border will change to the errorStrokeColor)|
|errorTextColor|Color|`errorTextColor`|Color of errorText|
|startText|String|`startText`|Text displayed at the start of EditText before text and after startDrawable if exists|
|startDrawable|Reference|`startDrawable`|The drawable to be drawn to the start of the EditText before startText|
|endDrawable|Reference|`endDrawable`|The drawable to be drawn to the start of the EditText after text|
|maxLenght|Integer|`maxLenght`|Set an input filter to constrain the text length to the specified number. Default is MAX_VALUE for int (2147483647)
|inputType|Integer|`inputType`|The type of data being placed in a text field|
|handlePassword|Boolean|`handlePassword`|When set to true it and inputType is one of the password input types (ex: textPassword) it handles showing and hiding password probably and showes a proper eye_closed/opened drawable|
|passwordShownDrawable|Reference|`passwordShownDrawable`|Drawable to show when password is visible (handle password has to be true and inputType is one of the password input types ex: textPassword)|
|textSize|Dimension|`textSize`|Size of the text|
|isEdEnabled|Boolean|`isEdEnabled`|Specifies whether the EditText is enabled|
|activeStrokeColor|Color|`activeStrokeColor`|Color of the border when EditText is active (text is not empty or it is focused)|
|inactiveStrokeColor|Color|`inactiveStrokeColor`|Color of the border when EditText is inactive (text empty and it is not focused)|
|errorStrokeColor|Color|`errorStrokeColor`|Color of the border when EditText is in error state (when errorText is not null or blank)|
|containerBackgroundColor|Color|`containerBackgroundColor`|Background color for the EditText|
|containerRadius|Dimension|`containerRadius`|Raduis for the border of the EditText|
|strokeWidth|Dimension|`strokeWidth`|Storke Width for the border of the EditText|
|edTextHeight|Dimension|`edTextHeight`|Sets a height to the text area only (android:height sets the height of the whole view)|
|textGravity|Integer|`textGravity`|Specifies how to align the text by the EditText's x- and/or y-axis when the text is smaller than the EditText|

## Social media
[![](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/omar1adel)
[![](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/omarzer0)
[![](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/@devOmarAZ)


## License

```
Copyright 2023 Omar Adel

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


