package com.jackson.playfeatureresourcesample.custom

import android.annotation.TargetApi
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

class IntroVideoSurfaceView: SurfaceView, SurfaceHolder.Callback {

    enum class State {
        PLAY, STOP
    }

    private var state: State = State.STOP
    private var mp: MediaPlayer? = null
    private var videoUri: Uri? = null

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attributeSet, defStyleAttr, defStyleRes)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context) : super(context)

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mp = MediaPlayer().apply {

            setDisplay(getHolder())
            isLooping = true
            updateLayoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }

            setOnErrorListener { mediaPlayer, what, extra ->
                Log.e("IntroVideoSurfaceView", "Error occurred while playing video : $what, $extra")
                false
            }
        }
    }

    private fun prepareVideoSource(uri: Uri?): MediaPlayer? {
        return try {
            mp?.apply {
                setDataSource(context, uri!!)
                prepare()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setVideoSourcePath(videoUri: Uri?) {
        this.videoUri = videoUri
        prepareVideoSource(videoUri)
        start()
    }

    fun start() {
        mp?.start()?.let {
            state = State.PLAY
        }
    }

    fun stop() {
        mp?.run {
            if (state == State.PLAY) {
                stop()
                reset()
                state = State.STOP
            }
        }
    }

    fun isStart(): Boolean = state == State.PLAY && mp?.isPlaying ?: false

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) { }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        mp?.stop()
        videoUri = null
    }
}