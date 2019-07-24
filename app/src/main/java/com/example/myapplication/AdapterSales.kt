package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.pojo.SalesData
import kotlinx.android.synthetic.main.sales_view.view.*
import android.app.Activity



class AdapterSales(val mContext :Context,val salesarray:ArrayList<SalesData>,val listner: OnClickListner) : RecyclerView.Adapter<AdapterSales.ViewHolderSales>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderSales {
        val view=LayoutInflater.from(mContext).inflate(R.layout.sales_view,p0,false)
        return ViewHolderSales(view)
    }

    override fun getItemCount(): Int {

        return salesarray.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: ViewHolderSales, p1: Int) {
        val data= salesarray[p1]
        p0.bindItems()
        p0.txtRqube!!.text=salesarray[p1].rqube_id
        p0.txtProject!!.text=salesarray[p1].project_name
        p0.txtBuild!!.text=salesarray[p1].building_name
        p0.txtFlat!!.text=salesarray[p1].flat_no
        if (ActivitApproval.role.equals("Manager") && data.manager_approval.equals("1")){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        }
        else if (ActivitApproval.role.equals("President") && data.president_approval.equals("1")){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        }
        else if (ActivitApproval.role.equals("Sales_Co-ordinator") && data.salesco_approval.equals("1")){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        }
        else if (ActivitApproval.role.equals("BackOffice") && data.president_approval.equals("1")){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        }else if (ActivitApproval.role.equals("Filestream") && data.president_approval.equals("1") || ActivitApproval.role.equals("Registration") && data.president_approval.equals("1")){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        }else if (ActivitySalesLanding.rolesale.equals("Sales_Executive") ){
            p0.lin_status!!.setBackgroundResource(R.drawable.not_approved_bg)
        } else{
            p0.lin_status!!.setBackgroundResource(R.drawable.status_bg)
        }


        if (data.president_approval.equals("1")){
            p0.linBackApp!!.visibility=View.VISIBLE
        }
        p0.itemView.setOnClickListener {
            listner.onItemClick(p1)
           /* val intent=Intent(mContext,ActivityFormView::class.java)
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

            mContext.startActivity(intent)*/


        }

    }


    inner class ViewHolderSales (itemView:View):RecyclerView.ViewHolder(itemView){
         var txtRqube :TextView?=null
        var txtProject :TextView?=null
        var txtBuild   :TextView?=null
        var txtFlat    :TextView?=null
        var lin_status:RelativeLayout?=null
        var linBackApp:LinearLayout?=null

        fun bindItems() {
           txtRqube  =itemView.txtRqube
           txtProject=itemView.txtProject
          txtBuild  =itemView.txtBuild
           txtFlat   =itemView.txtFlat
            lin_status=itemView.lin_status
            linBackApp=itemView.linBackApp
        }
    }
}