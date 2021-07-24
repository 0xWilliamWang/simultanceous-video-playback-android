package com.example.practiceandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginBtn:Button = findViewById(R.id.login)
        loginBtn.setOnClickListener(
            {Toast.makeText(this,"正在开发中",Toast.LENGTH_SHORT).show()}
        )
    }
}