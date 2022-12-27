package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

// A função dessa classe é a de fazer a conexão com o banco de dados, ela fornece uma conexão.

//class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

//Usando a notation @Database eu digo que este é meu DataBase dentro do construtor eu informo quem é
//minha entidade(tabela do banco) e a versão do banco
@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDataBase : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO

    companion object {
        private lateinit var INSTANCE: GuestDataBase

        fun getDataBase(context: Context): GuestDataBase {
            if (!::INSTANCE.isInitialized) {  //Verifica se o banco já foi instanciado
                synchronized(GuestDataBase::class) { //Esse syncronized não deixa que a variável
                    // seja instanciada em threads diferentes simultâneamente

                    //Logo abaixo é feita a instância do banco. É dessa forma que se instancia o Room
                    INSTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestdb")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DELETE FROM Guest")
            }

        }

    }

}