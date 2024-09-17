 package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.adapters.WeatherModel
import com.example.weatherapp.databinding.FragmentHoursBinding


 class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         initRcView()
     }

     private fun initRcView() = with(binding) {
         rcView.layoutManager = LinearLayoutManager(activity)
         adapter = WeatherAdapter()
         rcView.adapter = adapter
         val list= listOf(
             WeatherModel(
                 "", "12:00","Sunny","",
                 "28°C", "28°C","17°C",
                 ""

             ),
         WeatherModel(
             "", "13:00","Sunny","",
             "23°C", "28°C","17°C",
             ""

         ),
         WeatherModel(
             "", "14:00","Cloudy","",
             "22°C", "28°C","17°C",
             ""

         )
         )
         adapter.submitList(list)
     }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()

    }
}