package com.example.navegacaotelas

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.navegacaotelas.constants.Constants
import com.example.navegacaotelas.models.Game

class GameActivity : AppCompatActivity() {
    val constants = Constants()

    lateinit var edtId: EditText
    lateinit var edtNome: EditText
    lateinit var edtPreco: EditText
    var edit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        edtId = findViewById( R.id.edit_text_game_id )
        edtNome = findViewById(R.id.edit_text_game_nome)
        edtPreco = findViewById(R.id.edit_text_game_preco)

        edtId.isEnabled = false


        var id: String = intent.getStringExtra("id").toString()

        edtId.setText(id.toString())

        if(intent.getStringExtra("nome") != null){
            var nome: String = intent.getStringExtra("nome").toString()
            var preco: String = intent.getStringExtra("preco").toString()

            edtNome.setText(nome)
            edtPreco.setText(preco)

            edit = true
        }
    }

    public fun onClickConcluir(view: View){
        var id: String = edtId.getText().toString()
        var nome: String = edtNome.getText().toString()
        var preco: String = edtPreco.getText().toString()

        val intent: Intent = Intent()

        intent.putExtra("nome", nome)
        intent.putExtra("preco", preco)

        if(edit) { intent.putExtra("idEdit", id )}

        setResult( constants.RESULT_ADD, intent );
        finish();
    }

    public fun onClickCancelar(view: View){
        setResult( constants.RESULT_CANCEL );
        finish();
    }
}