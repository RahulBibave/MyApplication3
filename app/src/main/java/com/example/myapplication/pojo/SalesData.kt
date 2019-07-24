package com.example.myapplication.pojo

import java.io.Serializable

data class SalesData(val id:String,
                     val project_id:String,
                     val rqube_id:String,
                     val building_id:String,
                     val manager_approval:String,
                     val salesco_approval:String,
                     val president_approval:String,
                     val backoffice_approval:String,
                     val flat_no:String,
                     val datetime:String,
                     val front_image:String,
                     val back_image:String,
                     val adhar_card:String,
                     val pan_card:String,
                     val cp_image:String,
                     val cheque_image:String,
                     val project_name:String,
                     val building_name:String,
                     val optional1:String,
                     val optional2:String,
                     val cp_image1:String,
                     val adhar_card1:String,
                     val enquiry_form:String,
                     val manager_date:String,
                     val salesco_date:String,
                     val president_date:String,
                     val backoffice_date:String,
                     val manager_note:String,
                     val salesco_note:String


):Serializable
