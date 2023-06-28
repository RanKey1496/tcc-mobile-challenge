package com.jimbo.jimboapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jimbo.jimboapp.data.response.Client
import com.jimbo.jimboapp.databinding.ItemClientBinding

class ClientsAdapter(
    private var clients: List<Client> = mutableListOf(),
    private var context: Context
) : RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val binding = ItemClientBinding.inflate(LayoutInflater.from(context), parent, false)
        return ClientsViewHolder(binding)
    }

    override fun getItemCount(): Int = clients.size

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    class ClientsViewHolder(
        private val binding: ItemClientBinding
    ): ViewHolder(binding.root) {

        fun bind(client: Client) {
            binding.idTextView.text = "Identificación ${client.identification}"
            binding.nameTextView.text = client.name
            binding.typeTextView.text = client.type
            binding.genderTextView.text = "Genero ${client.gender}"
        }
    }
}