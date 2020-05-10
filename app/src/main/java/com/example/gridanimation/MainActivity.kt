package com.example.gridanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gridanimation.presentation.GridFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GridFragment.newInstance())
                    .commitNow()
        }
    }
}
