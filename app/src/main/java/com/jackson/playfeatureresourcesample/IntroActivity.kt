package com.jackson.playfeatureresourcesample

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.jackson.playfeatureresourcesample.base.BaseViewBindingActivity
import com.jackson.playfeatureresourcesample.databinding.ActivityIntroBinding

class IntroActivity: BaseViewBindingActivity<ActivityIntroBinding>(), View.OnClickListener {

    override fun onBinding(layoutInflater: LayoutInflater): ActivityIntroBinding
        = ActivityIntroBinding.inflate(layoutInflater)

    override fun ActivityIntroBinding.initView() {
        supportActionBar?.hide()
        btnNext.setOnClickListener(this@IntroActivity)
    }

    private fun moveMainActivity() {
        Intent(this, MainActivity::class.java)
            .let(::startActivity)
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnNext -> moveMainActivity()
        }
    }
}