package com.tencent.kuiklybase.toast

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import org.json.JSONObject

class KRToastModule : KuiklyRenderBaseModule() {

    private val mainHandler = Handler(Looper.getMainLooper())

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
        val gravity = json.optInt("gravity", 80)
        val offsetX = json.optInt("offsetX", 0)
        val offsetY = json.optInt("offsetY", 0)

        val ctx = context ?: return
        val toastDuration = if (duration == 1) Toast.LENGTH_LONG else Toast.LENGTH_SHORT

        mainHandler.post {
            val toast = Toast.makeText(ctx, text, toastDuration)
            toast.setGravity(gravity, offsetX, offsetY)
            toast.show()
        }
    }

    private fun log(params: String?) {
        if (params == null) return
        val json = JSONObject(params)
        val content = json.optString("content", "")
        android.util.Log.d("KRToastModule", content)
    }

    companion object {
        const val MODULE_NAME = "KRToastModule"
    }
}
