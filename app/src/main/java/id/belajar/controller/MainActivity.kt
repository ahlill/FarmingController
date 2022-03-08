package id.belajar.controller

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.belajar.controller.Helper.ProgressBarAnimation
import id.belajar.controller.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var mProgressAnimationHum: ProgressBarAnimation
    private lateinit var mProgressAnimationTemp: ProgressBarAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        if (!Helper().checkConnection(this)){
            noConnection()
        }

        initProgressBar()
        mainViewModel.state.observe(this, { state ->
            state(state)
            if (state == true) {
                mainViewModel.data.observe(this, { data ->
                    initHum(data[0].hum)
                    initTemp(data[0].temp)
                    showData(data)
                })
            } else {
                initFalse()
            }
        })

        activityMainBinding.btnPower.setOnClickListener {
            if (Helper().checkConnection(this)){
                val state = if (mainViewModel.state.value == true) 0 else 1
                mainViewModel.state(state) //mengirim data ke server
            }else{
                noConnection()
            }

        }
    }

    private fun initHum(hum: String) {
        if (hum != "null") {
            val mHum = hum.toInt()
            mProgressAnimationHum.setProgress(mHum)
            with(activityMainBinding) {
                tvHum.text = "$mHum %"
                tvHumKeterangan.text = when (mHum) {
                    in 0..60 -> {
                        "rendah"
                    }
                    in 60..80 -> {
                        "normal"
                    }
                    in 80..100 -> {
                        "tinggi"
                    }
                    else -> {
                        "Error"
                    }
                }
            }
        }
    }

    private fun initTemp(temp: String) {
        if (temp != "null") {
            val mTemp = temp.toInt()
            mProgressAnimationTemp.setProgress(mTemp)
            with(activityMainBinding) {
                tvTemp.text = "$mTemp ℃"
                tvTempKeterangan.text = when (mTemp) {
                    in 0..25 -> {
                        "rendah"
                    }
                    in 25..37 -> {
                        "normal"
                    }
                    in 37..100 -> {
                        "tinggi"
                    }
                    else -> {
                        "Error"
                    }
                }
            }
        }
    }

    private fun initFalse() {
        mProgressAnimationHum.setProgress(0)
        activityMainBinding.tvHum.text = "- - %"

        mProgressAnimationTemp.setProgress(0)
        activityMainBinding.tvTemp.text = "- - ℃"
    }

    private fun initProgressBar() {
        mProgressAnimationHum = ProgressBarAnimation(activityMainBinding.pbHum, 1000)
        mProgressAnimationTemp = ProgressBarAnimation(activityMainBinding.pbTemp, 1000)
    }

    private fun state(state: Boolean?) {
        activityMainBinding.btnPower.setImageResource(if (state == true) R.drawable.ic_power_on else R.drawable.ic_power_off)
    }

    private fun showData(dataItems: ArrayList<MainModel>) = with(activityMainBinding) {
        rvData.layoutManager = LinearLayoutManager(this@MainActivity)
        rvData.setHasFixedSize(true)
        val adapter = DataAdapter(dataItems)
        rvData.adapter = adapter
    }

    private fun noConnection(){
        val i = Intent(this, NoConnectionActivity::class.java)
        startActivity(i)
    }

}