package br.com.gabriel.supermarktlist.model

import java.io.Serializable
import kotlin.Int as Int

public class Produto: Serializable {

    var id:Int = 0
        get() = field
    var produto: String
        get() = field
    var quantidade: Int = 0
        get() = field

    constructor(produto: String, quantidade: Int){
        this.produto = produto
        this.quantidade = quantidade
    }

    override fun toString(): String {
        return "${this.produto}    ${this.quantidade} unidade(s)"
    }
}