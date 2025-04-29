package com.example.kiname.presentation.components

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiname.databinding.ItemAssignmentAppointmentsBinding
import com.example.kiname.domain.model.Appoinment
import com.example.kiname.presentation.ui.addappoinment.AddAppointmentActivity

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
        val filteredItems =
            newItems.filter { it.clientEmail.isNotEmpty() && it.clientPhone.isNotEmpty() }
        if(filteredItems.isNotEmpty()) {
            items.addAll(filteredItems)
            notifyDataSetChanged()
        }
    }

}

public class AppointmentViewHolder(private val binding: ItemAssignmentAppointmentsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val tvTitle = binding.tvTitle

    private val tvSubtitle = binding.tvSubtitle
    private val customerName = binding.tvClientName
    private val customerPhone = binding.tvPhone
    private val buttonEdit = binding.btnEdit

    fun bind(appointment: Appoinment) {
        tvTitle.text = appointment.clientEmail
        tvSubtitle.text = appointment.clientLastName
        customerName.text = appointment.clientName
        customerPhone.text = appointment.clientPhone

        buttonEdit.setOnClickListener {
            val context = binding.root.context
            val intent = Intent(context, AddAppointmentActivity::class.java).apply {
                putExtra("clientEmail", appointment.clientEmail)
                putExtra("clientLastName", appointment.clientLastName)
                putExtra("clientName", appointment.clientName)
                putExtra("clientPhone", appointment.clientPhone)
                putExtra("appointmentId", appointment.id)
            }
            context.startActivity(intent)
        }
    }

}
