package com.leonsio.weatherappv2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonsio.weatherappv2.R
import com.leonsio.weatherappv2.adapters.HomeAdapter
import com.leonsio.weatherappv2.databinding.FragmentHomeBinding
import com.leonsio.weatherappv2.ui.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        setupRecyclerView()
        subscribeToObservers()

        //value resource Livedata<Resource<List<Weather>>>
        viewModel.weatherLiveData.value
        //then when

        homeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("cityName", it.cityName)
            }
            findNavController().navigate(
                R.id.action_weatherFragment_to_detailWeatherFragment,
                bundle
            )
        }

        binding.sRefreshHome.setOnRefreshListener {
            viewModel.updateUI()
        }

        viewModel.updateUI()
    }

    fun subscribeToObservers() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { listWeather ->
            binding.sRefreshHome.isRefreshing = false
            homeAdapter.differ.submitList(listWeather.distinctBy { it.cityName }
                .sortedBy { it.cityName })
        }

    }

    fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

}
