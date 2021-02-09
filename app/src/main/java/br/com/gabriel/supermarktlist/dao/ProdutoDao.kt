package br.com.gabriel.supermarktlist.dao

import android.util.Log
import br.com.gabriel.supermarktlist.model.Produto

object ProdutoDao {
    @JvmStatic val listagemProdutos: ArrayList<Produto> = ArrayList()
    @JvmStatic var idContador: Int = 0;

    @JvmStatic fun adicionarProduto(produto: Produto){
        listagemProdutos.add(produto)
        produto.id = idContador
        incrementarId()
    }

    private fun incrementarId(){
        idContador++
    }

    fun tamanhoListagem(): Int{
        return listagemProdutos.size
    }

    fun editarProduto(produto: Produto){
        listagemProdutos.forEach {
            if(it.id == produto.id){
                var posicao:Int = it.id
                listagemProdutos.set(posicao,produto)
            }
        }
    }
}