package com.example.practiceandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practiceandroid.databinding.LoginBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private fun sendHttp() {
        thread {
            var conn: HttpURLConnection? = null
            try {
                var response = StringBuilder()
                var url = URL("http://blog.wangkaixuan.com/technical/")
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                var input = conn.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                Log.d("http", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                conn?.disconnect()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.send.setOnClickListener {
            Log.d("log", "sendHttp")
        }
        binding.login.setOnClickListener {

            if (binding.account.text.toString() == "wkx" && binding.password.text.toString() == "123") {
                val intent = Intent(this, video::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "账户或密码错误，请重试", Toast.LENGTH_SHORT).show()
            }
        }
    }
}