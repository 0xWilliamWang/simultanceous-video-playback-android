package com.example.practiceandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.practiceandroid.databinding.VideoBinding

class video : AppCompatActivity() {
    val REQUEST_IMAGE_GET = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var vb = VideoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        var videoSelectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("log", "file:${result.data?.data}")
                vb.videoView.setVideoURI(result.data?.data)
            }
        })

        vb.videoSelect.setOnClickListener({
            Log.d("log", "you click select")
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }

            if (intent.resolveActivity(packageManager) != null) {
                videoSelectLauncher.launch(intent)
            }
        })

        vb.videoStart.setOnClickListener({
            Log.d("log", "you click start")
            if (!vb.videoView.isPlaying) {
                vb.videoView.start()
            }
        })

        vb.videoPause.setOnClickListener({
            Log.d("log", "you click pause")
            if (vb.videoView.isPlaying) {
                vb.videoView.pause()
            }
        })

        vb.videoResume.setOnClickListener({
            Log.d("log", "you click resume")
            if (vb.videoView.isPlaying) {
                vb.videoView.resume()
            }
        })

    }
}