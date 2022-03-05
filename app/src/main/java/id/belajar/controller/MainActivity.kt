package id.belajar.controller

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import id.belajar.controller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        supportActionBar?.hide()

        mainViewModel.state.observe(this, { state ->
            state(state)
            if (state == true) {
                mainViewModel.data.observe(this, { data ->
                    initHum(data.last().hum)
                    initTemp(data.last().temp)
                })
            } else {
                initFalse()
            }
        })

        activityMainBinding.btnState.setOnClickListener {
            val state = if (mainViewModel.state.value == true) 0 else 1
            mainViewModel.state(state) //mengirim data ke server
        }
    }

    private fun initHum(hum: String) {
        if (hum != "null") {
            activityMainBinding.pbHum.progress = hum.toInt()
            activityMainBinding.tvHum.text = "$hum %"
        }
    }

    private fun initTemp(temp: String) {
        if (temp != "null") {
            activityMainBinding.pbTemp.progress = temp.toInt()
            activityMainBinding.tvTemp.text = "$temp C"
        }
    }

    private fun initFalse() {
        activityMainBinding.pbHum.progress = 0
        activityMainBinding.tvHum.text = "- - %"

        activityMainBinding.pbTemp.progress = 0
        activityMainBinding.tvTemp.text = "- - C"
    }

    private fun state(state: Boolean?) {
        activityMainBinding.btnState.text = if (state == true) "OFF" else "ON"
    }

}