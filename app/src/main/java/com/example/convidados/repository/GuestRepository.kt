package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

// Classe responsável por fazer a alteração ou inserção de dados no banco, ele é quem lida com os dados.

class GuestRepository(context: Context) {

    private val guestDataBase = GuestDataBase(context) //Abre a conexão com o banco

    // Singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {    // Essa função serve para somente
            // instanciar a variavel repository caso ela ainda não tenha sido instanciada
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values =
                ContentValues() //Essa classe é usada para armazenar um conjunto de valores que
            // o ContentResolver pode processar. Assim podemos utilizar os métodos da classe ContentValues
            values.put(DataBaseConstants.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.COLUMNS.NAME, guest.name)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase //Abre a conexão com o banco de dados
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues() //Preenche os valores
            values.put(DataBaseConstants.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.COLUMNS.NAME, guest.name)

            val selection = DataBaseConstants.COLUMNS.ID + " = ?" //Crítério necessário
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db =
                guestDataBase.writableDatabase //Abre a conexão com o banco de dados para atualizar
            // os valores(Escrever ou Removeer)

            val selection = DataBaseConstants.COLUMNS.ID + " = ?" //Crítério necessário
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }


    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase //Abre conexão com o banco no modo leitura

            val projection = arrayOf(
                DataBaseConstants.COLUMNS.ID,
                DataBaseConstants.COLUMNS.NAME,
                DataBaseConstants.COLUMNS.PRESENCE
            )

            //O cursor é como se fosse o cursor de um mouse que vai selecionando as linhas da tabela
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    //Cursor.getInt é qual o tipo de dado estamos pegando, getColumnIndex irá retornar o
                    // índice da coluna da tabela onde está a string informada.
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1)) //Aqui preenchemos a lista
                }
            }

            cursor.close() //Precisamos fechar o cursor depois de utilizá-lo
        } catch (e: Exception) {
            return list
        }
        return list

    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase //Abre conexão com o banco no modo leitura

            //O cursor é como se fosse o cursor de um mouse que vai selecionando as linhas da tabela
            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    //Cursor.getInt é qual o tipo de dado estamos pegando, getColumnIndex irá retornar o
                    // índice da coluna da tabela onde está a string informada.
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1)) //Aqui preenchemos a lista
                }
            }

            cursor.close() //Precisamos fechar o cursor depois de utilizá-lo
        } catch (e: Exception) {
            return list
        }
        return list

    }

    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase //Abre conexão com o banco no modo leitura

            //O cursor é como se fosse o cursor de um mouse que vai selecionando as linhas da tabela
            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    //Cursor.getInt é qual o tipo de dado estamos pegando, getColumnIndex irá retornar o
                    // índice da coluna da tabela onde está a string informada.
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1)) //Aqui preenchemos a lista
                }
            }

            cursor.close() //Precisamos fechar o cursor depois de utilizá-lo
        } catch (e: Exception) {
            return list
        }
        return list

    }

}