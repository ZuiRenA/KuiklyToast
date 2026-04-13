package com.tencent.kuiklybase.toast

import com.tencent.kuikly.core.pager.Pager

val Pager.toastModule: ToastModule
    get() = acquireModule(ToastModule.MODULE_NAME)

fun Pager.showToast(text: String, duration: Int = ToastModule.DURATION_SHORT) {
    toastModule.setText(text).setDuration(duration).showToast()
}

fun Pager.showToast(
    text: String,
    duration: Int = ToastModule.DURATION_SHORT,
    gravity: Int = ToastModule.GRAVITY_BOTTOM,
    offsetX: Int = 0,
    offsetY: Int = 0,
    image: String? = null,
    backgroundColor: String? = null,
    textColor: String? = null,
    fontSize: Int = 0,
    borderRadius: Int = 0
) {
    toastModule
        .setText(text)
        .setDuration(duration)
        .setGravity(gravity)
        .setOffsetX(offsetX)
        .setOffsetY(offsetY)
        .apply {
            image?.let { setImage(it) }
            backgroundColor?.let { setBackgroundColor(it) }
            textColor?.let { setTextColor(it) }
            if (fontSize > 0) setFontSize(fontSize)
            if (borderRadius > 0) setBorderRadius(borderRadius)
        }
        .showToast()
}
