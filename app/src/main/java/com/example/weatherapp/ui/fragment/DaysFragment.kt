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
import com.example.weatherapp.databinding.FragmentDaysBinding
import org.json.JSONArray
import org.json.JSONObject

class DaysFragment : Fragment() {

    lateinit var binding: FragmentDaysBinding
    lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataList.observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
    }
        //метод для инициализации и заполнения ресайклера
    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter

    }

   /* private fun getDaysList(wItem: WeatherModel): List<WeatherModel> {
        //что бы доставать из JSON массива часы и другие данные фуекция будет с циклом

        // 1. прверащаем все в JSON массив
        val daysArray = JSONArray(wItem.hours)


        // 2. подготавливаем список кот будем перебирать
        val list = ArrayList<WeatherModel>()

        //3. с помощью цикла перебираем WeatherModel и передаем это все в list
        for (i in 0 until daysArray.length()) {
            val item = WeatherModel(
                wItem.city,
                (daysArray[i] as JSONObject).getString("time"),
                (daysArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                (daysArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                (daysArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                "",


                )
            list.add(item)
        }


        return list
    }*/

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()


    }
}