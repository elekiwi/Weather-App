package com.leonsio.weatherappv2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonsio.weatherappv2.R
import com.leonsio.weatherappv2.adapters.HomeAdapter
import com.leonsio.weatherappv2.databinding.FragmentHomeBinding
import com.leonsio.weatherappv2.ui.viewmodels.WeatherViewModel
import com.leonsio.weatherappv2.util.InternetConnection
import com.leonsio.weatherappv2.util.Status
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
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { resource ->


            val listWeather = resource.data

            when (resource.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    homeAdapter.differ.submitList(listWeather!!.distinctBy { it.cityName }
                        .sortedBy { it.cityName })
                }
                Status.ERROR -> {
                    hideProgressBar()
                    if (listWeather!!.isEmpty()){
                        //show empty page
                    }
                    homeAdapter.differ.submitList(listWeather!!.distinctBy { it.cityName }
                        .sortedBy { it.cityName })

                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        }

    }

    fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun hideProgressBar() {
        binding.homeProgressBar.visibility = View.INVISIBLE
        binding.sRefreshHome.isRefreshing = false
    }

    private fun showProgressBar() {
        binding.homeProgressBar.visibility = View.VISIBLE
    }

    /*private fun checkConnectivity() {
        val connectivity = InternetConnection(this)
        connectivity.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.message = "Internet is connected!!!"
                binding.internetStatusTV.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.internet_conn_green
                    )
                )
                Handler(Looper.myLooper()!!).postDelayed({
                    binding.networkStatus = true
                    startNextActivity()
                    finish()
                }, 2000)
            } else {
                binding.networkStatus = false
                binding.message = "No internet connection!!!"
                binding.internetStatusTV.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.internet_conn_red
                    )
                )
            }
        })
    }*/

}
