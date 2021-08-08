package com.example.practiceandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.practiceandroid.databinding.VideoBinding

class video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = VideoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        val videoSelectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("log", "file:${result.data?.data}")
                vb.videoView.setVideoURI(result.data?.data)
            }
        }

        vb.forDebug.setOnClickListener {
            Log.d("log", "you click forDebug")
            Thread(Runnable {
                val sql = "select * from comm.eth_miner limit 3"
                Log.d("log", sql)
            }).start()
        }

        vb.videoSelect.setOnClickListener {
            Log.d("log", "you click select")
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }

            if (intent.resolveActivity(packageManager) != null) {
                videoSelectLauncher.launch(intent)
            }
        }

        vb.videoStart.setOnClickListener {
            Log.d("log", "you click start")
            if (!vb.videoView.isPlaying) {
                vb.videoView.start()
            }
        }

        vb.videoPause.setOnClickListener {
            Log.d("log", "you click pause")
            if (vb.videoView.isPlaying) {
                vb.videoView.pause()
            }
        }

        vb.videoResume.setOnClickListener {
            Log.d("log", "you click resume")
            if (vb.videoView.isPlaying) {
                vb.videoView.resume()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }
}