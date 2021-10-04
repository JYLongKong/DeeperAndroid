package com.lgjy.deeper.ndk.player

import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.lgjy.deeper.common.log.LogP

/**
 * Created by LGJY on 2021/10/4.
 * Email：yujye@sina.com
 *
 * Tip9: Video player based on FFmpeg
 */

class DeepPlayer {

    // media source(file path or stream address)
    var dataSource: String = ""

    // call when prepare succeed
    var onPreparedListener: (() -> Unit)? = null

    // call when progress update
    var onProgressUpdateListener: ((Int) -> Unit)? = null

    // call when error appeared
    var onErrorListener: ((Int, String) -> Unit)? = null

    // surface and its callback
    private var surfaceHolder: SurfaceHolder? = null
    private val surfaceHolderCallback: SurfaceHolder.Callback2 by lazy {
        object : SurfaceHolder.Callback2 {
            override fun surfaceCreated(holder: SurfaceHolder) {
                LogP.i(TAG, "surfaceCreated()")
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                setPlayerSurface(holder.surface)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                LogP.i(TAG, "surfaceDestroyed()")
            }

            override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
                LogP.i(TAG, "surfaceRedrawNeeded()")
            }

        }
    }

    fun prepare() {
        preparePlayer(dataSource)
    }

    fun start() {
        startPlayer()
    }

    fun stop() {
        stopPlayer()
    }

    fun release() {
        releasePlayer()
    }

    /**
     * Used for JNI call when prepare succeed
     */
    fun onPrepared() {
        onPreparedListener?.invoke()
    }

    /**
     * Used for JNI call when progress update
     */
    fun onProgressUpdate(progress: Int) {
        onProgressUpdateListener?.invoke(progress)
    }

    /**
     * Used for JNI call when error appeared
     */
    fun onError(errCode: Int) {
        onErrorListener?.let {
            val errorMsg = when (errCode) {
                ERROR_OPEN_URL_FAIL -> "can not open: $dataSource"
                ERROR_FIND_STREAM_FAIL -> "can not find streams: $dataSource"
                ERROR_FIND_DECODER_FAIL -> "can not find decoder"
                ERROR_ALLOC_CODEC_FAIL -> "can not create codec context"
                ERROR_CODEC_PARAMETERS_INVALID -> "invalid codec parameters"
                ERROR_OPEN_DECODER_FAIL -> "can not open decoder"
                ERROR_NO_MEDIA -> "no media info"
                else -> "unknown error"
            }
            it.invoke(errCode, errorMsg)
        }
    }

    /**
     * Set player surfaceView
     */
    fun setSurfaceView(surfaceView: SurfaceView) {
        surfaceHolder?.removeCallback(surfaceHolderCallback)
        surfaceHolder = surfaceView.holder
        surfaceHolder?.addCallback(surfaceHolderCallback)
    }

    /**
     * Get duration of this dataSource
     */
    fun getDuration(): Int = getPlayerDuration()

    /**
     * Seek media progress
     */
    fun seek(progress: Int) {
        seekPlayer(progress)
    }

    // native functions
    private external fun preparePlayer(dataSource: String)
    private external fun startPlayer()
    private external fun stopPlayer()
    private external fun releasePlayer()
    private external fun seekPlayer(progress: Int)
    private external fun setPlayerSurface(surface: Surface)
    private external fun getPlayerDuration(): Int

    companion object {

        init {
            System.loadLibrary("deepplayer")
        }

        private const val TAG = "===DeepPlayer"

        private const val ERROR_OPEN_URL_FAIL = 1
        private const val ERROR_FIND_STREAM_FAIL = 2
        private const val ERROR_FIND_DECODER_FAIL = 3
        private const val ERROR_ALLOC_CODEC_FAIL = 4
        private const val ERROR_CODEC_PARAMETERS_INVALID = 5
        private const val ERROR_OPEN_DECODER_FAIL = 6
        private const val ERROR_NO_MEDIA = 7
    }
}
