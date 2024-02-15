package com.example.mockuniversity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        Bnv.setupWithNavController(fragment.findNavController())
    }
}