package com.arif.daggerhilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arif.daggerhilt.R
import com.arif.daggerhilt.ui.adapter.SectionsPagerAdapter
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