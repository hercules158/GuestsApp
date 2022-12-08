package com.example.convidados.constants

class DataBaseConstants private constructor() { //private constructor para ninguém instanciar

     object GUEST {
         const val ID = "guestid"
        const val TABLE_NAME = "Guest"
     }
    object COLUMNS {
        const val ID = "id"
        const val NAME = "name"
        const val PRESENCE = "presence"
    }

}