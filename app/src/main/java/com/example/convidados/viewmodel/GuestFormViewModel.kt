package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    //Extende de AndroidViewModel, pois como nosso getInstance precisa passar um context e a
    //ViewModel não possui context a gente precisa usar o AdnroidViewModel. A necessidade de passar
    //contexto veio lá do GuestDataBase que pede um contexto e todos que estavam antes precisaram
    //começar a passar esse contexto até chegar no GuestRepository

    private val repository = GuestRepository(application)
    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private val _saveGuest = MutableLiveData<String>()
    var saveGuest: LiveData<String> = _saveGuest

    fun save(guest: GuestModel) {
        if (guest.id == 0) {
            if (repository.insert(guest)){
                _saveGuest.value = "Inserção com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }
        } else {
            if (repository.update(guest)) {
                _saveGuest.value = "Atualização com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }
        }
    }

    fun get(id: Int){
        guestModel.value = repository.get(id)
    }

}