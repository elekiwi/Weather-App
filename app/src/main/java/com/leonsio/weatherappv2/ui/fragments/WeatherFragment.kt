package com.leonsio.weatherappv2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonsio.weatherappv2.R
import com.leonsio.weatherappv2.adapters.WeathersAdapter
import com.leonsio.weatherappv2.databinding.FragmentWeatherBinding
import com.leonsio.weatherappv2.ui.viewmodels.WeatherViewModel
import com.leonsio.weatherappv2.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var weathersAdapter: WeathersAdapter
    val args: WeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        setupRecyclerView()
        subscribeToObservers()

    }

    fun subscribeToObservers(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { resource ->
            val listWeather = resource.data

            weathersAdapter.differ.submitList(listWeather!!.sortedBy { it.date }
                .filter { it.cityName == args.cityName })


        }
    }



    fun setupRecyclerView() {
        weathersAdapter = WeathersAdapter()
        binding.rvWeathers.apply {
            adapter = weathersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}