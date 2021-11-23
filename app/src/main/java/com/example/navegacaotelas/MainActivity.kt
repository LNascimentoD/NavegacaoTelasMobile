package com.example.navegacaotelas

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.navegacaotelas.constants.Constants
import com.example.navegacaotelas.models.Game
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val listaGames : ArrayList<Game> = ArrayList<Game>()
    val constants = Constants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListView()
        onClickEdit()
        onClickAdd()
    }

    private fun showListView(){
        val arrayAdapter: ArrayAdapter<*>

        var listView: ListView = findViewById(R.id.listView)
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, listaGames)

        listView.adapter = arrayAdapter
    }

    private fun onClickEdit(){
        val btnAdd: Button = findViewById(R.id.button_edit)
        btnAdd.setOnClickListener { view: View ->
            var edtId: EditText = findViewById( R.id.edit_text_id )
            try {
                var id = edtId.getText().toString().toInt()

                val editGame = Intent(this,  GameActivity::class.java)

                val game = listaGames.get(id - 1)

                editGame.putExtra("id", game.id.toString())
                editGame.putExtra("nome", game.nome)
                editGame.putExtra("preco", game.preco)

                startActivityForResult(editGame, constants.REQUEST_EDIT )
            }catch(e: Exception){
                Toast.makeText( this,"ID inválido",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun onClickAdd(){
        val btnAdd: Button = findViewById(R.id.button_add)
        btnAdd.setOnClickListener { view: View ->
            val addGame = Intent(this,  GameActivity::class.java)
            var id = listaGames.size + 1

            addGame.putExtra("id", id.toString())
            startActivityForResult(addGame, constants.REQUEST_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == constants.REQUEST_ADD && resultCode == constants.RESULT_ADD ){
            var id = listaGames.size + 1
            var nome = data?.getStringExtra("nome").toString()
            var preco = data?.getStringExtra("preco").toString()

            var game = Game(id, nome, preco)

            listaGames.add(game)

            showListView()
        }else if( requestCode == constants.REQUEST_EDIT && resultCode == constants.RESULT_ADD ){
            val nome = data?.getStringExtra("nome").toString()
            val preco = data?.getStringExtra("preco").toString()
            val idEdit = data?.getStringExtra("idEdit").toString()

            for(game in listaGames){
                if(game.id.toString() == idEdit){
                    game.id = idEdit.toInt()
                    game.nome = nome
                    game.preco = preco
                }
            }

            showListView()
        }else if( resultCode == constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado",
                Toast.LENGTH_SHORT).show();
        }
    }
}