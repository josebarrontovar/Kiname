package com.example.kiname.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiname.data.impl.AppoinmentRepositoryImpl
import com.example.kiname.domain.model.Appoinment
import com.example.kiname.domain.repository.AppoinmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val repository: AppoinmentRepository = AppoinmentRepositoryImpl()

    private val _listAppointment = MutableLiveData<List<Appoinment>>()
    val listAppointment: MutableLiveData<List<Appoinment>> get() = _listAppointment


    fun getAllAppointments() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getAllAppointment()
            }
            result.onSuccess { appointments ->
                listAppointment.value = appointments
            }.onFailure { exception ->
                listAppointment.value = null
            }
        }
    }

    fun deleteAppointment(id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.deleteAppointment(id)
            }
            result.onSuccess {
                getAllAppointments()
            }.onFailure { exception ->
                listAppointment.value = null
            }
        }
    }

}