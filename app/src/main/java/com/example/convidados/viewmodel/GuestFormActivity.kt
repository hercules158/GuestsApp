package com.example.convidados.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root) //Seta o conteúdo

        viewModel =
            ViewModelProvider(this).get(GuestFormViewModel::class.java) //O Owner do ciclo de vida é a nossa activity GuestForm

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked =
            true //Aqui definimos que o radio button começa marcado por padrão
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString() //Pega o nome que foi digitado
            val presence = binding.radioPresent.isChecked //Pega o valor do radioButton presente

            val model = GuestModel(0, name, presence)
            viewModel.insert(model)
        }
    }
}