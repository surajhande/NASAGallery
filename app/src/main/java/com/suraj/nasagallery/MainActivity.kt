package com.suraj.nasagallery

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.suraj.nasagallery.databinding.ActivityMainBinding
import com.suraj.nasagallery.ui.GalleryFragment

class MainActivity : FragmentActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, GalleryFragment.newInstance())
            .commit()
    }
}