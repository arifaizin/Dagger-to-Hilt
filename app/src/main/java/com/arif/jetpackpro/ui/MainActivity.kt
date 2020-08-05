package com.arif.jetpackpro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arif.jetpackpro.R
import com.arif.jetpackpro.ui.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main_tab.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab)
        setSupportActionBar(toolbar)

        setupViewPager()
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

}