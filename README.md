# KuiklyToast

Kuikly 跨平台 Toast 通知组件，支持 Android / iOS / 鸿蒙三端。

## 功能

- 设置 Toast 文案
- 设置显示时长（SHORT / LONG）
- 设置显示位置（TOP / CENTER / BOTTOM）
- 设置偏移量（offsetX / offsetY）
- 设置显示图片（预留）
- Builder 链式调用

## 项目结构

```
KuiklyToast/
├── KuiklyToast/                    # KMP 组件模块（commonMain）
│   └── src/commonMain/kotlin/com/tencent/kuiklybase/toast/
│       ├── ToastModule.kt          # 核心 Module，通过 callNative 通信
│       └── ToastExtensions.kt      # Pager 扩展函数
├── KuiklyToastAndroid/             # Android 原生 Module
│   └── src/main/java/.../KRToastModule.kt
├── KuiklyToastIOS/                 # iOS 原生 Module
│   ├── KRToastModule.h
│   └── KRToastModule.m
├── KuiklyToastOhos/                # 鸿蒙原生 Module
│   └── src/main/ets/KRToastModule.ets
├── shared/                         # Demo 共享模块
├── androidApp/                     # Android Demo 宿主
├── iosApp/                         # iOS Demo 宿主
└── ohosApp/                        # 鸿蒙 Demo 宿主
```

## 使用方式

### 1. KMP 层

在 `BasePager.createExternalModules()` 中注册：

```kotlin
import com.tencent.kuiklybase.toast.ToastModule

externalModules[ToastModule.MODULE_NAME] = ToastModule()
```

在页面中使用：

```kotlin
import com.tencent.kuiklybase.toast.showToast
import com.tencent.kuiklybase.toast.toastModule

// 简单调用
showToast("Hello!")

// 完整参数
showToast(
    text = "顶部显示",
    gravity = ToastModule.GRAVITY_TOP,
    offsetY = 100
)

// Builder 模式
toastModule
    .setText("Builder 模式")
    .setDuration(ToastModule.DURATION_LONG)
    .setGravity(ToastModule.GRAVITY_CENTER)
    .showToast()
```

### 2. 原生端注册

- **Android**: 将 `KRToastModule` 注册到 `KuiklyRenderActivity` 的自定义 Module 列表
- **iOS**: 类名 `KRToastModule` 与 `moduleName()` 一致，框架通过 `NSClassFromString` 自动发现
- **鸿蒙**: 在 ohosApp 中 import 并注册 `KRToastModule`

## 发布

```bash
./publish-maven.sh
```

## Maven 坐标

```
com.tencent.kuiklybase:KuiklyToast:${version}
```
