package ro.pub.cs.systems.eim.colocviu1_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class Colocviul_1_BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val action = intent.action

            if (Colocviul_1_Service.ACTION_INSTRUCTIONS == action) {
                val instructions = intent.getStringExtra(Colocviul_1_Service.INSTRUCTIONS_KEY)
                val dateTime = intent.getStringExtra(Colocviul_1_Service.DATE_TIME_KEY)

                val message = "Received at: $dateTime\nInstructions: $instructions"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                Log.d("Colocviul_1_BroadcastReceiver", message)
            }
        }
    }
}