package com.jackson.playfeatureresourcesample

import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.postDelayed
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.jackson.playfeatureresourcesample.base.BaseViewBindingActivity
import com.jackson.playfeatureresourcesample.databinding.ActivityMainBinding


class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val splitInstallManager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(this) }

    override fun onBinding(layoutInflater: LayoutInflater): ActivityMainBinding
        = ActivityMainBinding.inflate(layoutInflater)

    override fun ActivityMainBinding.initView() {
        supportActionBar?.hide()

        btnTest1.setOnClickListener {
            svIntroVideo.stop()
            DynamicModuleManager.getInstance()?.getResourceUri(this@MainActivity, "raw", "intro")?.also {
                Log.e("btnTest1", "$it")
            }?.let(svIntroVideo::setVideoSourcePath)
        }

        btnTest2.setOnClickListener {
            val videoUri = DynamicModuleManager.getInstance()?.getResourceUri(this@MainActivity, "raw", "big_buck_bunny")
            it.postDelayed(800) {
                SampleVideoActivity.moveVideoActivity(this@MainActivity, videoUri)
            }
        }

        btnTest3.setOnClickListener {
            slideImages()
        }

        btnTest4.setOnClickListener {
            timer?.cancel()
            timer = null
            ivBg.visibility = View.GONE
        }

        if (splitInstallManager.installedModules.contains(AppConstants.MODULE_NAME_NEW_FEATURE)) {
            tvState.text = "dynamicfeature module is install\n[${splitInstallManager.installedModules.joinToString(",")}]"
            tvState.setBackgroundColor(Color.BLUE)
        } else {
            tvState.text = "dynamicfeature module is not install"
            tvState.setBackgroundColor(Color.RED)
        }

        btnSample.setOnClickListener {
            Intent().setClassName(BuildConfig.APPLICATION_ID,"${MyApplication.FEATURE_PACKAGE}.SampleActivity")
                .let(::startActivity)
        }
    }

    private var timer: CountDownTimer? = null
    private fun ActivityMainBinding.slideImages() {
        var idx = 0
        timer?.cancel()
        timer = null
        ivBg.visibility = View.VISIBLE
        timer = object: CountDownTimer(15 * 1000, 1000) {
            override fun onTick(time: Long) {
                val fileName = String.format("test_bg_%02d", idx++)
                val resourceUri = DynamicModuleManager.getInstance()?.getResourceUri(this@MainActivity, "drawable", fileName)
                    .also { Log.e("resourceUri", "$it") }
                ivBg.setImageURI(resourceUri)
            }

            override fun onFinish() {
                ivBg.setImageDrawable(null)
                ivBg.visibility = View.GONE
                idx = 0
            }
        }
        timer?.start()
    }
}