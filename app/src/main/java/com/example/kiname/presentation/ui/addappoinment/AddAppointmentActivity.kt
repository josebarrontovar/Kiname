package com.example.kiname.presentation.ui.addappoinment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kiname.databinding.ActivityAddAppointmentBinding
import com.example.kiname.domain.model.Appoinment
import com.example.kiname.presentation.components.DateTimePickerDialogFragment
import java.util.Calendar

class AddAppointmentActivity : AppCompatActivity() {
    private var appointmentId: String? = null
    private val addAppointmentVM: AddAppointmentViewModel by viewModels()
    private lateinit var binding: ActivityAddAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding()
        initObservers()
        getIntents()
    }

    private fun getIntents() {
        val clientEmail = intent.getStringExtra("clientEmail")
        val clientName = intent.getStringExtra("clientName")
        val clientLastName = intent.getStringExtra("clientLastName")
        val clientPhone = intent.getStringExtra("clientPhone")
        appointmentId = intent.getStringExtra("appointmentId")

        if (clientPhone != null && clientEmail != null && clientName != null && clientLastName != null) {
            binding.clientEmailEditText.setText(clientEmail)
            binding.clientNameEditText.setText(clientName)
            binding.clientLastNameEditText.setText(clientLastName)
            binding.clientPhoneEditText.setText(clientPhone)
        }
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
            val dateTime = binding.dateTimeEditText.text.toString()
            val treatment = binding.spinnerTreatment.selectedItem.toString()

            addAppointmentVM.updateFormData(
                Appoinment(
                    id = appointmentId,
                    clientName = clientName,
                    clientLastName = clientLastName,
                    clientPhone = clientPhone,
                    clientEmail = clientEmail,
                    dateTime = dateTime,
                    treatment = treatment
                )
            )
            addAppointmentVM.submitForm()
        }

        binding.dateTimeEditText.setOnClickListener {
            val dateTimePicker = DateTimePickerDialogFragment { calendar ->
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                        calendar.get(Calendar.YEAR)
                    }"
                val selectedTime =
                    "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
                binding.dateTimeEditText.setText("$selectedDate $selectedTime")
            }
            dateTimePicker.show(supportFragmentManager, "dateTimePicker")
        }

        val treatments = addAppointmentVM.getTreatments()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            treatments
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTreatment.adapter = adapter

        binding.spinnerTreatment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedTreatment = parent.getItemAtPosition(position) as String
                    // Maneja la selección aquí
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Maneja la no selección aquí, si es necesario
                }
            }

    }
}