package br.com.gabriel.supermarktlist.model

import java.io.Serializable

data class ProdutoTeste(
    val id:Integer,
    val produto: String,
    var quantidade: Int
): Serializable {

    override fun toString(): String {
        return "${this.produto}    ${this.quantidade} unidade(s)"
    }
}