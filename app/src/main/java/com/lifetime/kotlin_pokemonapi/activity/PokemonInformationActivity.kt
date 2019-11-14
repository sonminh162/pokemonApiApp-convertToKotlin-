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

@Suppress("DEPRECATION")
class PokemonInformationActivity : AppCompatActivity(),View.OnClickListener {
    override fun onClick(p0: View?) {
        turnOffState(statButton)
        turnOffState(evolutionButton)
        turnOffState(movesButton)
        when(p0){
            statButton->{
                turnOnState(statButton)
                loadFragment(statFragment)
            }
            evolutionButton->{
                turnOnState(evolutionButton)
                loadFragment(evolutionFragment)
            }
            movesButton->{
                turnOnState(movesButton)
                loadFragment(moveFragment)
            }
        }
    }

    var searchKey: String = ""

    private var pokemonViewModel: PokemonViewModel? = null

    private var statFragment: StatFragment? = null
    private var moveFragment: MoveFragment? = null
    private var evolutionFragment: EvolutionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchKey = intent.extras?.getString(SEARCH_KEY) ?:""

        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        setUpUi()

        pokemonViewModel?.getName(searchKey)
        pokemonViewModel?.getDescription(searchKey)
        pokemonViewModel?.getUrlImage(searchKey)

    }

    private fun setUpUi(){

        initFirstTimeFragment()

        loadFragment(statFragment)

        closeButton.setOnClickListener{finish()}

        pokemonViewModel?.nameMutableLiveData?.observe(this,
            Observer<String> { t -> titlePokemonName.text = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,t) })

        pokemonViewModel?.descriptionMutableLiveData?.observe(this,
            Observer<String> { t -> descriptionPokemon.text = t})

        pokemonViewModel?.urlImageMutableLiveData?.observe(this,
            Observer<String> { t ->
                if (t.isNotBlank()){
                    Picasso.get()
                        .load(t)
                        .error(R.drawable.background_box)
                        .into(pokemonView)
                }else{
                    pokemonView.setImageResource(R.drawable.not_found_img)
                }
            })
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

    private fun turnOnState(currentButton: Button){
        currentButton.background = resources.getDrawable(R.drawable.button_box)
        currentButton.setTextColor(resources.getColor(R.color.white_two))
    }

    private fun turnOffState(currentButton: Button){
        currentButton.background = resources.getDrawable(R.drawable.background_box)
        currentButton.setTextColor(resources.getColor(R.color.dark_blue))
    }
}
