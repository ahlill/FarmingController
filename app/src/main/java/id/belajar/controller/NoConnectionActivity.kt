package id.belajar.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.belajar.controller.databinding.ActivityNoConnectionBinding

class NoConnectionActivity : AppCompatActivity() {
    private val noConnectionActivityBinding by lazy {ActivityNoConnectionBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(noConnectionActivityBinding.root)

        noConnectionActivityBinding.btnRefresh.setOnClickListener{
            if (Helper().checkConnection(this)){
                finish()
            }else{
                Toast.makeText(applicationContext, "koneksimu bermasalah", Toast.LENGTH_SHORT).show()
            }
        }

    }
}