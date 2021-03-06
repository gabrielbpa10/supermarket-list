package br.com.gabriel.supermarktlist.model

import java.io.Serializable
import kotlin.Int as Int

data class Produto(
    var produto: String,
    var quantidade: Int
): Serializable {
    var id:Int? = null

    override fun toString(): String {
        return "${this.produto}    ${this.quantidade} unidade(s)"
    }
}