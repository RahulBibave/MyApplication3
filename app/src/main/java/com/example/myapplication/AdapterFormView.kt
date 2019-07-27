package com.example.myapplication

import android.app.ActionBar
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.pojo.ImageWith
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.lay_view.view.*

import android.app.Dialog
import android.util.Log
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.show_image.*





class AdapterFormView(val mContext:Context,val mArrayListView:ArrayList<ImageWith>):
    RecyclerView.Adapter<AdapterFormView.ViewHolders>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolders {
        val view=LayoutInflater.from(mContext).inflate(R.layout.lay_view,p0,false)
        return ViewHolders(view)
    }

    override fun getItemCount(): Int {
      return mArrayListView.size
    }

    override fun onBindViewHolder(p0: ViewHolders, p1: Int) {
        p0.binditem()
        val data=mArrayListView[p1]
        p0.txtTital!!.text=data.imgName
        Picasso.with(mContext).load(data.imgPath).into(p0.imageView)
        Log.e("eeeeeeeeeeeeeeeeeeee",""+data.imgPath)
        if (data.imgPath.equals("")){
            p0.lin_main!!.visibility=View.GONE
        }
        p0.itemView.setOnClickListener {
            /*val dialog = Dialog(mContext,R.style.Theme_Dialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setContentView(R.layout.show_image)
            Picasso.with(mContext).load(data.imgPath).into(dialog.img)
            dialog.show()*/
            val dialog = Dialog(mContext)
            dialog.setContentView(R.layout.show_image)
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            Picasso.with(mContext).load(data.imgPath).into(dialog.img)
            dialog.show()}

    }


    inner class ViewHolders (itemView: View): RecyclerView.ViewHolder(itemView){
        var imageView:ImageView?=null
        var txtTital:TextView?=null
        var lin_main:LinearLayout?=null
        fun binditem (){
            imageView=itemView.imageView
            txtTital=itemView.txtTital
            lin_main=itemView.lin_main
        }
    }


}