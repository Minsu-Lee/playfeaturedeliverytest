package com.jackson.dynamicfeature

import android.annotation.TargetApi
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

class IntroVideoView: SurfaceView, SurfaceHolder.Callback {

    private var mp: MediaPlayer? = null

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

            val afd = resources.openRawResourceFd(R.raw.intro)
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.declaredLength)
            prepare()
            start()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) { }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        mp?.stop()
    }
}