package com.example.practiceandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.practiceandroid.databinding.LoginBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private fun loginFailTips() {
        runOnUiThread {
            Toast.makeText(this, "账户或密码错误，请重试", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginVerify() {
        thread {
            var conn: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("http://192.168.50.174:18605/login/")
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("Accept", "application/json")
                val out: OutputStream = conn.outputStream
                val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                val tmp1 = mapOf("account" to "WangKaixuan1", "password" to "wkxpasswor1d")
                val params = JSONObject(tmp1)
                writer.write(params.toString())
                writer.flush()
                writer.close()
                out.close()

                val input = conn.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                val tmp2 = JSONObject(response.toString())
                val tmp3 = tmp2.getString("token")
                Log.d("http", tmp3)

                if (tmp2.getBoolean("ok")) {
                    val intent = Intent(this, video::class.java)
                    startActivity(intent)
                } else {
                    loginFailTips()
                }
            } catch (e: JSONException) {
                loginFailTips()
            } catch (e: Exception) {
                loginFailTips()
            } finally {
                conn?.disconnect()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            loginVerify()
        }
    }
}