package com.example.kiname.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiname.databinding.ActivityHomeBinding
import com.example.kiname.presentation.components.AppointmentAdapter
import com.example.kiname.presentation.ui.addappoinment.AddAppointmentActivity

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdapter

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding()
        initRecycler()
        initDataAppointments()
        initObservers()
    }

    private fun initDataAppointments() {
        homeViewModel.getAllAppointments()
    }

    private fun initObservers() {
        homeViewModel.listAppointment.observe(this) {
            adapter.updateData(it)
        }


    }

    private fun initRecycler() {
        adapter = AppointmentAdapter(mutableListOf()) { id ->
            homeViewModel.deleteAppointment(id)
        }
        binding.rvAppointments.layoutManager = LinearLayoutManager(this)
        binding.rvAppointments.adapter = adapter
    }

    private fun setBinding() {
        binding.buttonFirst.setOnClickListener {
            goToAddAppoinment()
        }

    }

    private fun goToAddAppoinment() {
        val intent = Intent(this, AddAppointmentActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllAppointments()
    }
}