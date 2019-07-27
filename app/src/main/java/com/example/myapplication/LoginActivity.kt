package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {
    private var myPreferences = "myPrefs"
    var sharedPreferences:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
    }
     fun buClick(view :View){
         login()
     }

    fun login(){
        progressBar.visibility=View.VISIBLE
        var queue=Volley.newRequestQueue(this)
        val mUrl="http://bookings.megapolis.co.in/user-login"
        var stringRequest=object :StringRequest(Request.Method.POST,mUrl, Response.Listener
        { response ->
            progressBar.visibility=View.GONE

            var strResp = response.toString()
            val jsonObj: JSONObject = JSONObject(strResp)
            var status=jsonObj.getInt("status")
            Log.e("qwwwwwwwwww",""+status)
            if (status==200){
                sharedPreferences!!.edit().clear().commit()

                val jsonInner: JSONObject = jsonObj.getJSONObject("data")


                var id = jsonInner.getString("id")
                var name = jsonInner.getString("name")
                var mobile_no = jsonInner.getString("mobile_no")
                var email = jsonInner.getString("email")
                var role = jsonInner.getString("role")
                var buildID=jsonInner.getString("building_id")
                var project_id=jsonInner.getString("project_id")



                if (role.equals("Sales_Executive")){
                    var intent= Intent(this,ActivitySalesLanding::class.java)
                   /* intent.putExtra("userID",id)
                    intent.putExtra("buidID",buildID)
                    intent.putExtra("proID",project_id)
                    intent.putExtra("userName",name)
                    intent.putExtra("role",role)*/

                    val editor = sharedPreferences!!.edit()
                    editor.putString("userIDx", id)
                    editor.putString("buidIDx", buildID)
                    editor.putString("proIDx", project_id)
                    editor.putString("userNamex",name)
                    editor.putString("rolex", role)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }else{
                    var intent= Intent(this,ActivitApproval::class.java)
                    val editor = sharedPreferences!!.edit()
                    editor.putString("userIDx", id)
                    editor.putString("buidIDx", buildID)
                    editor.putString("proIDx", project_id)
                    editor.putString("userNamex",name)
                    editor.putString("rolex", role)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }
            }

        }, Response.ErrorListener { error ->
            progressBar.visibility=View.GONE
            Log.e("aaaaaaaaaaaaaaaaaaaaa",""+error)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Megapolis")
            builder.setMessage("login failed enter valid Mobile number and Password")
            builder.setPositiveButton("OK"){dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }){
            //Header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Client-Service","frontend-client")
                headers.put("Auth-Key","d04d46b77cf9ff9db712232e2a7f4b9cf5dbafbd")
                return headers
            }
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("mobile_no",input_email.text.toString())
                params.put("password",input_password.text.toString() )
                params.put("device_id", FirebaseInstanceId.getInstance().getToken().toString())

                return params
            }


        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue!!.add(stringRequest)


    }
}
