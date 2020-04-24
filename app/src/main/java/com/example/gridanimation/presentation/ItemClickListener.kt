package com.example.gridanimation.presentation

import android.view.View

/**
 * Iterface to handle recycle view item click
 */
interface ItemClickListener {

    /**
     * method will be called on recycle view item clicked
     */
    fun onItemClick(position: Int, view: View)
}