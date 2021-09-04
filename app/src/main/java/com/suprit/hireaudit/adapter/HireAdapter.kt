package com.suprit.hireaudit.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suprit.hireaudit.R
import com.suprit.hireaudit.UpdateActivity
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.repository.HireRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HireAdapter (
        val listBookings : MutableList<HireModel>,
        val context: Context
        ) : RecyclerView.Adapter<HireAdapter.HireModelView>(){

            class HireModelView(view : View) : RecyclerView.ViewHolder(view){
                val accountantImg : CircleImageView
                val accountantName : TextView
                val startDate : TextView
                val endDate : TextView
                val pricePerDay : TextView
                val btnImgUpdate : ImageView
                val btnImgDelete : ImageView

                init {
                    accountantImg = view.findViewById(R.id.imgAccountantImg)
                    accountantName = view.findViewById(R.id.tvAccountantFullName)
                    startDate = view.findViewById(R.id.tvStartDate)
                    endDate = view.findViewById(R.id.tvEndDate)
                    pricePerDay = view.findViewById(R.id.tvPricePerDay)
                    btnImgUpdate = view.findViewById(R.id.imgBtnUpdate)
                    btnImgDelete = view.findViewById(R.id.imgBtnDelete)
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HireModelView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hireaccountants, parent, false)

        return HireModelView(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HireModelView, position: Int) {
        val newHirings = listBookings[position]

        holder.accountantName.text = newHirings.accountantID?.accountantFName + " " + newHirings.accountantID?.accountantLName
        holder.startDate.text = "From date : "+ newHirings.startDate!!.split("T")[0]
        holder.endDate.text = "To date : "+ newHirings.endDate!!.split("T")[0]
        holder.pricePerDay.text = "Price per day :"+ newHirings.accountantID?.pricePerDay?.toString()

        var imagePath = ServiceBuilder.loadImagePath() + newHirings.accountantID?.accountantImage!!.split("\\")[1]


        Glide.with(context).load(imagePath).into(holder.accountantImg)


        holder.btnImgDelete.setOnClickListener{
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Delete your accountant?")

            builder.setMessage("Deleting the contract before the end date can land you in legal issues? Make sure to" +
                    " consult with the related parties")

            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Delete"){ dialogInterface: DialogInterface, i: Int ->
                CoroutineScope(Dispatchers.IO).launch{
                    try{
                        val hireRepository = HireRepository()

                        val response = hireRepository.deleteMyAccountant(newHirings._id!!)

                        if(response.success == true){
                            withContext(Main){
                                Toast.makeText(
                                    context, "Contract deleted", Toast.LENGTH_SHORT
                                ).show()
                            }

                            withContext(Main){
                                listBookings.remove(newHirings)
                                notifyDataSetChanged()
                            }
                        }
                    }

                    catch (ex : Exception){
                        Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            builder.setNegativeButton("Cancel deletion"){ dialogInterface: DialogInterface, i: Int ->

            }

            val alertDialog : AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()
        }

        holder.btnImgUpdate.setOnClickListener{
            val intent = Intent(context, UpdateActivity::class.java)

            intent.putExtra("newHirings", newHirings)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listBookings.size
    }


}

