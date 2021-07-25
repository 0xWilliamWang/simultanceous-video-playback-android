package com.example.practiceandroid

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practiceandroid.databinding.VideoBinding

class video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var vb = VideoBinding.inflate(layoutInflater)
        setContentView(vb.root)
        var uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        vb.videoView.setVideoURI(uri)

        vb.videoStart.setOnClickListener({
            Log.d("log","you click start")
            if (!vb.videoView.isPlaying){
                vb.videoView.start()
            }
        })

        vb.videoPause.setOnClickListener({
            Log.d("log","you click pause")
            if (vb.videoView.isPlaying){
                vb.videoView.pause()
            }
        })

        vb.videoResume.setOnClickListener({
            Log.d("log","you click resume")
            if (vb.videoView.isPlaying){
                vb.videoView.resume()
            }
        })

    }
}