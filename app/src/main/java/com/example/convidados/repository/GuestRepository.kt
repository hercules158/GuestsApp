package com.example.convidados.repository

import android.content.Context
import com.example.convidados.model.GuestModel

// Classe responsável por fazer a alteração ou inserção de dados no banco, ele é quem lida com os dados.

class GuestRepository(context: Context) {

    private val guestDataBase = GuestDataBase.getDataBase(context).guestDAO() //Abre acesso com a camada DAO

    /*
    // Singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {    // Essa função serve para somente
            // instanciar a variavel repository caso ela ainda não tenha sido instanciada
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
    }*/

    fun insert(guest: GuestModel): Boolean {
        return guestDataBase.insert(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return guestDataBase.update(guest) > 0
    }

    fun delete(id: Int) {
        val guest = get(id)
       guestDataBase.delete(guest)
    }

    fun getAll(): List<GuestModel> {
        return guestDataBase.getAll()
    }

    fun get(id: Int): GuestModel {
       return guestDataBase.get(id)
    }

    fun getPresent(): List<GuestModel> {
        return guestDataBase.getPresent()
    }

    fun getAbsent(): List<GuestModel> {
        return guestDataBase.getAbsent()
    }

}