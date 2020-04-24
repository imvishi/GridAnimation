package com.example.gridanimation.utils

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE

/**
 * Util class to provide utility functions related to device screen.
 */
object ScreenUtils {

    private const val SPAN_COUNT_LANDSPACE = 8
    private const val SPAN_COUNT_PORTRAIT = 4

    private fun isLandScape(context: Context): Boolean {
        return context.resources.configuration.orientation == ORIENTATION_LANDSCAPE
    }

    /**
     * method returns different span count for different orientation
     * @param context: the context
     *
     * @return span count based on device orientation
     */
    fun getSpanCount(context: Context): Int {
        return if (isLandScape(context)) {
            SPAN_COUNT_LANDSPACE
        } else {
            SPAN_COUNT_PORTRAIT
        }
    }
}