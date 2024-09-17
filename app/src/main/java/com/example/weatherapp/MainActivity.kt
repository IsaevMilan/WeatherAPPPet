package com.example.weatherapp


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.databinding.ActivityWeatherBinding
import com.example.weatherapp.ui.fragment.MainFragment
import org.json.JSONObject

//const val API_KEY = "e25becb2a55144fdb0a42600242108"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.rootActivity, MainFragment.newInstance())
            .commit()



       /* binding.button2.setOnClickListener{
            getResult("London")

        }*/
    }
    /*private fun getResult(name: String){
        val url = "http://api.weatherapi.com/v1/current.json" +
                "?key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {response->
                val obj=JSONObject(response)
                val temp=obj.getJSONObject("current")
                Log.d("MyLog", "Response: ${temp.getString("temp_c")}")
            },
            {
                Log.d("MyLog", "Volley error: $it")

            },)
        queue.add(stringRequest)

    }*/
}


