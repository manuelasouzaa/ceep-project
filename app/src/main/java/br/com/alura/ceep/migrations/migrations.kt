package br.com.alura.ceep.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.UUID

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        val tabelaNova = "Nota_nova"
        val tabelaAtual = "Nota"

        database.execSQL(
            """CREATE TABLE IF NOT EXISTS $tabelaNova (
                `id` TEXT PRIMARY KEY NOT NULL,
                `titulo` TEXT NOT NULL,
                `descricao` TEXT NOT NULL,
                `imagem` TEXT)"""
        )

        database.execSQL(
            """INSERT INTO $tabelaNova (id, titulo, descricao, imagem)
                SELECT id, titulo, descricao, imagem FROM $tabelaAtual"""
        )

        val cursor = database.query("SELECT * FROM $tabelaNova")
        while (cursor.moveToNext()) {
            val id = cursor.getString(
                cursor.getColumnIndex("id")
            )
            database.execSQL(
                """UPDATE $tabelaNova
                    SET id = '${UUID.randomUUID()}'
                    WHERE id = $id"""
            )
        }

        database.execSQL("DROP TABLE $tabelaAtual")

        database.execSQL("ALTER TABLE $tabelaNova RENAME TO $tabelaAtual")
    }

}