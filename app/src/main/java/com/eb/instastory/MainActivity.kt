package com.eb.instastory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eb.instastory.databinding.ActivityMainBinding
import com.eb.instastory.utils.DummyStoryData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storyViewPager.setUsers(supportFragmentManager, DummyStoryData.getStoryUsers())
    }
}