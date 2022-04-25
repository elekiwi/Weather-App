package com.leonsio.weatherappv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonsio.weatherappv2.R
import com.leonsio.weatherappv2.databinding.ItemHomeListBinding
import com.leonsio.weatherappv2.domain.models.Weather

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Weather>() {

        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.cityName == newItem.cityName
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val binding = ItemHomeListBinding.bind(holder.itemView)

        val weather = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(weather.cityPic).into(binding.ivCityImage)
            binding.tvCityName.text = weather.cityName
            setOnClickListener {
                onItemClicklistener?.let { it(weather) }
            }
        }
    }

    private var onItemClicklistener : ((Weather) -> Unit)? = null

    fun setOnItemClickListener(listener: (Weather) -> Unit){
        onItemClicklistener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}