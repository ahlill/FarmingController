package id.belajar.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.belajar.controller.databinding.DataItemBinding

class DataAdapter(private val dataItems: ArrayList<MainModel>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            DataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataItems[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataItems.size

    class ViewHolder(private var binding: DataItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        internal fun bind(data: MainModel?) = with(binding) {

            tvItemDate.text = "tanggal data"
            tvItemHum.text = data?.hum
            tvItemTemp.text = data?.temp
        }
    }
}
