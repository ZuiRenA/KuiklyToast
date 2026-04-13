# KuiklyToast

[![GitHub](https://img.shields.io/badge/GitHub-KuiklyToast-blue)](https://github.com/Kuikly-contrib/KuiklyToast)

Kuikly 跨平台 Toast 组件，支持 Android / iOS / 鸿蒙三端。


## 依赖引入

### KMP 层

```kotlin
// build.gradle.kts（Android / iOS ）
implementation("com.tencent.kuiklybase:KuiklyToast:1.0.0-2.0.21")

// build.ohos.gradle.kts（鸿蒙）
implementation("com.tencent.kuiklybase:KuiklyToast:1.0.0-2.0.21-KBA-010")
```

### Android 原生端

```kotlin
implementation("com.tencent.kuiklybase:KuiklyToastAndroid:1.0.0-2.0.21")
```

### iOS 原生端

```ruby
# Podfile
pod 'KuiklyToastIOS', :git => 'https://github.com/Kuikly-contrib/KuiklyToast.git', :branch => 'main'
```

### 鸿蒙原生端

```json5
{
  "dependencies": {
    "@yuki8273/kuikly-toast": "1.0.0"
  }
}
```

同时在 `ohosApp/build-profile.json5` 的 modules 中添加：

```json5
{ "name": "KuiklyToastOhos", "srcPath": "../KuiklyToastOhos" }
```

## 三端 Module 注册

### Android

在 `KuiklyRenderActivity` 中注册：

```kotlin
import com.tencent.kuiklybase.toast.KRToastModule

override fun registerExternalModule(kuiklyRenderExport: IKuiklyRenderExport) {
    super.registerExternalModule(kuiklyRenderExport)
    with(kuiklyRenderExport) {
        moduleExport(KRToastModule.MODULE_NAME) {
            KRToastModule()
        }
    }
}
```

### iOS

**无需手动注册。** 框架通过 `NSClassFromString("KRToastModule")` 自动发现，只需确保 `KuiklyToastIOS` pod 被引入编译即可。

### 鸿蒙

在 `KuiklyViewDelegate.ets` 中注册：

```typescript
import { KRToastModule } from 'KuiklyToastOhos';

getCustomRenderModuleCreatorRegisterMap(): Map<string, KRRenderModuleExportCreator> {
    const map: Map<string, KRRenderModuleExportCreator> = new Map();
    map.set(KRToastModule.MODULE_NAME, () => new KRToastModule());
    return map;
}
```

## KMP 层注册

在 `BasePager.createExternalModules()` 中注册：

```kotlin
import com.tencent.kuiklybase.toast.ToastModule

externalModules[ToastModule.MODULE_NAME] = ToastModule()
```

## API 用法

### 快捷调用

```kotlin
import com.tencent.kuiklybase.toast.showToast

// 基础 Toast
showToast("Hello!")

// 指定时长
showToast("长时间显示", ToastModule.DURATION_LONG)
```

### 完整参数调用

```kotlin
showToast(
    text = "顶部显示",
    gravity = ToastModule.GRAVITY_TOP,
    offsetY = 100,
    backgroundColor = "#FFFFFF",
    textColor = "#333333",
    fontSize = 16,
    borderRadius = 12
)
```

### Builder 链式调用

```kotlin
import com.tencent.kuiklybase.toast.toastModule

toastModule
    .setText("自定义样式")
    .setDuration(ToastModule.DURATION_SHORT)
    .setGravity(ToastModule.GRAVITY_CENTER)
    .setBackgroundColor("#4F8FFF")
    .setTextColor("#FFFFFF")
    .setFontSize(16)
    .setBorderRadius(20)
    .showToast()
```

### 参数说明

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| text | String | - | Toast 文本内容 |
| duration | Int | DURATION_SHORT | 显示时长：`DURATION_SHORT`(0) / `DURATION_LONG`(1) |
| gravity | Int | GRAVITY_BOTTOM | 位置：`GRAVITY_TOP`(48) / `GRAVITY_CENTER`(17) / `GRAVITY_BOTTOM`(80) |
| offsetX | Int | 0 | 水平偏移量 |
| offsetY | Int | 0 | 垂直偏移量 |
| backgroundColor | String | 默认黑色半透明 | 背景色，hex 格式如 `"#FFFFFF"` |
| textColor | String | 白色 | 文字颜色，hex 格式如 `"#333333"` |
| fontSize | Int | 14 | 字号 (sp) |
| borderRadius | Int | 8 | 圆角 (dp) |

