package com.example.practiceandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practiceandroid.databinding.LoginBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener({
            Toast.makeText(this, "account:${binding.account.text},password:${binding.password.text}", Toast.LENGTH_SHORT).show()
        }
        )
    }
}