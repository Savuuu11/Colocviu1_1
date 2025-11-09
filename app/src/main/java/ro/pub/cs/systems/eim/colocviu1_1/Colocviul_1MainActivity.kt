package ro.pub.cs.systems.eim.colocviu1_1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Colocviul_1MainActivity : AppCompatActivity() {

    private lateinit var butoaneEditText: EditText
    private var allClicks: String = ""
    private var serviceStarted: Boolean = false

    private val REQUEST_CODE_SECONDARY = 1

    private fun appendButtonClick(buttonText: String) {
        if (allClicks.isEmpty()) {
            allClicks = buttonText
        } else {
            allClicks += ",$buttonText"
        }
        butoaneEditText.setText(allClicks)

        // Check if all cardinal points are present
        if (allClicks.contains("NORTH") &&
            allClicks.contains("EAST") &&
            allClicks.contains("WEST") &&
            allClicks.contains("SOUTH") &&
            !serviceStarted) {
            val serviceIntent = Intent(this, Colocviul_1_Service::class.java)
            serviceIntent.putExtra("INSTRUCTIONS", allClicks)
            startService(serviceIntent)
            serviceStarted = true
        }
    }


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)

        butoaneEditText = findViewById(R.id.butoane)

        val north = findViewById<Button>(R.id.NORTH)
        val east = findViewById<Button>(R.id.EAST)
        val west = findViewById<Button>(R.id.WEST)
        val south = findViewById<Button>(R.id.SOUTH)

        north.setOnClickListener { appendButtonClick("NORTH") }
        east.setOnClickListener { appendButtonClick("EAST") }
        west.setOnClickListener { appendButtonClick("WEST") }
        south.setOnClickListener { appendButtonClick("SOUTH") }

        val secondActivityButton = findViewById<Button>(R.id.SECONDACTIVITY)



        secondActivityButton.setOnClickListener {

            val TOTAL_CLICKS = allClicks.split(",").size.toString()
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)

            intent.putExtra("TOTAL_CLICKS", TOTAL_CLICKS)
            startActivityForResult(intent, REQUEST_CODE_SECONDARY)
            butoaneEditText.setText("")
            allClicks = ""
            // Reset serviceStarted flag if needed, or stop the service here if it's meant to be tied to this activity's lifecycle
            // For now, let's assume service is stopped when the app is destroyed.
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        val serviceIntent = Intent(this, Colocviul_1_Service::class.java)
        stopService(serviceIntent)
        serviceStarted = false
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ALL_CLICKS", allClicks)
        outState.putBoolean("SERVICE_STARTED", serviceStarted)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        allClicks = savedInstanceState.getString("ALL_CLICKS", "")
        butoaneEditText.setText(allClicks)
        serviceStarted = savedInstanceState.getBoolean("SERVICE_STARTED", false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SECONDARY && data != null) {
            val result = data.getStringExtra("RESULT") ?: "No result"
            android.widget.Toast.makeText(this, "Result: $result", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

}