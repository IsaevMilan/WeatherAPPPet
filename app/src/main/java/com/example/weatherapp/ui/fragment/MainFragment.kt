package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.adapters.VpAdapter
import com.example.weatherapp.adapters.WeatherModel
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.isPermitionsGranted
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val API_KEY = "e25becb2a55144fdb0a42600242108"

class MainFragment : Fragment() {
    private val flist = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Разрешение предоставлено, выполните нужное действие
                } else {
                    // Разрешение не предоставлено, обработайте отказ
                }
            }

        // Теперь можно вызывать метод checkPermission или другую логику, требующую pLauncher
        checkPermission()

        // Инициализация интерфейса после настройки разрешений
        init()
        udateCurrentCard()

        requestWeatherData("Moscow")

    }


    private fun init() = with(binding) {
        val adapter = VpAdapter(activity as AppCompatActivity, flist)
        vp.adapter = adapter
        //TabLayoutMediator
        TabLayoutMediator(tlTitle, vp) { tab, pos ->
            tab.text = tList[pos]
        }
    }.attach()

    private fun udateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMinTemp = "${it.maxTemp}°C/${it.minTemp}°C"
            tvData.text = it.time
            Picasso.get().load(it.imageUrl).into(imWeather)
            tvCity.text = it.city
            tvTemperature.text = it.currentTemp
            tvCondition.text = it.condition
            tvMaxMin.text = maxMinTemp

        }
    }

    //Функция разрешений
    private fun permissionListener() {
        pLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

    }

    private fun checkPermission() {
        if (!isPermitionsGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
        }
    }

    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            com.android.volley.Request.Method.GET,
            url,
            {

                    result ->
                parseWeatherData(result)
            },

            { error ->
                Log.d("My Log", "Error: $error")
            }

        )

        queue.add(stringRequest)

    }

    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        purseCurrentData(mainObject, list[0])
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        //get JSON Array
        val daysArray = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                //передаем пустоту
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONArray("hour").toString(),
            )
            list.add(item)
        }
        return list
    }

    private fun purseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel) {
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            weatherItem.hours,
        )
        model.liveDataCurrent.value = item
        Log.d("My Log", "City: ${item.maxTemp}")
        Log.d("My Log", "time: ${item.minTemp}")
        Log.d("My Log", "hours: ${item.hours}")


    }

    companion object {

        fun newInstance() = MainFragment()

    }

}