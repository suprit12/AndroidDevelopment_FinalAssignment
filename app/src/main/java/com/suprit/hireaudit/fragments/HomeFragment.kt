package com.suprit.hireaudit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suprit.hireaudit.R
import com.suprit.hireaudit.adapter.AccountAdapter
import com.suprit.hireaudit.database.UserDB
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.repositoryapi.AccountantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var rvAccountants : RecyclerView

    private lateinit var ckAccounting : CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        rvAccountants = view.findViewById(R.id.rvAccountants)



        loadAccountants()

        return view
    }

    private fun loadAccountants(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val accountantRepository = AccountantRepository()

                val response = accountantRepository.getAccountants()

                if(response.success == true){
                    val listAccountants : ArrayList<Accountant> = response.data!!


                    context?.let { UserDB.getInstance(it).getAccountantDao().deleteAccountants() }

                    context?.let { UserDB.getInstance(it).getAccountantDao().registerAccountant(listAccountants) }

                    val accountant = context?.let { UserDB.getInstance(it).getAccountantDao().getAccountants() }

                    withContext(Main) {
                        rvAccountants.adapter = context?.let { AccountAdapter(accountant!!, it) }
                        val gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                        rvAccountants.layoutManager = gridLayoutManager
                    }
                }
            }
            catch (ex : Exception){
                withContext(Main){
                    Toast.makeText(context, ex.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    }


