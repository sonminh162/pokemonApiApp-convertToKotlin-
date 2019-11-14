package com.lifetime.kotlin_pokemonapi.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lifetime.kotlin_pokemonapi.R
import com.lifetime.kotlin_pokemonapi.constant.Constant.Companion.SEARCH_KEY
import com.lifetime.kotlin_pokemonapi.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.activity_search_pokemon.*

class SearchPokemonActivity : AppCompatActivity() {

    private var pokemonViewModel: PokemonViewModel? = null

    var searchKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_pokemon)

        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        pokemonViewModel?.init()

        searchText.setOnEditorActionListener(fun(
            _: TextView,
            i: Int,
            keyEvent: KeyEvent
        ): Boolean {
            if (i == EditorInfo.IME_ACTION_SEARCH
                || i == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER
            ) {
                searchKey = searchText.text.toString().trim()
                pokemonViewModel?.getStats(searchKey)
            }
            return false
        })

        pokemonViewModel?.validResult?.observe(this, Observer<Boolean> { t ->
            if(t!!){
                var intent: Intent = Intent(this@SearchPokemonActivity,PokemonInformationActivity::class.java)
                intent.putExtra(SEARCH_KEY, searchKey)
                startActivity(intent)
            } else {
                Toast.makeText(this@SearchPokemonActivity, "New User Created", Toast.LENGTH_LONG).show()
            }
        })
    }
}