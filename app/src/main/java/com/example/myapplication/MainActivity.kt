package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    val mProjectName:ArrayList<String> = ArrayList()
    val mProjectId:ArrayList<String> = ArrayList()

    val mBuildingName:ArrayList<String> = ArrayList()
    val mBuildingID:ArrayList<String> = ArrayList()
    var proID=""
    var buildID=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getProject()

        txtProject.setOnClickListener {
            mBuildingName.clear()
            mBuildingID.clear()
            buildID=""
            txtBuiding.text=""
            val array = arrayOfNulls<String>(mProjectName.size)
            mProjectName.toArray(array)

            val builder = AlertDialog.Builder(this)


            // Set a title for alert dialog
            builder.setTitle("Select Project.")
            builder.setItems(array,{_, which ->
                val selected = mProjectName[which]
                try {
                    txtProject.text=selected
                    proID=mProjectId[which] as String
                    getBuilding(proID)
                }catch (e:IllegalArgumentException){

                }
            })

            val dialog = builder.create()
            dialog.show()
        }


        txtBuiding.setOnClickListener {
            val array = arrayOfNulls<String>(mBuildingName.size)
            mBuildingName.toArray(array)

            val builder = AlertDialog.Builder(this)


            // Set a title for alert dialog
            builder.setTitle("Select Building.")
            builder.setItems(array,{_, which ->
                val selected = mBuildingName[which]
                try {
                    txtBuiding.text=selected
                    buildID=mBuildingID[which] as String
                    //  Toast.makeText(applicationContext, "Building  ID  - "+buildID, Toast.LENGTH_LONG).show()
                    // getBuilding(projectID)
                }catch (e:IllegalArgumentException){

                }
            })

            val dialog = builder.create()
            dialog.show()
        }





    }

    fun buNext(view:View){

        if (!(proID.equals("") || buildID.equals("") || edtFlatNo.text.toString().equals("") || edtRqube.text.toString().equals(""))){
            val intents=Intent(this,Activity_UploadImages::class.java)
            if (checkbox.isChecked){
                intents.putExtra("chennel","2")
            }else{
                intents.putExtra("chennel","1")
            }
            intents.putExtra("user_id",intent.getStringExtra("userIDs"))
            intents.putExtra("project_id",proID)
            intents.putExtra("building_id",buildID)
            intents.putExtra("flat_no",edtFlatNo.text.toString())
            intents.putExtra("rqube_id",edtRqube.text.toString())

            startActivity(intents)
            finish()
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Megapolis")
            builder.setMessage("All Fields are Required")
            builder.setPositiveButton("OK"){dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }


    fun getProject(){
        var url="http://bookings.megapolis.co.in/all_projects"
        var queue= Volley.newRequestQueue(this)
        var stringRequest=object : StringRequest(
            Request.Method.GET,url,
            Response.Listener { response ->

                Log.e("FFFFFFFFFFFFFFF",""+response)

                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status=jsonObj.getInt("status")
                if (status==200){
                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        var id = jsonInner.getString("id")
                        var project_name = jsonInner.getString("project_name")
                        mProjectId.add(id)
                        mProjectName.add(project_name)
                    }

                }


            },
            Response.ErrorListener {
                    error ->

            }) {
            //Header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Client-Service","frontend-client")
                headers.put("Auth-Key","d04d46b77cf9ff9db712232e2a7f4b9cf5dbafbd")
                return headers
            }


        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue!!.add(stringRequest)

    }

    fun getBuilding(projectID:String){

        var url="http://bookings.megapolis.co.in/all_buildings"
        var queue= Volley.newRequestQueue(this)
        var stringRequest=object : StringRequest(
            Request.Method.POST,url,
            Response.Listener { response ->

                mBuildingID.clear()
                mBuildingName.clear()
                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status=jsonObj.getInt("status")
                if (status==200){
                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        var id = jsonInner.getString("id")
                        var building_name = jsonInner.getString("building_name")
                        mBuildingName.add(building_name)
                        mBuildingID.add(id)

                    }

                }


            },
            Response.ErrorListener {
                    error ->

            }) {
            //Header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Client-Service","frontend-client")
                headers.put("Auth-Key","d04d46b77cf9ff9db712232e2a7f4b9cf5dbafbd")
                return headers
            }
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("project_id",projectID)
                return params
            }


        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue!!.add(stringRequest)
    }
}
