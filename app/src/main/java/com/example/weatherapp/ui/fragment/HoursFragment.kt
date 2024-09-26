 package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.adapters.WeatherModel
import com.example.weatherapp.databinding.FragmentHoursBinding
import org.json.JSONArray
import org.json.JSONObject


 class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

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
         model.liveDataCurrent.observe(viewLifecycleOwner) {
             adapter.submitList(getHoursList(it))
             Log.d("My Log", "Hours: ${it.hours}")

         }
     }

     private fun initRcView() = with(binding) {
         rcView.layoutManager = LinearLayoutManager(activity)
         adapter = WeatherAdapter(null)
         rcView.adapter = adapter

     }

     private fun getHoursList (wItem: WeatherModel):List<WeatherModel> {
         //что бы доставать из JSON массива часы и другие данные фуекция будет с циклом

         // 1. прверащаем все в JSON массив
         val hoursArray = JSONArray(wItem.hours)

         // 2. подготавливаем список кот будем перебирать
         val list = ArrayList<WeatherModel>()

         //3. с помощью цикла перебираем WeatherModel и передаем это все в list
         for (i in 0 until hoursArray.length()) {
             val item = WeatherModel (
                wItem.city,
                 (hoursArray[i] as JSONObject).getString("time"),
                 (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                 (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                 (hoursArray[i] as JSONObject).getString("temp_c"),
                 "",
                 "",
                 "",


             )
             list.add(item)
         }


         return list
     }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()

    }
}