package com.lgjy.deeper.ndk.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.common.util.LogP
import com.lgjy.deeper.ndk.R
import com.lgjy.deeper.ndk.player.DeepPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by LGJY on 2021/10/4.
 * Email：yujye@sina.com
 *
 * Media Player
 */

class PlayerFragment : BaseFragment() {

    // Tip10: by lazy {} used in findViewById()
    private val tvStatus: TextView by lazy { requireView().findViewById(R.id.tv_status) }
    private val tvProgress: TextView by lazy { requireView().findViewById(R.id.tv_progress) }   // show 12:30/50:00
    private val surfaceView: SurfaceView by lazy { requireView().findViewById(R.id.surface_player) }
    private val seekBarProgress: SeekBar by lazy { requireView().findViewById(R.id.seekbar_progress) }

    private lateinit var player: DeepPlayer
    private var duration: Int = 0           // in Seconds
    private var isDragged: Boolean = false  // whether drag the seekbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = DeepPlayer().apply {
            setSurfaceView(surfaceView)
            dataSource = "rtmp://58.200.131.2:1935/livetv/hunantv"

            onErrorListener = { code, msg ->
                CoroutineScope(Dispatchers.Main).launch {
                    LogP.e(TAG, "DeepPlayer onError: code->$code msg->$msg")
                    tvStatus.setTextColor(Color.RED)
                    tvStatus.text = "Player error: $msg"
                }
            }

            onPreparedListener = {
                duration = getDuration()
                CoroutineScope(Dispatchers.Main).launch {
                    // Note: Live streams have no progress
                    if (duration > 0) {
                        // duration: 119 transform into "01:59"
                        tvProgress.text = "00:00/${getMinutes(duration)}:${getSeconds(duration)}"
                        tvProgress.visibility = View.VISIBLE
                        seekBarProgress.visibility = View.VISIBLE
                    }
                    tvStatus.setTextColor(Color.GREEN)
                    tvStatus.text = "DeepPlayer init success"
                }
                start()
            }

            onProgressUpdateListener = { progress ->
                if (!isDragged && duration > 0) {   // ignore manually drag the seekbar
                    CoroutineScope(Dispatchers.Main).launch {
                        tvProgress.text = "${getMinutes(progress)}:${getSeconds(progress)}/" +
                                "${getMinutes(duration)}:${getSeconds(duration)}"
                        seekBarProgress.progress = progress * 100 / duration
                    }
                }
            }
        }


        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) { // if drag by user
                    // progress(1-100) -> 12:30/50:00
                    val progressDuration = progress * duration / 100
                    tvProgress.text = "${getMinutes(progressDuration)}:${getSeconds(progressDuration)}/" +
                            "${getMinutes(duration)}:${getSeconds(duration)}"
                }
            }

            // Call when key down
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isDragged = true
            }

            // Call when key up
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                isDragged = false
                player.seek(seekBar.progress * duration / 100)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        player.prepare()
    }

    override fun onStop() {
        super.onStop()
        player.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }

    private fun getMinutes(duration: Int): String {
        val minutes: Int = duration / 60
        return if (minutes <= 9) "0$minutes" else minutes.toString()

    }

    private fun getSeconds(duration: Int): String {
        val seconds: Int = duration % 60
        return if (seconds <= 9) "0$seconds" else seconds.toString()
    }

    companion object {
        private const val TAG = "===PlayerFragment"
    }
}
