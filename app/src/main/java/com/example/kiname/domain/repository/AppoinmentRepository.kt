package com.example.kiname.domain.repository

import com.example.kiname.domain.model.Appoinment

interface AppoinmentRepository {
    suspend fun saveAppointment(appointment: Appoinment): Result<Unit>
    suspend fun getAllAppointment(): Result<List<Appoinment>>
    suspend fun deleteAppointment(id: String): Result<Unit>
    suspend fun saveSheet(appointment: Appoinment)
}