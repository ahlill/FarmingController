package id.belajar.controller

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {
    private val myRef = Firebase.database("https://farmingcontroller-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    private val _data = MutableLiveData<ArrayList<MainModel>>()
    var data: LiveData<ArrayList<MainModel>> = _data
    private val _state = MutableLiveData<Boolean>()
    var state: LiveData<Boolean> = _state

    init {

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrData = arrayListOf<MainModel>()
                for (data: DataSnapshot in snapshot.child("data").children) {
                    val key = data.key
                    Log.d(TAG, "key: $key")
                    Log.d(TAG, "onDataChange1: ${data.value}")

                    arrData.add(MainModel(
                            data.key.toString(),
                            data.child("hum").value.toString(),
                            data.child("temp").value.toString()
                    ))
                }
                arrData.reverse()
                _data.value = arrData
                _state.value = snapshot.child("state").value.toString() == "1"
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun state(state: Int) {
        myRef.child("state").setValue(state)
    }

//    fun add(hum: String, temp: String) {
//        val key = System.currentTimeMillis().toString()
//        myRef.child("data").child(key).setValue(MainModel(hum, temp))
//    }
}