package com.suprit.hireaudit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suprit.hireaudit.R
import com.suprit.hireaudit.ViewAccountantActivity
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.Accountant

class AccountAdapter(
        val listAccountants : MutableList<Accountant>,
        val context: Context

) : RecyclerView.Adapter<AccountAdapter.AccountantView>(){


    class AccountantView(view : View) : RecyclerView.ViewHolder(view){
        val imgProfile : ImageView
        val tvAccountantName : TextView
        val tvPrice : TextView
        val btnBook : Button

        init {
            imgProfile = view.findViewById(R.id.imgProfile)
            tvAccountantName = view.findViewById(R.id.tvAccountantName)
            tvPrice = view.findViewById(R.id.tvPrice)
            btnBook = view.findViewById(R.id.btnBook)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountantView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accountant, parent, false)
        return AccountantView(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AccountantView, position: Int) {
        val accountant = listAccountants[position]

        holder.tvAccountantName.text = accountant.accountantFName + " " + accountant.accountantLName
        holder.tvPrice.text = "${accountant.pricePerDay.toString()}/day"


        val imagePath = ServiceBuilder.loadImagePath() + accountant.accountantImage!!.split("\\")[1]

        Glide.with(context).load(imagePath).into(holder.imgProfile)



        holder.btnBook.setOnClickListener{
            val intent = Intent(context, ViewAccountantActivity::class.java)

            intent.putExtra("accountant", accountant)

            context.startActivity(intent)
        }

    }



    override fun getItemCount(): Int {
        return listAccountants.size
    }
}