package com.jackson.playfeatureresourcesample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.*
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import com.jackson.playfeatureresourcesample.base.BaseViewBindingActivity
import com.jackson.playfeatureresourcesample.databinding.ActivitySampleVideoBinding

class SampleVideoActivity: BaseViewBindingActivity<ActivitySampleVideoBinding>(),
    SurfaceHolder.Callback,
    View.OnTouchListener, View.OnClickListener{

    companion object {
        fun moveVideoActivity(context: Context, videoUri: Uri?) {
            Intent(context, SampleVideoActivity::class.java).apply {
                data = videoUri
            }.let(context::startActivity)
        }
    }

    private var videoUri: Uri? = null
    private val mp: MediaPlayer by lazy { MediaPlayer() }

    override fun onBinding(layoutInflater: LayoutInflater): ActivitySampleVideoBinding
        = ActivitySampleVideoBinding.inflate(layoutInflater)

    override fun Intent.loadData() {
        videoUri = data
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun ActivitySampleVideoBinding.initView() {
        supportActionBar?.hide()
        svIntroVideo.holder.addCallback(this@SampleVideoActivity)
        initPlayAndPauseIcon()

        startGoneEffect()

        clParent.setOnTouchListener(this@SampleVideoActivity)
        ivBack.setOnClickListener(this@SampleVideoActivity)
        ivPlayAndPause.setOnClickListener(this@SampleVideoActivity)
    }

    private fun ActivitySampleVideoBinding.startGoneEffect() {
        ivBack.postDelayed(2000) {
            ivBack.visibility = View.GONE
            ivPlayAndPause.visibility = View.GONE
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        with(binding) {
            ivBack.visibility = View.VISIBLE
            ivPlayAndPause.visibility = View.VISIBLE
            startGoneEffect()
        }
        return true
    }

    private fun prepareVideoSource(): MediaPlayer? {
        return try {
            mp?.apply {
                setDataSource(this@SampleVideoActivity, videoUri!!)
                prepare()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun playAndPause() {
        with(mp) {
            if (!isPlaying) start() else pause()
            initPlayAndPauseIcon()
        }
    }

    private fun initPlayAndPauseIcon(delayTimeMillis: Long = 400) {
        with(binding) {
            ivPlayAndPause.postDelayed(delayTimeMillis) {
                val iconRes = if (mp?.isPlaying == true) R.drawable.ic_pause_img else R.drawable.ic_play_img
                ivPlayAndPause.setImageResource(iconRes)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivBack -> onBackPressed()
            R.id.ivPlayAndPause -> playAndPause()
        }
    }

    override fun surfaceCreated(h: SurfaceHolder) {
        with(binding.svIntroVideo) {
            updateLayoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }

        with(mp) {
            setDisplay(binding.svIntroVideo.holder)
            prepareVideoSource()


            setOnInfoListener { mediaPlayer, i, i2 ->
                initPlayAndPauseIcon()
                false
            }

            setOnErrorListener { mediaPlayer, what, extra ->
                Log.e("SampleVideoActivity", "Error occurred while playing video : $what, $extra")
                false
            }
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) { }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        mp.stop()
        mp.reset()
        videoUri = null
    }
}