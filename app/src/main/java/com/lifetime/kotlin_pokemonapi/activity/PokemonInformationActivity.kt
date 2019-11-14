package com.lifetime.kotlin_pokemonapi.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.common.base.CaseFormat
import com.lifetime.kotlin_pokemonapi.R
import com.lifetime.kotlin_pokemonapi.constant.Constant.Companion.SEARCH_KEY
import com.lifetime.kotlin_pokemonapi.fragment.EvolutionFragment
import com.lifetime.kotlin_pokemonapi.fragment.MoveFragment
import com.lifetime.kotlin_pokemonapi.fragment.StatFragment
import com.lifetime.kotlin_pokemonapi.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class PokemonInformationActivity : AppCompatActivity() {

    var searchKey: String = ""

    private var pokemonViewModel: PokemonViewModel? = null

    private var statFragment: StatFragment? = null
    private var moveFragment: MoveFragment? = null
    private var evolutionFragment: EvolutionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchKey = intent.extras?.getString(SEARCH_KEY)!!

        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        pokemonViewModel!!.init()

        pokemonViewModel!!.getName(searchKey)
        pokemonViewModel!!.getDescription(searchKey)
        pokemonViewModel!!.getUrlImage(searchKey)

        setUpUi()
    }

    private fun setUpUi(){

        initFirstTimeFragment()

        loadFragment(statFragment)

        setUpFragmentFeature()

        closeButton.setOnClickListener{finish()}

        pokemonViewModel?.nameMutableLiveData?.observe(this,
            Observer<String> { t -> titlePokemonName.text = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,t) })

        pokemonViewModel?.descriptionMutableLiveData?.observe(this,
            Observer<String> { t -> descriptionPokemon.text = t})

        pokemonViewModel?.urlImageMutableLiveData?.observe(this,
            Observer<String> { t ->
                Picasso.get()
                    .load(t)
                    .into(pokemonView)
            })
    }

    private fun setUpFragmentFeature(){
        val buttons: ArrayList<Button> = ArrayList()
        buttons.add(statButton)
        buttons.add(evolutionButton)
        buttons.add(movesButton)

        statButton.setOnClickListener {
            buttons[0].isSelected = true
            buttons[1].isSelected = false
            buttons[2].isSelected = false
            queryButton(buttons)

            loadFragment(statFragment)
        }

        movesButton.setOnClickListener{
            buttons[2].isSelected = true
            buttons[0].isSelected = false
            buttons[1].isSelected = false
            queryButton(buttons)

            loadFragment(moveFragment)
        }

        evolutionButton.setOnClickListener {
            buttons[1].isSelected = true
            buttons[0].isSelected = false
            buttons[2].isSelected = false

            loadFragment(evolutionFragment)
        }
    }

    private fun initFirstTimeFragment(){
        statFragment = StatFragment.instance(searchKey)
        moveFragment = MoveFragment.instance(searchKey)
        evolutionFragment = EvolutionFragment()
    }

    private fun loadFragment(fragment: Fragment?){
        val fm: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment!!)
        fragmentTransaction.commit()
    }

    private fun queryButton(buttons: List<Button>){
        for(i: Int in 0 until buttons.size){
            val currentButton: Button = buttons[i]
            if(currentButton.isSelected){
                currentButton.background = resources.getDrawable(R.drawable.button_box)
                currentButton.setTextColor(resources.getColor(R.color.white_two))
            } else {
                currentButton.background = resources.getDrawable(R.drawable.background_box)
                currentButton.setTextColor(resources.getColor(R.color.dark_blue))
            }
        }
    }
}
