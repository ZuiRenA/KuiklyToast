# @yuki8273/kuikly-toast

Kuikly 跨平台 Toast 组件的鸿蒙端原生 Module 实现。

项目地址：https://github.com/Kuikly-contrib/KuiklyToast

## 安装

```bash
ohpm install @yuki8273/kuikly-toast
```

## 使用

### 1. 在 KuiklyViewDelegate 中注册 Module

```typescript
import { KRToastModule } from '@yuki8273/kuikly-toast';

getCustomRenderModuleCreatorRegisterMap(): Map<string, KRRenderModuleExportCreator> {
    const map: Map<string, KRRenderModuleExportCreator> = new Map();
    map.set(KRToastModule.MODULE_NAME, () => new KRToastModule());
    return map;
}
```

### 2. KMP 层调用

```kotlin
import com.tencent.kuiklybase.toast.showToast

// 基础 Toast
showToast("Hello!")

// 指定位置
showToast(text = "顶部显示", gravity = ToastModule.GRAVITY_TOP, offsetY = 100)
```

## 支持的参数

| 参数 | 类型 | 说明 |
|------|------|------|
| text | String | Toast 文本 |
| duration | Int | 时长：DURATION_SHORT(0) / DURATION_LONG(1) |
| gravity | Int | 位置：GRAVITY_TOP(48) / GRAVITY_CENTER(17) / GRAVITY_BOTTOM(80) |
| offsetX | Int | 水平偏移 |
| offsetY | Int | 垂直偏移 |

## 依赖

- `@kuikly-open/render` >= 2.7.0

## License

MIT
