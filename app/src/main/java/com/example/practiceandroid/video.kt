package com.example.practiceandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practiceandroid.databinding.LoginBinding
import com.example.practiceandroid.databinding.VideoBinding

class video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = VideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.videoStart.setOnClickListener({
            Log.d("log","you click videoStart")
        })
    }
}