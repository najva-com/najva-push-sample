package com.example.najvapushapp_android
import android.app.Application
import android.widget.Spinner
import com.najva.sdk.NajvaClient
import com.najva.sdk.NajvaConfiguration

class NajvaApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        val config = NajvaConfiguration()
       // config.firebaseEnabled = true
       // config.locationEnabled = true
        registerActivityLifecycleCallbacks(NajvaClient.getInstance(this,config))
    }
    override fun onTerminate(){
        super.onTerminate()

        NajvaClient.getInstance().onAppTerminated()
    }
}
