package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.util.Base64
import android.util.Log
import android.view.View
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mikelau.croperino.Croperino
import com.mikelau.croperino.Croperino.prepareChooser
import com.mikelau.croperino.CroperinoConfig
import com.mikelau.croperino.CroperinoFileUtil
import kotlinx.android.synthetic.main.activity__upload.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Activity_UploadImages : AppCompatActivity() {

    var temp=1
    var img=""
    var frontImg=""
    var backImg=""
    var cpImg=""
    var checkImg=""
    var aadharImg=""
    var panImg=""
    var timing=""
    var cp1Img=""
    var aadharImg1=""
    var enqImg=""
    var opt1Img=""
    var opt2Img=""
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__upload)


        if (intent.getStringExtra("chennel").equals("1")){
            linearCp.visibility=View.GONE
        }
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mma")
            timing =  current.format(formatter)

        } else {
            var date = Date();
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            timing = formatter.format(date)

        }*/
        val date = getCurrentDateTime()
        timing = date.toString("yyyy/MM/dd HH:mm:ss")
        CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures")
        CroperinoFileUtil.setupDirectory(this)
        imgFront.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=2
            }
        }
        imgBack.setOnClickListener{
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=3
            }
        }
        imgPan.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=4
            }
        }
        imgAadar.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=5
            }
        }
        imgCheck.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=6
            }
        }
        imgCP.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=7
            }
        }
        linearCp1.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=8
            }
        }
        imgAadar2.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=9
            }
        }
        imgOpt1.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=10
            }
        }
        imgOpt2.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=11
            }
        }
        imgEnq.setOnClickListener {
            if (CroperinoFileUtil.verifyStoragePermissions(this)!!) {
                prepareChooser()
                temp=12
            }
        }

    }



    private fun prepareChooser() {
       /* Croperino.prepareChooser(
            this,
            "Capture photo...",
            ContextCompat.getColor(this, android.R.color.background_dark)
        )*/
        Croperino.prepareCamera(this)
    }


    private fun prepareCamera() {
        Croperino.prepareCamera(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CroperinoConfig.REQUEST_TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                Croperino.runCropImage(
                    CroperinoFileUtil.getTempFile(),
                    this,
                    true,
                    2,
                    3,
                    R.color.gray,
                    R.color.gray_variant
                )
            }
            CroperinoConfig.REQUEST_PICK_FILE -> if (resultCode == Activity.RESULT_OK) {
                CroperinoFileUtil.newGalleryFile(data, this)
                Croperino.runCropImage(
                    CroperinoFileUtil.getTempFile(),
                    this,
                    true,
                    2,
                    3,
                    R.color.gray,
                    R.color.gray_variant
                )
            }
            CroperinoConfig.REQUEST_CROP_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val i = Uri.fromFile(CroperinoFileUtil.getTempFile())

                if (temp==2){
                    imgFront!!.setImageURI(i)

                   var imageStream = this.getContentResolver().openInputStream(i)
                   var bitmap= BitmapFactory.decodeStream(imageStream)
                    frontImg=getStringImage(bitmap)

                }else if (temp==3){
                    imgBack!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    backImg=getStringImage(bitmap)

                }else if (temp==4){
                    imgPan!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    panImg=getStringImage(bitmap)

                }else if (temp==5){
                    imgAadar!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    aadharImg=getStringImage(bitmap)

                }else if(temp==6){
                    imgCheck!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    checkImg=getStringImage(bitmap)

                }else if (temp==7){
                    imgCP!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    cpImg=getStringImage(bitmap)
                }else if (temp==8){
                    imgCP1!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    cp1Img=getStringImage(bitmap)
                }else if (temp==9){
                    imgAadar2!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    aadharImg1=getStringImage(bitmap)
                }else if (temp==10){
                    imgOpt1!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    opt1Img=getStringImage(bitmap)
                }else if (temp==11){
                    imgOpt2!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    opt2Img=getStringImage(bitmap)
                }else if (temp==12){
                    imgEnq!!.setImageURI(i)

                    var imageStream = this.getContentResolver().openInputStream(i)
                    var bitmap= BitmapFactory.decodeStream(imageStream)
                    enqImg=getStringImage(bitmap)
                }

            }
            else -> {
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CroperinoFileUtil.REQUEST_CAMERA) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]

                if (permission == Manifest.permission.CAMERA) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        prepareCamera()
                    }
                }
            }
        } else if (requestCode == CroperinoFileUtil.REQUEST_EXTERNAL_STORAGE) {
            var wasReadGranted = false
            var wasWriteGranted = false

            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]

                if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasReadGranted = true
                    }
                }
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasWriteGranted = true
                    }
                }
            }

            if (wasReadGranted && wasWriteGranted) {
                prepareChooser()
            }
        }
    }

    fun buSubmit(view:View){

        if (!(frontImg.equals("") || backImg.equals("") || aadharImg.equals("") || panImg.equals("") || checkImg.equals("")) ){

            submit()
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Megapolis")
            builder.setMessage("Capture All Images")
            builder.setPositiveButton("OK"){dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }


    fun submit(){
        progressBar.visibility=View.VISIBLE
        var url="http://bookings.megapolis.co.in/booking_details"
        var queue= Volley.newRequestQueue(this)
        var stringRequest=object : StringRequest(
            Request.Method.POST,url,
            Response.Listener { response ->
                Log.e("rrrrrrrrrrrrrrrrrrrrrrr",""+response)
                progressBar.visibility=View.GONE
                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status=jsonObj.getInt("status")
                if (status==200) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Megapolis")
                    builder.setMessage("Form Uploaded Successfully ")
                    builder.setPositiveButton("OK")
                    {dialog, which ->
                        dialog.dismiss()
                        val intent=Intent(this,ActivitySalesLanding::class.java)
                        startActivity(intent)
                        finish()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()




                }


            },
            Response.ErrorListener {
                    error ->
                progressBar.visibility=View.GONE
                Log.e("rrrrrrrrrrrrrrrrrrrrrrrerror",""+error)
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
                params.put("user_id",intent.getStringExtra("user_id"))
                params.put("project_id",intent.getStringExtra("project_id"))
                params.put("building_id",intent.getStringExtra("building_id"))
                params.put("flat_no",intent.getStringExtra("flat_no"))
                params.put("front_image",frontImg)
                params.put("back_image",backImg)
                params.put("cp_image",cpImg)
                params.put("adhar_card",aadharImg)
                params.put("pan_card",panImg)
                params.put("cheque_image",checkImg)
                params.put("optional1",opt1Img)
                params.put("optional2",opt2Img)
                params.put("cp_image1",cp1Img)
                params.put("adhar_card1",aadharImg1)
                params.put("enquiry_form",enqImg)
                params.put("form_id","")
                params.put("datetime",timing)
                params.put("rqube_id",intent.getStringExtra("rqube_id"))
                return params
              Log.e("xxxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",""+timing)


            }


        }
        stringRequest.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue!!.add(stringRequest)
    }


    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }




}
