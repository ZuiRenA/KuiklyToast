package com.example.myapplication

import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.base.*
import com.tencent.kuikly.core.views.*
import com.tencent.kuikly.core.views.compose.Button
import com.example.myapplication.base.BasePager
import com.tencent.kuiklybase.toast.ToastModule
import com.tencent.kuiklybase.toast.showToast
import com.tencent.kuiklybase.toast.toastModule

@Page("router", supportInLocal = true)
internal class ToastDemoPage : BasePager() {

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            attr {
                backgroundColor(Color.WHITE)
            }

            View {
                attr {
                    flex(1f)
                    padding(20f)
                }

                Text {
                    attr {
                        text("KuiklyToast Demo")
                        fontSize(24f)
                        fontWeightBold()
                        color(Color(0xFF333333))
                        marginBottom(30f)
                    }
                }

                DemoButton("基础 Toast") {
                    ctx.showToast("Hello KuiklyToast!")
                }

                DemoButton("长时间 Toast") {
                    ctx.showToast("这是一个长时间显示的 Toast", ToastModule.DURATION_LONG)
                }

                DemoButton("顶部 Toast") {
                    ctx.showToast(
                        text = "顶部显示",
                        gravity = ToastModule.GRAVITY_TOP,
                        offsetY = 100
                    )
                }

                DemoButton("居中 Toast") {
                    ctx.showToast(
                        text = "居中显示",
                        gravity = ToastModule.GRAVITY_CENTER
                    )
                }

                DemoButton("白色背景 Toast") {
                    ctx.showToast(
                        text = "白色背景样式",
                        backgroundColor = "#FFFFFF",
                        textColor = "#333333",
                        borderRadius = 12
                    )
                }

                DemoButton("自定义样式 Toast") {
                    ctx.showToast(
                        text = "自定义样式",
                        gravity = ToastModule.GRAVITY_CENTER,
                        backgroundColor = "#4F8FFF",
                        textColor = "#FFFFFF",
                        fontSize = 16,
                        borderRadius = 20
                    )
                }

                DemoButton("Builder 模式") {
                    ctx.toastModule
                        .setText("Builder 模式调用")
                        .setDuration(ToastModule.DURATION_SHORT)
                        .setGravity(ToastModule.GRAVITY_CENTER)
                        .setBackgroundColor("#FF4444")
                        .setTextColor("#FFFFFF")
                        .setBorderRadius(16)
                        .showToast()
                }
            }
        }
    }
}

private fun ViewContainer<*, *>.DemoButton(title: String, onClick: () -> Unit) {
    Button {
        attr {
            width(pagerData.pageViewWidth - 40f)
            height(48f)
            borderRadius(8f)
            marginBottom(16f)
            backgroundColor(Color(0xFF4F8FFF))
            titleAttr {
                text(title)
                fontSize(16f)
                color(Color.WHITE)
            }
        }
        event {
            click { onClick() }
        }
    }
}
