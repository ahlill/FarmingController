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

        if (position == 0){
            holder.bindDataHeader()
        }else{
            val data = dataItems[position - 1]
            holder.bindDataCell(data)
        }
    }

    override fun getItemCount(): Int = dataItems.size + 1

    class ViewHolder(private var binding: DataItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        internal fun bindDataCell(data: MainModel?) = with(binding) {
            val tanggal = Helper().getDateTime(data?.timeStamp)
            tvItemDate.setBackgroundResource(R.drawable.bg_table_content_cell)
            tvItemHum.setBackgroundResource(R.drawable.bg_table_content_cell)
            tvItemTemp.setBackgroundResource(R.drawable.bg_table_content_cell)

            tvItemDate.text = tanggal
            tvItemHum.text = data?.hum
            tvItemTemp.text = data?.temp
        }

        internal fun bindDataHeader() = with(binding){
            tvItemDate.setBackgroundResource(R.drawable.bg_table_header_cell)
            tvItemTemp.setBackgroundResource(R.drawable.bg_table_header_cell)
            tvItemHum.setBackgroundResource(R.drawable.bg_table_header_cell)

            tvItemDate.setText(R.string.table_head_content_date)
            tvItemTemp.setText(R.string.table_head_content_temperature)
            tvItemHum.setText(R.string.table_head_content_humidity)
        }
    }
}
