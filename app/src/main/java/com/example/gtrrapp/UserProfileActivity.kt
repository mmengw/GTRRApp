package com.example.gtrrapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gtrrapp.fragments.AchievementsFragment
import com.example.gtrrapp.fragments.LogFragment
import com.example.gtrrapp.fragments.NewsFeedFragment
import com.example.gtrrapp.fragments.UserFragment
import kotlinx.android.synthetic.main.activity_home.*

class UserProfileActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)

        val newsFeedFragment = NewsFeedFragment()
        val logFragment = LogFragment()
        val achievementsFragment = AchievementsFragment()
        val profileFragment = UserFragment()

        makeCurrentFragment(profileFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_newsfeed -> makeCurrentFragment(newsFeedFragment)
                R.id.ic_Log -> makeCurrentFragment(logFragment)
                R.id.ic_achievements -> makeCurrentFragment(achievementsFragment)
                R.id.ic_user -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }
    //Display current
    private fun makeCurrentFragment (fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
}