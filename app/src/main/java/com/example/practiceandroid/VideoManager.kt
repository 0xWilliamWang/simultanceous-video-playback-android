package com.example.practiceandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.practiceandroid.databinding.VideoBinding
import okio.ByteString.Companion.toByteString
import org.json.JSONException
import org.json.JSONObject
import java.security.DigestInputStream
import java.security.MessageDigest
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates
import kotlin.text.StringBuilder as StringBuilder1

class VideoManager : AppCompatActivity() {
    lateinit var vb: VideoBinding
    private var isMaster = false
    private lateinit var serverConn: ServerConn

    fun onRadioButtonClicked(v: View) {
        if (v is RadioButton) {
            when (v.getId()) {
                vb.radioMaster.id -> {
                    isMaster = true
                }
                vb.radioSlave.id -> {
                    isMaster = false
                }
            }
        }
    }

    private fun loginFailTips() {
        runOnUiThread {
            Toast.makeText(this, "账户或密码错误，请重试", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = VideoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val config = this.assets.open("config.yml")
        val proper = Properties()
        proper.load(config)
        val tmp = "${proper.getProperty("serverUrl")}/status/"
        serverConn = ServerConn(tmp)
        val videoSelectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val fileUri = result.data?.data
                val sha1 = getVideoSha1(fileUri)
                val tmp1 = mapOf(
                    "account" to "wangkaixuan", "action" to "newSession", "videoSHA1" to sha1, "token" to "1481e789-dc8b-4ac5-afc6-e12c329d59f1"
                )
                thread {
                    try {
                        val res = serverConn.send(tmp1)
                        Log.d("VideoManager.res", res.toString())
                    } catch (e: JSONException) {
                        Log.d("JSONException", e.toString())
                        loginFailTips()
                    } catch (e: Exception) {
                        android.util.Log.d("Exception", e.toString())
                        loginFailTips()
                    }
                }

                vb.videoView.setVideoURI(result.data?.data)
            }
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

    private fun getVideoSha1(fileUri: Uri?): String {
        val resolver = applicationContext.contentResolver
        var hex = ""
        fileUri?.let {
            resolver.openInputStream(it).use { stream ->
                val sha1 = MessageDigest.getInstance("sha1")
                val dis = DigestInputStream(stream, sha1)
                val buffer = ByteArray(1024 * 8)
                while (dis.read(buffer) != -1) {

                }
                dis.close()

                val raw: ByteArray = dis.messageDigest.digest();
                for (b in raw) {
                    hex += String.format("%02x", b)
                }
            }
        }
        Log.d("sha1", hex)
        return hex
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}