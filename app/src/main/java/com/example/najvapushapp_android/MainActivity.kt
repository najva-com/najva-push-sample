package com.example.najvapushapp_android
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.najva.sdk.NajvaClient
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    val actions = arrayListOf<String>()
    val priority = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NajvaClient.configuration.setUserSubscriptionListener {
            findViewById<TextView>(R.id.token).text = it
        }
        findViewById<TextView>(R.id.token).text = NajvaClient.getInstance().subscribedToken
        //handle spinners
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        actions.add("بازکردن لینک ")
        actions.add("بازکردن اپلیکیشن ")

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, actions
            )
            spinner.adapter = adapter
            }
        priority.add("بالا")
        priority.add("کم")
        if (spinner2 != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, priority
            )
            spinner2.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                spinner_settext(selectedItem)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //‌Button
        val button = findViewById<Button>(R.id.btn_register)
        button.setOnClickListener{
            val intent = Intent(this, Najva_Activity::class.java)
            startActivity(intent)
           }

        }
    //handle click on button send notif
    fun sendPush(view: View) {
        Log.d("on click", "on click called")
        //set data
        val data = getBasicData()
        data["onclick_action"] =spinner_chosen()
        if(isNull(data)){
        val request: JsonObjectRequest = object : JsonObjectRequest(
            "https://app.najva.com/notification/api/v1/notifications/",
            JSONObject(data as Map<*, *>),
            Response.Listener { },
            Response.ErrorListener { }
        ) {
            override fun getHeaders(): Map<String, String> {
                return this@MainActivity.getHeaders()
            }
        }

        Volley.newRequestQueue(this).add(request)
        }}

    private fun isNull(data: MutableMap<String, Any>): Boolean {
       for (item in data){
           if (item.value.toString()==""){
               Toast.makeText(applicationContext, "اطلاعات ناقص است. ", Toast.LENGTH_SHORT).show()
               return false
           }
       }
        return true
    }

    private fun getHeaders(): Map<String, String> {
        val map: MutableMap<String, String> =
            HashMap()
        map["Authorization"] = "Token aff3c68af97bf4608fdf28673ca26201ca027ed9"
        return map
    }

    //notification date handle
    private fun getBasicData(): MutableMap<String, Any> {
        //declare objects
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val priority: String = if (spinner2.selectedItem.toString() == "کم") {
            "normal"
        } else {
            "high"
        }
        val url = findViewById<EditText>(R.id.link).text.toString()
        val title = findViewById<EditText>(R.id.title).text.toString()
        val body = findViewById<EditText>(R.id.description).text.toString()
        val icon = findViewById<EditText>(R.id.icon).text.toString()
        val image = findViewById<EditText>(R.id.image).text.toString()
        val map: MutableMap<String, Any> =
            HashMap()
        map["api_key"] = "8b84ad3a-3daa-4520-9adc-d7528ea95a54"
        map["title"] = title
        map["body"] = body
        map["url"] = url
        map["image"] = image
        map["icon"] = icon
        map["priority"] = priority

        val array = JSONArray()
        array.put(NajvaClient.getInstance().subscribedToken)
        map["subscriber_tokens"] = array
        Log.d("map creation", map.toString())
        return map
    }
    private fun spinner_chosen(): String {
        var chosen = String()
        val spinner = findViewById<Spinner>(R.id.spinner)
        val text: String = spinner.selectedItem.toString()
        when (text) {
            "بازکردن لینک " -> {
                chosen = "open-link"
            }
            "بازکردن اپلیکیشن " -> {
                chosen = "open-app"
            }
        }
        return chosen
    }
    private fun spinner_settext(selectedItem: String) {
        val link = findViewById<EditText>(R.id.link)
        when (selectedItem) {
            "بازکردن لینک " -> {
                link.setText("http://www.najva.com")
            }
            "بازکردن اپلیکیشن " -> {
                link.setText("com.najvapushapp_android.app")
            }
        }
    }

    fun delete(view: View) {
        when (view.id) {
            R.id.trash_link -> {
                val link = findViewById<EditText>(R.id.link)
                link.setText(" ")
            }
            R.id.trash_title -> {
                val title = findViewById<EditText>(R.id.title)
                title.text.clear()
            }
            R.id.trash_description -> {
                val description = findViewById<EditText>(R.id.description)
                description.text.clear()
            }
            R.id.trash_icon -> {
                val icon = findViewById<EditText>(R.id.icon)
                icon.text.clear()
            }
            R.id.trash_image -> {
                val image = findViewById<EditText>(R.id.image)
                image.text.clear()
            }
        }
    }

    fun refresh(view: View) {
        val intent = intent
        finish()
        startActivity(intent)
    }
}
