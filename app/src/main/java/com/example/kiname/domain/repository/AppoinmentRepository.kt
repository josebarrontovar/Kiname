package com.example.kiname.domain.repository

import com.example.kiname.domain.model.Appoinment

interface AppoinmentRepository {
    suspend fun saveAppointment(appointment: Appoinment): Result<Unit>
    suspend fun getAllAppointment(): Result<List<Appoinment>>
}