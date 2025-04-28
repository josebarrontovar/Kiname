package com.example.kiname.data.impl

import com.example.kiname.domain.model.Appoinment
import com.example.kiname.domain.repository.AppoinmentRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppoinmentRepositoryImpl : AppoinmentRepository {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun saveAppointment(appointment: Appoinment): Result<Unit> {
        return try {
            firestore.collection("appointment")
                .add(appointment)
                .await() // Use the appropriate method to wait for the operation to complete
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllAppointment(): Result<List<Appoinment>> {
        return try {
            val collection = firestore.collection("appointment").get().await()
            val appointments = collection.toObjects(Appoinment::class.java)
            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}