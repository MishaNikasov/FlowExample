package com.app.flowexample.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.app.flowexample.R
import com.app.flowexample.common.extensions.getResourceDrawable
import com.nikasov.cleanarchitectureapp.common.extensions.dpToPx

class Loader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        setupView()
        setupLottie()
    }

    private fun setupView() {
        background = context.getResourceDrawable(R.color.white)
    }

    private fun setupLottie() {
        val lottieView = ProgressBar(context).apply {
            layoutParams = LayoutParams(
                dpToPx(160f), dpToPx(160f), Gravity.CENTER
            )
        }
        this.addView(lottieView)
    }

}