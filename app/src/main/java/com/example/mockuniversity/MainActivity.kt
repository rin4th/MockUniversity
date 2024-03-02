package com.example.mockuniversity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = findViewById<FragmentContainerView>(R.id.fr_bnv)
        val Bnv = findViewById<BottomNavigationView>(R.id.bnv_main)

        // Set up navigation
        val navController = fragment.findNavController()
        Bnv.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.courseFragment2) { // Replace with your detail fragment's ID
                Bnv.visibility = View.GONE
            } else {
                Bnv.visibility = View.VISIBLE
            }
        }
    }
}