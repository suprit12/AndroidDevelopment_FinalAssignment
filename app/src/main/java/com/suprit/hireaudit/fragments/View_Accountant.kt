package com.suprit.hireaudit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.suprit.hireaudit.R


class View_Accountant : Fragment() {
    private lateinit var viewAccountantLayout : LinearLayout
    private lateinit var accountantImage : ImageView
    private lateinit var accountantName : TextView
    private lateinit var accountantEmail : TextView
    private lateinit var accountantGender : TextView
    private lateinit var accountantExp : TextView
    private lateinit var accountantMobNo : TextView
    private lateinit var btnBook : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view__accountant, container, false)

        viewAccountantLayout = view.findViewById(R.id.viewAccountantLayout)
        accountantImage = viewAccountantLayout.findViewById(R.id.accountantImage)
        accountantName = viewAccountantLayout.findViewById(R.id.accountantName)
        accountantEmail = viewAccountantLayout.findViewById(R.id.accountantEmail)
        accountantGender = viewAccountantLayout.findViewById(R.id.accountantGender)
        accountantExp = viewAccountantLayout.findViewById(R.id.accountantExp)
        accountantMobNo = viewAccountantLayout.findViewById(R.id.accountantMob)
        btnBook = viewAccountantLayout.findViewById(R.id.btnBook)


        return view
    }


}