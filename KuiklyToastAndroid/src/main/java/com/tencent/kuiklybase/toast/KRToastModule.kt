package com.tencent.kuiklybase.toast

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.TextView
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import org.json.JSONObject

class KRToastModule : KuiklyRenderBaseModule() {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var customToastView: View? = null
    private val dismissRunnable = Runnable { dismissCustomToast() }

    override fun call(method: String, params: String?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "showToast" -> {
                showToast(params)
                null
            }
            "log" -> {
                log(params)
                null
            }
            else -> null
        }
    }

    private fun showToast(params: String?) {
        if (params == null) return
        val json = JSONObject(params)
        val text = json.optString("text", "")
        val duration = json.optInt("duration", 0)
        val gravity = json.optInt("gravity", Gravity.BOTTOM)
        val offsetX = json.optInt("offsetX", 0)
        val offsetY = json.optInt("offsetY", 0)
        val bgColor = json.optString("backgroundColor", "")
        val txtColor = json.optString("textColor", "")
        val fontSize = json.optInt("fontSize", 0)
        val borderRadius = json.optInt("borderRadius", 0)

        val ctx = context ?: return

        mainHandler.post {
            showCustomToast(ctx, text, gravity, offsetX, offsetY, duration, bgColor, txtColor, fontSize, borderRadius)
        }
    }

    private fun showCustomToast(
        ctx: Context,
        text: String,
        gravity: Int,
        offsetX: Int,
        offsetY: Int,
        duration: Int,
        bgColor: String,
        txtColor: String,
        fontSize: Int,
        borderRadius: Int
    ) {
        dismissCustomToast()

        val activity = findActivity(ctx) ?: return
        val decorView = activity.window.decorView as? FrameLayout ?: return

        val actualBgColor = parseColor(bgColor, DEFAULT_BG_COLOR)
        val actualTxtColor = parseColor(txtColor, DEFAULT_TEXT_COLOR)
        val actualFontSize = if (fontSize > 0) fontSize else DEFAULT_FONT_SIZE
        val actualRadius = if (borderRadius > 0) borderRadius else DEFAULT_BORDER_RADIUS

        val tv = TextView(ctx).apply {
            this.text = text
            setTextColor(actualTxtColor)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, actualFontSize.toFloat())
            setPadding(dp(ctx, 20), dp(ctx, 12), dp(ctx, 20), dp(ctx, 12))
            background = GradientDrawable().apply {
                setColor(actualBgColor)
                cornerRadius = dp(ctx, actualRadius).toFloat()
            }
            this.gravity = Gravity.CENTER
            maxWidth = (ctx.resources.displayMetrics.widthPixels * 0.75).toInt()
        }

        val yOffset = if (offsetY != 0) dp(ctx, offsetY) else dp(ctx, 100)

        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            this.gravity = gravity or Gravity.CENTER_HORIZONTAL
            leftMargin = dp(ctx, offsetX)
            when {
                gravity and Gravity.TOP == Gravity.TOP -> topMargin = yOffset
                gravity and Gravity.BOTTOM == Gravity.BOTTOM -> bottomMargin = yOffset
            }
        }

        tv.startAnimation(AlphaAnimation(0f, 1f).apply { this.duration = 200 })
        decorView.addView(tv, lp)
        customToastView = tv

        val delayMs = if (duration == 1) 3500L else 2000L
        mainHandler.removeCallbacks(dismissRunnable)
        mainHandler.postDelayed(dismissRunnable, delayMs)
    }

    private fun dismissCustomToast() {
        customToastView?.let { view ->
            val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300; fillAfter = true }
            view.startAnimation(fadeOut)
            mainHandler.postDelayed({
                (view.parent as? FrameLayout)?.removeView(view)
            }, 300)
            customToastView = null
        }
    }

    private fun log(params: String?) {
        if (params == null) return
        val json = JSONObject(params)
        val content = json.optString("content", "")
        android.util.Log.d("KRToastModule", content)
    }

    override fun onDestroy() {
        mainHandler.removeCallbacks(dismissRunnable)
        customToastView?.let { (it.parent as? FrameLayout)?.removeView(it) }
        customToastView = null
    }

    private fun parseColor(colorStr: String, default: Int): Int {
        if (colorStr.isBlank()) return default
        return try { Color.parseColor(colorStr) } catch (_: Exception) { default }
    }

    companion object {
        const val MODULE_NAME = "KRToastModule"
        private const val DEFAULT_BG_COLOR = 0xBF000000.toInt()
        private const val DEFAULT_TEXT_COLOR = 0xFFFFFFFF.toInt()
        private const val DEFAULT_FONT_SIZE = 14
        private const val DEFAULT_BORDER_RADIUS = 8
    }

    private fun findActivity(ctx: Context): Activity? {
        var c = ctx
        while (c is android.content.ContextWrapper) {
            if (c is Activity) return c
            c = c.baseContext
        }
        return null
    }

    private fun dp(ctx: Context, value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value.toFloat(),
            ctx.resources.displayMetrics
        ).toInt()
    }
}
