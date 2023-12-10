package com.example.diaryplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.diaryplanner.Fragments.HomeFragment
import com.example.diaryplanner.Fragments.ProfileFragment
import com.example.diaryplanner.databinding.ActivityMainBinding
import com.example.diaryplanner.Fragments.TargetsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_targets -> replaceFragment(TargetsFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}