package com.example.kiname.data.impl

import com.example.kiname.domain.model.Appoinment
import com.example.kiname.domain.repository.AppoinmentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppoinmentRepositoryImpl @Inject constructor() : AppoinmentRepository {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun saveAppointment(appointment: Appoinment): Result<Unit> {
        return try {
            if (appointment.id != null) {
                firestore.collection("appointment")
                    .document(appointment.id!!)
                    .set(appointment)
                    .await()
            } else {
                val docRef = firestore.collection("appointment")
                    .document()
                appointment.id = docRef.id
                docRef.set(appointment).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllAppointment(): Result<List<Appoinment>> {
        return try {
            val collection = firestore.collection("appointment").get().await()
            val appointments = mutableListOf<Appoinment>()
            for (doc in collection.documents) {
                val appointment = doc.toObject<Appoinment>()
                appointment?.id = doc.id
                appointment?.let {
                    appointments.add(it)
                }
            }
            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}