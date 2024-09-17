package com.example.weatherapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//adapter for TabLayout, getItemCount() for keeping elements of TabLayout, createFragment() position of current element
class VpAdapter(fa: FragmentActivity, private val list: List<Fragment>) : FragmentStateAdapter(fa){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list [position]
    }
}