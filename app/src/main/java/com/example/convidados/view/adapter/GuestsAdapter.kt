package com.example.convidados.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.view.viewholder.GuestViewHolder

// A ViewHolder é quem tem a referência para o elementos de interface
class GuestsAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener: OnGuestListener

    // Esse método faz a criação do layout, para cada item da recyclerView esse onCreate é chamado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        //Esse item é usado para inflar a RecyclerView. Como não temos acesso direto ao layoutInflater
        //é necessário utilizar o parent para pegar o contexto. Logo nós instanciamos o Layout
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GuestViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged() //Método que sinaliza para a RecyclerView a alteração nos dados
    }

    fun attachListener(guestListener: OnGuestListener) {
        listener = guestListener
    }

}