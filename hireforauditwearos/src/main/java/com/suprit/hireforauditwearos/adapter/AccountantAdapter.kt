package com.suprit.hireforauditwearos.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireforauditwearos.R

class AccountantAdapter (
   val listAccounts : MutableList<Accountant>,
   val context: Context

        ) : RecyclerView.Adapter<AccountantAdapter.AccountantView>(){

            class AccountantView(view : View) : RecyclerView.ViewHolder(view){
                val imgProfile : ImageView
                val accountantName : TextView
                val pricePerDay : TextView

                init {
                    imgProfile = view.findViewById(R.id.itemimage)
                    accountantName = view.findViewById(R.id.itemname)
                    pricePerDay = view.findViewById(R.id.itemrate)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountantView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accountant, parent, false)

        return AccountantView(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AccountantView, position: Int) {
        val accountant = listAccounts[position]

        val imgPath = ServiceBuilder.loadImagePath() + accountant.accountantImage!!.split("//")[0]

        holder.accountantName.text = accountant.accountantFName + " " + accountant.accountantLName
        holder.pricePerDay.text = "Rs ${accountant.pricePerDay.toString()}/day"

        Toast.makeText(context, imgPath, Toast.LENGTH_SHORT).show()

        Glide.with(context).load(imgPath).into(holder.imgProfile)


    }

    override fun getItemCount(): Int {
        return listAccounts.size
    }

}