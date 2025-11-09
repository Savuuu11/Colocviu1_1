package ro.pub.cs.systems.eim.colocviu1_1

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu_1_secondary)

        val register = findViewById<Button>(R.id.Register)
        val cancel = findViewById<Button>(R.id.cancel)

        val totalClicks = intent.getStringExtra("TOTAL_CLICKS")
        val totalClicksEditText = findViewById<EditText>(R.id.NUMAR)
        totalClicksEditText.setText(totalClicks)

       register.setOnClickListener {
           setResult(Activity.RESULT_OK, Intent().apply {
               putExtra("RESULT", "Register clicked")
           })
           finish()
       }

       cancel.setOnClickListener {
           setResult(Activity.RESULT_CANCELED, Intent().apply {
               putExtra("RESULT", "Cancel clicked")
           })
           finish()
       }
   }
}
