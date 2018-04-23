package com.escapesanctuary.utsuro

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ExpandableLayout : FrameLayout {

    enum class Status {
        COLLAPSED,
        EXPANDED
    }

    private var status: Status = Status.EXPANDED

    private val collapseHeight = (context.resources.displayMetrics.density * 16).toInt()

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxHeight = measureChildren(widthMeasureSpec)

        when (status) {
            Status.COLLAPSED -> setMeasuredDimension(widthMeasureSpec, collapseHeight)
            Status.EXPANDED -> setMeasuredDimension(widthMeasureSpec, maxHeight)
        }
    }

    private fun measureChildren(widthMeasureSpec: Int): Int {
        return (0..childCount.dec()).mapNotNull { getChildAt(it) }
                .onEach { measureChild(it, widthMeasureSpec, MeasureSpec.UNSPECIFIED) }
                .map { it.measuredHeight }
                .max() ?: 0
    }

    fun toggle() {
        status = when (status) {
            Status.COLLAPSED -> Status.EXPANDED
            Status.EXPANDED -> Status.COLLAPSED
        }
        requestLayout()
    }

    fun collapse() {
        status = Status.COLLAPSED
    }

    fun expanded() {
        status = Status.EXPANDED
    }
}