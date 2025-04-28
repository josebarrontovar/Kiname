package com.example.kiname.presentation.ui.addappoinment

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kiname.databinding.ActivityAddAppointmentBinding
import com.example.kiname.domain.model.Appoinment

class AddAppointmentActivity : AppCompatActivity() {
    private val addAppointmentVM: AddAppointmentViewModel by viewModels()
    private lateinit var binding: ActivityAddAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding()
        initObservers()
    }

    private fun initObservers() {

        addAppointmentVM.stateDBInsert.observe(this) { isSubmitted ->
            if (isSubmitted) {
                Log.d("JGBT", "YES")
                finish()
            } else {
                Log.d("JGBT", "NO")
            }
        }
    }

    private fun setBinding() {
        binding.submitButton.setOnClickListener {
            val clientName = binding.clientNameEditText.text.toString()
            val clientLastName = binding.clientLastNameEditText.text.toString()
            val clientPhone = binding.clientPhoneEditText.text.toString()
            val clientEmail = binding.clientEmailEditText.text.toString()

            addAppointmentVM.updateFormData(
                Appoinment(
                    clientName = clientName,
                    clientLastName = clientLastName,
                    clientPhone = clientPhone,
                    clientEmail = clientEmail
                )
            )
            addAppointmentVM.submitForm()

        }


    }
}