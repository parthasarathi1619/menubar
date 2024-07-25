package com.example.systemnotification

import android.app.LauncherActivity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.systemnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.systemnotification"
    private val description = "Test notification"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       mainBinding= ActivityMainBinding.inflate(layoutInflater)
       setContentView(mainBinding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

       mainBinding.btn.setOnClickListener {
           Toast.makeText(this,"Yes",Toast.LENGTH_LONG).show()
           try
           {
               val intent =Intent(this,LauncherActivity::class.java)
               val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   notificationChannel= NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                   notificationChannel.enableLights(true)
                   notificationChannel.lightColor=Color.GREEN
                   notificationChannel.enableVibration(true)
                   notificationManager.createNotificationChannel(notificationChannel)

                   builder=Notification.Builder(this,channelId)
                       .setContentTitle("System Notification")
                       .setContentText("Notification Recieved")
                       .setSmallIcon(R.drawable.ic_launcher_background)
                       .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                       .setContentIntent(pendingIntent)
               }
               else
               {
                   builder=Notification.Builder(this,channelId)
                       .setContentTitle("System Notification")
                       .setContentText("Notification Recieved")
                       .setSmallIcon(R.mipmap.ic_launcher)
                       .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher_background))
                       .setContentIntent(pendingIntent)

               }
               notificationManager.notify(1234,builder.build())

           }catch (e:Exception)
           {
               Log.d("error","Exception"+e)
               Toast.makeText(this,"Exception"+e,Toast.LENGTH_LONG).show()
           }
       }

    }
}