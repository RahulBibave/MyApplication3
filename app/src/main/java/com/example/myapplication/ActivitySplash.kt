package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class ActivitySplash : AppCompatActivity() {
    private var myPreferences = "myPrefs"
   // var sharedPreferences: SharedPreferences?=null
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
            val role = sharedPreferences.getString("rolex", "5")

            if (role.equals("Sales_Executive") ) {
                var intent = Intent(this, ActivitySalesLanding::class.java)
                startActivity(intent)
                finish()
            } else if(role.equals("5")){
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                var intent = Intent(this, ActivitApproval::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
