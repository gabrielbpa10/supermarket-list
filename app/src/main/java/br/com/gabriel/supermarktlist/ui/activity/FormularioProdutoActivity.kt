package br.com.gabriel.supermarktlist.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import br.com.gabriel.supermarktlist.R
import br.com.gabriel.supermarktlist.dao.ProdutoDao
import br.com.gabriel.supermarktlist.model.Produto

class FormularioProdutoActivity : AppCompatActivity() {
    private lateinit var campoProduto: EditText
    private lateinit var campoQuantidade: EditText
    private lateinit var botaoSalvar: Button
    private var produto: Produto? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_produto)
        inicializarCampos()
        val intent:Intent = getIntent()

        if(intent.hasExtra(ConstantesFormularioActivity.CHAVE_PRODUTO_SELECIONADO)){
            setTitle(ConstantesFormularioActivity.TITULO_BAR_EDITA_PRODUTO)
            produto = intent.getSerializableExtra(ConstantesFormularioActivity.CHAVE_PRODUTO_SELECIONADO) as Produto
            preencharCampos()
        } else{
            setTitle(ConstantesFormularioActivity.TITULO_BAR_NOVO_PRODUTO)
        }
        salvarProduto()
    }

    private fun inicializarCampos(){
        campoProduto = findViewById<EditText>(R.id.activity_novo_produto_nome_produto)
        campoQuantidade = findViewById<EditText>(R.id.activity_novo_produto_quantidade)
        botaoSalvar = findViewById<Button>(R.id.activity_novo_produto_salvar)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun salvarProduto(){
        botaoSalvar.setOnClickListener(View.OnClickListener {
            if(verificarCamposEmBranco(campoProduto, campoQuantidade) == false){
                if(produto == null) {
                    incrementarNovoProduto();
                    Toast.makeText(this,ConstantesFormularioActivity.TEXTO_PRODUTO_CADASTRADO, Toast.LENGTH_SHORT).show()
                } else {
                    editarProdutoDaLista()
                    ProdutoDao.editarProduto(produto!!)
                    Toast.makeText(this, ConstantesFormularioActivity.TEXTO_PRODUTO_EDITADO, Toast.LENGTH_SHORT).show()
                }
                finish();

            } else Toast.makeText(this,ConstantesFormularioActivity.TEXTO_EM_BRANCO, Toast.LENGTH_LONG).show()
        })
    }

    private fun verificarCamposEmBranco(campoProduto: EditText, campoQuantidade: EditText): Boolean{
        if(!campoProduto.getText().toString().equals("") && !campoQuantidade.getText().toString().equals("")){
            return false
        }
        return true
    }

    private fun preencharCampos(){
        campoProduto.setText(produto?.produto?.toString())
        campoQuantidade.setText(produto?.quantidade?.toString())
    }

    private fun incrementarNovoProduto(){
        produto = Produto(campoProduto.getText().toString(),campoQuantidade.getText().toString().toInt())
        ProdutoDao.adicionarProduto(produto!!)
    }

    private fun editarProdutoDaLista(){
        produto?.produto = campoProduto.getText().toString()
        produto?.quantidade = campoQuantidade.getText().toString().toInt()
    }
}

