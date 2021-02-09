package br.com.gabriel.supermarktlist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_produtos)
        setTitle(ConstantesListagemActivity.TITULO_BAR)
        this.inicializarCampos()
        this.verificarListagemSemProdutos()
        this.adicionarNovoProduto()

    }

    override fun onResume() {
        super.onResume()
        this.verificarListagemSemProdutos()
        this.listarProdutos()
        this.editarProduto()
    }

    private fun inicializarCampos(){
        botaoAdicionarProduto = findViewById(R.id.activity_listagem_produtos_adicionar_novo_produto)
        textoSemProdutosCadastrados = findViewById(R.id.activity_listagem_produtos_sem_produtos)
        listagemProdutos = findViewById(R.id.activity_listagem_produtos_lista)
    }

    private fun adicionarNovoProduto() {
        botaoAdicionarProduto.setOnClickListener {
            startActivity(Intent(this, FormularioProdutoActivity::class.java))
        }
    }

    private fun verificarListagemSemProdutos() {
        if (ProdutoDao.tamanhoListagem() > 0)
            textoSemProdutosCadastrados.setVisibility(View.INVISIBLE)
    }

    private fun listarProdutos(){
        val adapter: ArrayAdapter<Produto> = ArrayAdapter<Produto>(
            this
            , android.R.layout.simple_expandable_list_item_1
            , ProdutoDao.listagemProdutos
        )
        listagemProdutos.setAdapter(adapter)
    }

    private fun editarProduto(){
        listagemProdutos.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent:Intent = Intent(this,FormularioProdutoActivity::class.java)
            val alunoSelecionado:Produto = parent.getItemAtPosition(position) as Produto
            intent.putExtra(ConstantesListagemActivity.CHAVE_PRODUTO_SELECIONADO,alunoSelecionado as Serializable)
            startActivity(intent)
        }
            
        )
    }
}