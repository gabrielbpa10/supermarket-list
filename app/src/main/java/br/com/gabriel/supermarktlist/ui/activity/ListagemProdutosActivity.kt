package br.com.gabriel.supermarktlist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import br.com.gabriel.supermarktlist.R
import br.com.gabriel.supermarktlist.dao.ProdutoDao
import br.com.gabriel.supermarktlist.model.Produto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class ListagemProdutosActivity : AppCompatActivity() {
    private lateinit var textoSemProdutosCadastrados: TextView
    private lateinit var listagemProdutos: ListView
    private lateinit var botaoAdicionarProduto: FloatingActionButton
    private var adapter: ArrayAdapter<Produto>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_produtos)
        setTitle(ConstantesListagemActivity.TITULO_BAR)
        this.inicializarCampos()
        this.adicionarNovoProduto()
    }

    override fun onResume() {
        super.onResume()
        this.listarProdutos()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add("Remover")
        menu?.add("Editar")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.i("Nome Menu: ",item.title.toString())
        when(item.title){
            "Remover" -> removerProduto(item)
            "Editar" -> editaProduto(item)
        }
        return super.onContextItemSelected(item)
    }

    private fun editaProduto(item: MenuItem){
        var menuInfo:AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var produtoSelecionado: Produto? = adapter?.getItem(menuInfo.position)
        val intent:Intent = Intent(this,FormularioProdutoActivity::class.java)
        intent.putExtra(ConstantesListagemActivity.CHAVE_PRODUTO_SELECIONADO,produtoSelecionado as Serializable)
        startActivity(intent)
    }

    private fun removerProduto(item: MenuItem){
        var menuInfo:AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var produtoSelecionado: Produto? = adapter?.getItem(menuInfo.position)
        adapter?.remove(produtoSelecionado)
        ProdutoDao.removerProduto(produtoSelecionado)
        verificarListagemSemProdutos()
    }

    private fun inicializarCampos(){
        botaoAdicionarProduto = findViewById(R.id.activity_listagem_produtos_adicionar_novo_produto)
        textoSemProdutosCadastrados = findViewById(R.id.activity_listagem_produtos_sem_produtos)
        listagemProdutos = findViewById(R.id.activity_listagem_produtos_lista)
        adapter = ArrayAdapter<Produto>(this, android.R.layout.simple_expandable_list_item_1)
    }

    private fun adicionarNovoProduto() {
        botaoAdicionarProduto.setOnClickListener {
            startActivity(Intent(this, FormularioProdutoActivity::class.java))
        }
    }

    private fun verificarListagemSemProdutos() {
        if (ProdutoDao.tamanhoListagem() > 0)
            textoSemProdutosCadastrados.setVisibility(View.INVISIBLE)
        else if(ProdutoDao.tamanhoListagem() == 0)
                textoSemProdutosCadastrados.setVisibility(View.VISIBLE)

    }

    private fun listarProdutos(){
        verificarListagemSemProdutos()
        adapter?.clear()
        adapter?.addAll(ProdutoDao.listagemProdutos)
        listagemProdutos.setAdapter(adapter)
        registerForContextMenu(listagemProdutos)
    }
}