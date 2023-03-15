package com.jackson.playfeatureresourcesample.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<B: ViewBinding>: AppCompatActivity() {

    private var _binding: B? = null
    val binding: B get() = _binding!!

    abstract fun onBinding(layoutInflater: LayoutInflater): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBinding(LayoutInflater.from(this))
            .also { _binding = it }.root.let(::setContentView)
        intent.loadData()
        _binding?.initView()
    }

    open fun Intent.loadData() {}

    open fun B.initView() {}
}