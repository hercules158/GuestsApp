package com.example.convidados.view.viewholder

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.provider.CalendarContract.Colors
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.constants.ColorsConstants
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import kotlin.coroutines.coroutineContext

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        if (!guest.presence){
            bind.textName.setTextColor(Color.parseColor(ColorsConstants.COLOR.RED))
        } else {
            bind.textName.setTextColor(Color.parseColor(ColorsConstants.COLOR.BLACK))
        }
        bind.textName.setOnClickListener { 
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover o convidado?")
                .setPositiveButton("SIM") { dialog, wich ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("NÃO", null)
                .create()
                .show()
            true

        }
    }

}