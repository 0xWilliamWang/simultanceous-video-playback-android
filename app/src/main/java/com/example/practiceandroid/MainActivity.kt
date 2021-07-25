package com.example.practiceandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practiceandroid.databinding.LoginBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener({
            if (binding.account.text.toString() == "wkx" && binding.password.text.toString() =="123"){
                val intent = Intent(this,video::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"账户或密码错误，请重试",Toast.LENGTH_SHORT).show()
            }
        })
    }
}