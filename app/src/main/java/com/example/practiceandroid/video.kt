package com.example.practiceandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.practiceandroid.databinding.VideoBinding
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class video : AppCompatActivity() {
    val REQUEST_IMAGE_GET = 1
    var cn: Connection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var vb = VideoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        Thread(Runnable {
            cn = createMySQLConn()
        }).start()

        var videoSelectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("log", "file:${result.data?.data}")
                vb.videoView.setVideoURI(result.data?.data)
            }
        })

        vb.forDebug.setOnClickListener({
            Log.d("log", "you click forDebug")
            Thread(Runnable {
                val sql = "select * from comm.eth_miner limit 3"
                Log.d("log", sql)
                execSql(sql)
            }).start()
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

    fun createMySQLConn(): Connection {
        Class.forName("com.mysql.jdbc.Driver")
        //establish connection
        var cn = DriverManager.getConnection(
            "jdbc:mysql://amd:12173/comm", "wkx", "AD159D4-3B31"
        )
        return cn
    }

    fun execSql(sql: String) {
        try {
            if (cn == null) {
                return
            }

            val ps = cn?.createStatement()
            val resultSet = ps!!.executeQuery(sql)
            while (resultSet.next()) {
                Log.d(
                    "sql result: ", resultSet.getString("id") + "," + resultSet.getString("graphics-card-type") + "," + resultSet.getString("hashrate") + "," + resultSet.getString("daily-income") + "," + resultSet.getString("reference-price")
                )
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cn?.close()
    }
}