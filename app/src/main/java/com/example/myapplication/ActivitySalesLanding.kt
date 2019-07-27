package com.example.myapplication

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.pojo.SalesData
import kotlinx.android.synthetic.main.activity_sales_landing.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class ActivitySalesLanding : AppCompatActivity(), OnClickListner {


    val list:ArrayList<SalesData> = ArrayList()
    private var myPreferences = "myPrefs"
    var sharedPreferences: SharedPreferences?=null
    var userID=""
    var buildID=""
    var proID=""
    var userName=""
    companion object{
        var rolesale=""
    }
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_landing)
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(this,list,PermissionsRequestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        rolesale=sharedPreferences!!.getString("rolex", "").toString()
        proID=sharedPreferences!!.getString("proIDx", "").toString()
        buildID= sharedPreferences!!.getString("buidIDx", "").toString()
        userID= sharedPreferences!!.getString("userIDx", "").toString()
        userName=sharedPreferences!!.getString("userNamex", "").toString()
        txtNames.text=userName+" ( "+ rolesale+" )"
        getAllForms()
    }

    fun buNewEntry(view:View){
        var intentnext=Intent(this,MainActivity::class.java)
        Log.e("sssssssss",""+userID)
        intentnext.putExtra("userIDs",userID)
        intentnext.putExtra("buidIDs",buildID)
        intentnext.putExtra("proIDs",proID)
        startActivity(intentnext)
    }
    fun buLogOut(view: View){
        /*val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()*/

        val settings = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun getAllForms(){
        val requestQueue=Volley.newRequestQueue(this)
        val mUrl=" http://bookings.megapolis.co.in/all_forms"
        val stringRequest=object :StringRequest(Request.Method.POST,mUrl,
            Response.Listener { response ->
                var strResp = response.toString()
                val jsonObj = JSONObject(strResp)
                var status=jsonObj.getInt("status")
                if (status==200){
                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        val id=jsonInner.getString("id")
                        val project_id=jsonInner.getString("project_id")
                        val rqube_id=jsonInner.getString("rqube_id")
                        val building_id=jsonInner.getString("building_id")
                        val manager_approval=jsonInner.getString("manager_approval")
                        val salesco_approval=jsonInner.getString("salesco_approval")
                        val president_approval=jsonInner.getString("president_approval")
                        val backoffice_approval=jsonInner.getString("backoffice_approval")
                        val flat_no=jsonInner.getString("flat_no")
                        val datetime=jsonInner.getString("datetime")
                        val front_image=jsonInner.getString("front_image")
                        val back_image=jsonInner.getString("back_image")
                        val adhar_card=jsonInner.getString("adhar_card")
                        val pan_card=jsonInner.getString("pan_card")
                        val cp_image=jsonInner.getString("cp_image")
                        val cheque_image=jsonInner.getString("cheque_image")
                        val project_name=jsonInner.getString("project_name")
                        val building_name=jsonInner.getString("building_name")
                        val optional1=jsonInner.getString("optional1")
                        val optional2=jsonInner.getString("optional2")
                        val cp_image1 =jsonInner.getString("cp_image1")
                        val adhar_card1=jsonInner.getString("adhar_card1")
                        val enquiry_form=jsonInner.getString("enquiry_form")
                        val manager_date =jsonInner.getString("manager_date")
                        val salesco_date  =jsonInner.getString("salesco_date")
                        val president_date =jsonInner.getString("president_date")
                        val backoffice_date=jsonInner.getString("backoffice_date")
                        val manager_note=jsonInner.getString("manager_note")
                        val salesco_note=jsonInner.getString("salesco_note")
                        val sales=SalesData(id, project_id, rqube_id, building_id, manager_approval, salesco_approval, president_approval, backoffice_approval, flat_no, datetime, front_image, back_image, adhar_card, pan_card, cp_image, cheque_image, project_name, building_name, optional1, optional2, cp_image1, adhar_card1, enquiry_form, manager_date, salesco_date, president_date, backoffice_date, manager_note, salesco_note)

                        //val sales=SalesData(id, project_id, rqube_id, building_id, manager_approval, salesco_approval, president_approval, backoffice_approval, flat_no, datetime, front_image, back_image, adhar_card, pan_card, cp_image, cheque_image,project_name,building_name)
                        list.add(sales)



                    }
                    recyclerView.layoutManager= LinearLayoutManager(this)
                    var adapter=AdapterSales(this,list,this)
                    recyclerView.adapter=adapter

                }
            },
            Response.ErrorListener { error ->

            })
        {
            //Header
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Client-Service","frontend-client")
                headers.put("Auth-Key","d04d46b77cf9ff9db712232e2a7f4b9cf5dbafbd")
                return headers
            }
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user_id",userID)
                params.put("role","Sales_Executive")

                return params



            }


        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue!!.add(stringRequest)
    }



    // Receive the permissions request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode ->{
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode,permissions,grantResults)

                if(isPermissionsGranted){
                    // Do the task now
                    // toast("Permissions granted.")
                }else{
                    // toast("Permissions denied.")
                }
                return
            }
        }
    }
    override fun onItemClick(pos: Int) {
        val data=list[pos]
        var intent=Intent(this,ActivityFormView::class.java)
        intent.putExtra("adhar_card",data.adhar_card)
        intent.putExtra("front_image",data.front_image)
        intent.putExtra("back_image",data.back_image)
        intent.putExtra("pan_card",data.pan_card)
        intent.putExtra("cheque_image",data.cheque_image)
        intent.putExtra("cp_image",data.cp_image)

        intent.putExtra("cp_image1",data.cp_image1)
        intent.putExtra("optional1",data.optional1)
        intent.putExtra("optional2",data.optional2)
        intent.putExtra("enquiry_form",data.enquiry_form)
        intent.putExtra("adhar_card1",data.adhar_card1)


        intent.putExtra("manager_approval",data.manager_approval)
        intent.putExtra("president_approval",data.president_approval)
        intent.putExtra("backoffice_approval",data.backoffice_approval)
        intent.putExtra("salesco_approval",data.salesco_approval)


        intent.putExtra("form_id",data.id)

        intent.putExtra("manager_date",data.manager_date)
        intent.putExtra("salesco_date",data.salesco_date)
        intent.putExtra("president_date",data.president_date)
        intent.putExtra("backoffice_date",data.backoffice_date)
        intent.putExtra("manager_note",data.manager_note)
        intent.putExtra("salesco_note",data.salesco_note)
        Log.e("saaaaaaaaaaaaaa",""+data.salesco_note)
        startActivityForResult(intent,1)
    }
}
