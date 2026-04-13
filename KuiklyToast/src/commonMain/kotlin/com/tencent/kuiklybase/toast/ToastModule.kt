package com.tencent.kuiklybase.toast

import com.tencent.kuikly.core.module.Module
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

class ToastModule : Module() {

    override fun moduleName(): String = MODULE_NAME

    private var text: String? = null
    private var duration: Int = DURATION_SHORT
    private var gravity: Int = GRAVITY_BOTTOM
    private var offsetX: Int = 0
    private var offsetY: Int = 0
    private var image: String? = null
    private var backgroundColor: String? = null
    private var textColor: String? = null
    private var fontSize: Int = 0
    private var borderRadius: Int = 0

    fun setText(text: String?): ToastModule {
        this.text = text
        return this
    }

    fun setDuration(duration: Int): ToastModule {
        this.duration = duration
        return this
    }

    fun setGravity(gravity: Int): ToastModule {
        this.gravity = gravity
        return this
    }

    fun setOffsetX(offsetX: Int): ToastModule {
        this.offsetX = offsetX
        return this
    }

    fun setOffsetY(offsetY: Int): ToastModule {
        this.offsetY = offsetY
        return this
    }

    fun setImage(image: String): ToastModule {
        this.image = image
        return this
    }

    fun setBackgroundColor(color: String): ToastModule {
        this.backgroundColor = color
        return this
    }

    fun setTextColor(color: String): ToastModule {
        this.textColor = color
        return this
    }

    fun setFontSize(size: Int): ToastModule {
        this.fontSize = size
        return this
    }

    fun setBorderRadius(radius: Int): ToastModule {
        this.borderRadius = radius
        return this
    }

    fun showToast() {
        val params = JSONObject().apply {
            text?.let { put("text", it) }
            put("duration", duration)
            put("gravity", gravity)
            put("offsetX", offsetX)
            put("offsetY", offsetY)
            image?.let { put("image", it) }
            backgroundColor?.let { put("backgroundColor", it) }
            textColor?.let { put("textColor", it) }
            if (fontSize > 0) put("fontSize", fontSize)
            if (borderRadius > 0) put("borderRadius", borderRadius)
        }
        toNative(false, METHOD_SHOW_TOAST, params.toString(), null, false)
        reset()
    }

    fun log(content: String) {
        val params = JSONObject().apply {
            put("content", content)
        }
        toNative(false, METHOD_LOG, params.toString(), null, false)
    }

    private fun reset() {
        text = null
        duration = DURATION_SHORT
        gravity = GRAVITY_BOTTOM
        offsetX = 0
        offsetY = 0
        image = null
        backgroundColor = null
        textColor = null
        fontSize = 0
        borderRadius = 0
    }

    companion object {
        const val MODULE_NAME = "KRToastModule"
        const val METHOD_SHOW_TOAST = "showToast"
        const val METHOD_LOG = "log"

        const val DURATION_SHORT = 0
        const val DURATION_LONG = 1

        const val GRAVITY_TOP = 48
        const val GRAVITY_CENTER = 17
        const val GRAVITY_BOTTOM = 80
    }
}
