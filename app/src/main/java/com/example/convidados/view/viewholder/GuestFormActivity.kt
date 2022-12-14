package com.example.convidados.view.viewholder

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root) //Seta o conteúdo

        viewModel =
            ViewModelProvider(this).get(GuestFormViewModel::class.java) //O Owner do ciclo de vida é a nossa activity GuestForm

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked =
            true //Aqui definimos que o radio button começa marcado por padrão

        observer()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString() //Pega o nome que foi digitado
            val presence = binding.radioPresent.isChecked //Pega o valor do radioButton presente

            val model = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = presence
            }

            viewModel.save(model)
            finish()
        }
    }

    private fun observer() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this) {
            if (it != "Falha") {
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this,"Ops algo deu errado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}