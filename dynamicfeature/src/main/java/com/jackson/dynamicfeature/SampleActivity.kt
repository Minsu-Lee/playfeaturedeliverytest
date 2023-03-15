package com.jackson.dynamicfeature

import android.view.LayoutInflater
import com.jackson.dynamicfeature.databinding.ActivitySampleBinding
import com.jackson.playfeatureresourcesample.base.BaseViewBindingSplitCompatActivity

class SampleActivity: BaseViewBindingSplitCompatActivity<ActivitySampleBinding>() {

    override fun onBinding(layoutInflater: LayoutInflater): ActivitySampleBinding
        = ActivitySampleBinding.inflate(layoutInflater)

    override fun ActivitySampleBinding.initView() {
        supportActionBar?.hide()
    }
}