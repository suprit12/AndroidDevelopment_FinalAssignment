package com.suprit.hireaudit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suprit.hireaudit.R
import com.suprit.hireaudit.adapter.HireAdapter
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.repository.HireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class WorkFragment : Fragment() {
    private lateinit var rvHirings : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_work, container, false)

        rvHirings = view.findViewById(R.id.rvHirings)

        loadMyAccountants()

        return view
    }


    private fun loadMyAccountants(){



        CoroutineScope(Dispatchers.IO).launch {
            try {

                val hireRepository = HireRepository()

                val response = hireRepository.getMyAccountants()



                if (response.success == true){
                    var listHire : MutableList<HireModel> = response.hireData!!


                    withContext(Main){
                        val adapter = HireAdapter(listHire, context!!)

                        rvHirings.layoutManager = LinearLayoutManager(context)

                        rvHirings.adapter = adapter
                    }
                }
            }

            catch (ex : Exception){
                withContext(Main){
                    Toast.makeText(context, ex.toString() , Toast.LENGTH_SHORT).show()
                }

            }
        }
    }




}