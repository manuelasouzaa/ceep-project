package br.com.alura.ceep.webclient

import br.com.alura.ceep.model.Nota

class NotaWebCient {

    suspend fun buscaTodas(): List<Nota> {
        val notasResposta = RetrofitInicializador().notaService.buscaTodas()
        val notas = notasResposta.map { notaResposta ->
            notaResposta.nota
        }
        return notas
    }

}