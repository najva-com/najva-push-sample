package com.example.najvapushapp_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Najva_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_najva)

        val button = findViewById<Button>(R.id.btn_back)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }
    }

    fun openNajva(view: View) {
        val url = "http://www.najva.com"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}