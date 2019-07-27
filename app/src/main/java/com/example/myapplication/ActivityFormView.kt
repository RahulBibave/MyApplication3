package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.pojo.ImageWith
import com.example.myapplication.pojo.SalesData
import kotlinx.android.synthetic.main.activity_form_view.*
import org.json.JSONObject
import java.util.HashMap

class ActivityFormView : AppCompatActivity() {

    val imgList:ArrayList<ImageWith> = ArrayList()
    var form_id=""
    var temp="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_view)
        var listener : OnClickListner ? =null
        val adhar_card=intent.getStringExtra("adhar_card")
        val front_image=intent.getStringExtra("front_image")
        val back_image=intent.getStringExtra("back_image")
        val pan_card=intent.getStringExtra("pan_card")
        val cheque_image=intent.getStringExtra("cheque_image")
        val cp_image=intent.getStringExtra("cp_image")

        val cp_image1=intent.getStringExtra("cp_image1")
        val optional1=intent.getStringExtra("optional1")
        val optional2=intent.getStringExtra("optional2")
        val enquiry_form=intent.getStringExtra("enquiry_form")
        val adhar_card1=intent.getStringExtra("adhar_card1")


       Log.e("xxxxxxxxxxxxxx",""+optional1+","+enquiry_form)


        imgList.add(ImageWith(front_image,"Front Image"))
        imgList.add(ImageWith(back_image,"Back Image"))
        imgList.add(ImageWith(pan_card,"Pan Card"))
        imgList.add(ImageWith(cheque_image,"Cheque Image"))
        imgList.add(ImageWith(adhar_card,"Adhar Card"))

        if (cp_image.isNotEmpty()){
            imgList.add(ImageWith(cp_image,"CP Image 1"))
        }
        if (cp_image1.isNotEmpty()){
            imgList.add(ImageWith(cp_image1,"CP Image 2"))
        }
        if (enquiry_form.isNotEmpty()){
            imgList.add(ImageWith(enquiry_form,"Enquiry"))
        }
        if (adhar_card1.isNotEmpty()){
            imgList.add(ImageWith(adhar_card1,"Adhar Card"))
        }
        if (optional1.isNotEmpty()){
            imgList.add(ImageWith(optional1,"Optional"))
        }
        if (optional2.isNotEmpty()){
            imgList.add(ImageWith(optional2,"Optional"))
        }


        val manager_approval=intent.getStringExtra("manager_approval")
        val president_approval=intent.getStringExtra("president_approval")
        val backoffice_approval=intent.getStringExtra("backoffice_approval")
        val salesco_approval=intent.getStringExtra("salesco_approval")

        val manager_date=intent.getStringExtra("manager_date")
        val salesco_date=intent.getStringExtra("salesco_date")
        val president_date=intent.getStringExtra("president_date")
        val backoffice_date=intent.getStringExtra("backoffice_date")
        val manager_note=intent.getStringExtra("manager_note")
        val salesco_note=intent.getStringExtra("salesco_note")



        if (ActivitApproval.role.equals("President")){
            lin_note_sale.visibility=View.VISIBLE
            lin_note_man.visibility=View.VISIBLE
            txtMan.text=manager_note
            txtSales.text=salesco_note
        }else if (ActivitApproval.role.equals("Manager")){
            lin_note_man.visibility=View.VISIBLE
            lin_note_sale.visibility=View.VISIBLE
            txtMan.text=manager_note
            txtMannote.text="Note"
            txtSales.text=salesco_note
            edtNote.visibility=View.VISIBLE
        }else if (ActivitApproval.role.equals("Sales_Co-ordinator")){
            lin_note_sale.visibility=View.VISIBLE
            txtNoteSale.text="Note"
            lin_note_man.visibility=View.VISIBLE
            txtSales.text=salesco_note
            edtNote.visibility=View.VISIBLE
        }


        if (ActivitApproval.role.equals("Registration") || ActivitApproval.role.equals("Filestream") || ActivitySalesLanding.rolesale.equals("Sales_Executive") ||  ActivitApproval.role.equals("BackOffice")){
            lllllllllll.visibility=View.GONE
            temp="1"
          //  Log.e("xxxxxxxxxxxxxxxxxxxxx",""+ActivitySalesLanding.rolesale+","+ActivitApproval.role)
        }
        if ( ActivitApproval.role.equals("Manager") && manager_approval.equals("1"))  {
          //  Log.e("mmmmmmmmmmmmmmmmmmmm",""+backoffice_approval+","+ActivitApproval.role)
            lllllllllll.visibility=View.GONE
            temp="1"
        }
    /*    if ( ActivitApproval.role.equals("BackOffice") && backoffice_approval.equals("1")){

          //  Log.e("bbbbbbbbbbbbbbbbbbb",""+backoffice_approval+","+ActivitApproval.role)
            lllllllllll.visibility=View.GONE
            temp="1"
        }*/
        if (ActivitApproval.role.equals("President") && president_approval.equals("1")){

            lllllllllll.visibility=View.GONE
            temp="1"
        }
        if ( ActivitApproval.role.equals("Sales_Co-ordinator") && salesco_approval.equals("1")){
          //  Log.e("ssssssssssssssssss",""+backoffice_approval+","+ActivitApproval.role)
            lllllllllll.visibility=View.GONE
            temp="1"
        }


        recyclerViewIMg.layoutManager= GridLayoutManager(this, 3)
        val adapter=AdapterFormView(this,imgList)
        recyclerViewIMg.adapter=adapter



         form_id=intent.getStringExtra("form_id")


        if (manager_approval.equals("1")){
            txtRqube.text="Approved"+"\n"+"( "+manager_date+" )"
            txtRqube.setTextColor(Color.GREEN)
        }else{
            txtRqube.text="Waiting for Approval"
            txtRqube.setTextColor(Color.RED)
        }


        if (president_approval.equals("1")){
            txtBuild.text="Approved"+"\n"+"( "+president_date+" )"
            txtBuild.setTextColor(Color.GREEN)
        }else{
            txtBuild.text="Waiting for Approval"
            txtBuild.setTextColor(Color.RED)
        }

        if (backoffice_approval.equals("1")){
            txtBack.text="Approved"+"\n"+"( "+backoffice_date+" )"
            txtBack.setTextColor(Color.GREEN)

        }else{
            txtBack.text="Waiting for Approval"
            txtBack.setTextColor(Color.RED)
        }


        if (salesco_approval.equals("1")){
            txtProject.text="Approved"+"\n"+"( "+salesco_date+" )"
            txtProject.setTextColor(Color.GREEN)
        }else{
            txtProject.text="Waiting for Approval"
            txtProject.setTextColor(Color.RED)
        }


    }

    fun buApprove(view:View){

        lateinit var dialog: AlertDialog

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Megapolis")

        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to Approve this ?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> submitApprove("1")

               // DialogInterface.BUTTON_NEGATIVE -> toast("Negative/No button clicked.")
                DialogInterface.BUTTON_NEUTRAL -> dialog.dismiss()
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

       /* // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)*/

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()






       // submitApprove()
    }


   /* fun buReject(view: View){
        lateinit var dialog: AlertDialog

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Megapolis")

        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to Reject this ?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> submitApprove("2")

                // DialogInterface.BUTTON_NEGATIVE -> toast("Negative/No button clicked.")
                DialogInterface.BUTTON_NEUTRAL -> dialog.dismiss()
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        *//* // Set the alert dialog negative/no button
         builder.setNegativeButton("NO",dialogClickListener)*//*

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()

    }
*/
    fun submitApprove(status:String){
        val mUrl=" http://bookings.megapolis.co.in/approval"
        var requestQueue=Volley.newRequestQueue(this)
        var stringRequest=object :StringRequest(Request.Method.POST,mUrl, Response.Listener
        { response ->

            Log.e("ddddddddddddddddddddd",""+response)
            var strResp = response.toString()
            val jsonObj = JSONObject(strResp)
            var status=jsonObj.getInt("status")
            if (status==200) {
                try {
                    setResult(1)
                    /*val listener : =ActivitApproval
                    listener!!.onItemClick()*/
                    finish()
                }catch (e : NullPointerException){
                    e.printStackTrace()
                    //listener!!.onItemClick()
                    finish()
                }

            }
        }, Response.ErrorListener {
                error ->
            Log.e("zzzzzzzzzzzz",""+error)

        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Client-Service","frontend-client")
                headers.put("Auth-Key","d04d46b77cf9ff9db712232e2a7f4b9cf5dbafbd")
                return headers
            }

            override fun getParams(): MutableMap<String, String> {
                val params=HashMap<String,String>()
                params.put("user_id",ActivitApproval.userID)
                params.put("role",ActivitApproval.role)
                params.put("form_id",form_id)
                params.put("checked_status",status)
                params.put("note",edtNote.text.toString())

                Log.e("ccccccccccccccccccc",""+ActivitApproval.userID+"          "+ActivitApproval.role+"  "+form_id)
                return params
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue!!.add(stringRequest)

    }

    override fun onBackPressed() {


        if (temp.equals("0"))
        {
            val intent=Intent(this,ActivitApproval::class.java)
            startActivity(intent)
            finish()
        }
        else{
            super.onBackPressed()
        }
    }


    interface OnRefresh{
        fun onItemClickRefresh()
    }



}
