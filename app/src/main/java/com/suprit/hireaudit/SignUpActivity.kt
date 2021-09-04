package com.suprit.hireaudit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suprit.hireaudit.adapter.ViewPagerAdapter
import com.suprit.hireaudit.fragments.AccountantSignUpFragment
import com.suprit.hireaudit.fragments.UserSignUpFragment

class SignUpActivity : AppCompatActivity() {
    private lateinit var listTitle : ArrayList<String>
    private lateinit var listFragment : ArrayList<Fragment>
    private lateinit var signUpTab : TabLayout
    private lateinit var signUpView : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpView = findViewById(R.id.signUpView)
        signUpTab = findViewById(R.id.signUpTab)

        addView()

        val adapter = ViewPagerAdapter(listFragment, supportFragmentManager, lifecycle)

        signUpView.adapter = adapter

        TabLayoutMediator(signUpTab, signUpView){
            tab, position ->
                tab.text = listTitle[position]
        }.attach()


    }

    private fun addView(){
        listTitle = ArrayList()

        listTitle.add("Users")
        listTitle.add("Accountant")

        listFragment = ArrayList()

        listFragment.add(UserSignUpFragment())
        listFragment.add(AccountantSignUpFragment())
    }



}