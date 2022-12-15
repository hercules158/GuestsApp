package com.example.convidados.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel
import com.example.convidados.view.viewholder.GuestFormActivity

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter() //Instanciando aqui para usar lá embaixo no adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this)[GuestsViewModel::class.java]
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        // Os pacotes adapter e viewholder foram criador para fazer com que a RecyclerView funcione,
        // ela sempre precisa disso para conseguir ser usada.
        // Layout da recyclerView, fala como ela se comporta
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        // Adapter que faz a junção entre a lista e a interface
        binding.recyclerGuests.adapter = adapter

        val listener = object : OnGuestListener {

            override fun onClick(id: Int) {
                //Essa parte aqui é a responsável por fazer a edição dos convidados
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }
        }
        adapter.attachListener(listener)
        observer()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)

        }
    }
}