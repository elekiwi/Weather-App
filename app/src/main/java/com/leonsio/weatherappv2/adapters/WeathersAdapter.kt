package com.leonsio.weatherappv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leonsio.weatherappv2.R
import com.leonsio.weatherappv2.databinding.ItemWeatherListBinding
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.domain.models.convertTempToCelsius
import com.leonsio.weatherappv2.domain.models.formatDate

class WeathersAdapter : RecyclerView.Adapter<WeathersAdapter.WeatherViewHolder>(){

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Weather>() {

        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_weather_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val binding = ItemWeatherListBinding.bind(holder.itemView)

        val weather = differ.currentList[position]
        holder.itemView.apply {
            val formatTemp = String.format("%.1f", weather.convertTempToCelsius())
            "${formatTemp}º".also { binding.tvTemp.text = it }
            binding.tvDate.text = weather.formatDate()

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}