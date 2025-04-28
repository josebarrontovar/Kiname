package com.example.kiname.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiname.databinding.ItemAssignmentAppointmentsBinding
import com.example.kiname.domain.model.Appoinment

class AppointmentAdapter(private val items: MutableList<Appoinment>) :
    RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentViewHolder {

        val binding = ItemAssignmentAppointmentsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: AppointmentViewHolder,
        position: Int
    ) {
        val appointment = items[position]
        holder.bind(appointment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newItems: List<Appoinment>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}

public class AppointmentViewHolder(private val binding: ItemAssignmentAppointmentsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val tvTitle = binding.tvTitle

    private val tvSubtitle = binding.tvSubtitle
    private val customerName = binding.tvClientName
    private val customerPhone = binding.tvPhone

    fun bind(appointment: Appoinment) {
        tvTitle.text = appointment.clientEmail
        tvSubtitle.text = appointment.clientLastName
        customerName.text = appointment.clientName
        customerPhone.text = appointment.clientPhone

    }
}
