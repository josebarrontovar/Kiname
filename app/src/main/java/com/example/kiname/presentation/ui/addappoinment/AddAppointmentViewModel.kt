package com.example.kiname.presentation.ui.addappoinment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiname.data.impl.AppoinmentRepositoryImpl
import com.example.kiname.domain.model.Appoinment
import com.example.kiname.domain.repository.AppoinmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAppointmentViewModel : ViewModel() {

    private val appoinmentRepository: AppoinmentRepository = AppoinmentRepositoryImpl()

    private val _formData = MutableLiveData<Appoinment>()
    val fromData: MutableLiveData<Appoinment> get() = _formData

    private val _stateDBInsert = MutableLiveData<Boolean>()
    val stateDBInsert: MutableLiveData<Boolean> get() = _stateDBInsert

    fun updateFormData(data: Appoinment) {
        _formData.value = data
    }

    fun submitForm() {
        val currentData = _formData.value ?: return
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                appoinmentRepository.saveAppointment(currentData)
            }
            response.onSuccess {
                _stateDBInsert.value = true
            }.onFailure { exception ->
                _stateDBInsert.value = false
            }
        }
    }

}

sealed class FormState {
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val error: String) : FormState()
}