package com.example.gridanimation.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gridanimation.R

class ConfrigurationViewModel(app: Application) : AndroidViewModel(app) {

    var animationDuration = 500L
    var gridSpacing = app.resources.getDimension(R.dimen.item_margin).toInt()

    var alphabetList = mutableListOf<Char>()

    init {
        ('A'..'Z').forEach { alphabetList.add(it) }
    }
}