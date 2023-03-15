package com.jackson.playfeatureresourcesample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseViewBindingSplitCompatActivity<B: ViewBinding>: BaseViewBindingActivity<B>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitCompat.installActivity(this)
    }
}