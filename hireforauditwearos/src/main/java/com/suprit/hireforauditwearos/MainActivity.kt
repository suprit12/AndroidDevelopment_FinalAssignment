package com.suprit.hireforauditwearos

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.repositoryapi.AccountantRepository
import com.suprit.hireforauditwearos.adapter.AccountantAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var rvAccountants : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on

        rvAccountants = findViewById(R.id.rvAccountants)

        loadAccountants()

            }

    private fun loadAccountants(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val accountantRepository = AccountantRepository()

                val response = accountantRepository.getAccountants()

                if(response.success == true){
                    val listAccountants : MutableList<Accountant> = response.data!!

                    withContext(Main){
                        val adapter = AccountantAdapter(listAccountants, this@MainActivity)

                        rvAccountants.layoutManager = LinearLayoutManager(this@MainActivity)
                        rvAccountants.adapter = adapter
                    }
                }
            }

            catch (ex:Exception){
                withContext(Main)
                { Toast.makeText(this@MainActivity, ex.toString(), Toast.LENGTH_SHORT).show() }
            }
        }
    }
}