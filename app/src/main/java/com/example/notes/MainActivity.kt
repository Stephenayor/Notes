package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupNavigation()
        }

        private fun setupNavigation(){
            val navController = findNavController(R.id.nav_host_fragment)
            setupActionBarWithNavController(navController,binding.drawerLayout)
            binding.navView.setupWithNavController(navController)
        }

        override fun onSupportNavigateUp(): Boolean {
            return NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment),binding.drawerLayout)
        }
    }
