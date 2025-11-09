package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Colocviul_1_Service : Service() {

    companion object {
        const val ACTION_INSTRUCTIONS = "ro.pub.cs.systems.eim.colocviu1_1.action.INSTRUCTIONS_BROADCAST"
        const val INSTRUCTIONS_KEY = "INSTRUCTIONS_DATA"
        const val DATE_TIME_KEY = "DATE_TIME_DATA"
    }

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Colocviul_1_Service", "onStartCommand called")

        val instructions = intent?.getStringExtra("INSTRUCTIONS")

        if (instructions != null) {
            handler = Handler(Looper.getMainLooper())
            runnable = Runnable { sendBroadcastMessage(instructions) }
            handler?.postDelayed(runnable!!, 2000)
        }

        return START_NOT_STICKY // Service should not be restarted if killed
    }

    private fun sendBroadcastMessage(instructions: String) {
        val currentTime = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault()).format(Date())
        val broadcastIntent = Intent(ACTION_INSTRUCTIONS).apply {
            putExtra(INSTRUCTIONS_KEY, instructions)
            putExtra(DATE_TIME_KEY, currentTime)
        }
        sendBroadcast(broadcastIntent)
        Log.d("Colocviul_1_Service", "Broadcast sent: $currentTime - $instructions")
        stopSelf()
    }

    override fun onDestroy() {
        Log.d("Colocviul_1_Service", "onDestroy called")
        handler?.removeCallbacks(runnable!!)
        super.onDestroy()
    }
}
