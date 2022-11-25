package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    //Extende de AndroidViewModel, pois como nosso getInstance precisa passar um context e a
    //ViewModel não possui context a gente precisa usar o AdnroidViewModel. A necessidade de passar
    //contexto veio lá do GuestDataBase que pede um contexto e todos que estavam antes precisaram
    //começar a passar esse contexto até chegar no GuestRepository

    private val repository = GuestRepository.getInstance(application)

    fun insert(guest: GuestModel) {
        repository.insert(guest)
    }

}