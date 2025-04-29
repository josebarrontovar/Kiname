package com.example.kiname.data.impl

import android.util.Log
import com.example.kiname.data.remote.RetrofitClient
import com.example.kiname.data.remote.RetrofitService
import com.example.kiname.domain.model.Appoinment
import com.example.kiname.domain.repository.AppoinmentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class AppoinmentRepositoryImpl(
    private val retrofitClient: RetrofitService = RetrofitClient.instance
) : AppoinmentRepository {
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

    override suspend fun deleteAppointment(id: String): Result<Unit> {
        return try {
            firestore.collection("appointment").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveSheet(appointment: Appoinment) {
        try {
            // Creamos el mapa con todos los campos
            val data = mapOf(
                "nombre" to appointment.clientName,
                "apellido" to appointment.clientLastName,
                "telefono" to appointment.clientPhone,
                "email" to appointment.clientEmail,
                "fechaHora" to appointment.dateTime,
                "tratamiento" to appointment.treatment
            )

            // Enviamos la solicitud al script de Google Apps
            val response = retrofitClient.addAllData(data)

            if (response.isSuccessful) {
                Log.d("AppoinmentRepository", "Data added to sheet")
            } else {
                Log.e("AppoinmentRepository", "Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("AppoinmentRepository", "Error in saveSheet: ${e.message}")
        }
    }
}