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
    image: String? = null
) {
    toastModule
        .setText(text)
        .setDuration(duration)
        .setGravity(gravity)
        .setOffsetX(offsetX)
        .setOffsetY(offsetY)
        .apply { image?.let { setImage(it) } }
        .showToast()
}
