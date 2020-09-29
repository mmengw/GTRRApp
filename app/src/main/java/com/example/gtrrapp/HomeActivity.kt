package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gtrr.fragments.newsFeedFragment
import com.example.gtrrapp.fragments.AchievementsFragment
import com.example.gtrrapp.fragments.LogFragment
import com.example.gtrrapp.fragments.UserFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        verifyUserIsLoggedIn()

        val newsFeedFragment = newsFeedFragment()
        val logFragment = LogFragment()
        val achievementsFragment = AchievementsFragment()
        val profileFragment = UserFragment()

        makeCurrentFragment(newsFeedFragment)

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

    //TO CHECK IF THE USER HAVE BEEN AUTHENTICATED
    private fun verifyUserIsLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent (this, LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    //FUNCTION FOR LOGOUT
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.menu_LogOut){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }else{
            val intent = Intent (this, UpdateActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    //DISPLAY LOG OUT BUTTON ON THE TOP NAV MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    //Display current
    private fun makeCurrentFragment (fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper,fragment)
            commit()
        }

}
