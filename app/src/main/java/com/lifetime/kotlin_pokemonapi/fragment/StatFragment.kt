package com.lifetime.kotlin_pokemonapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lifetime.kotlin_pokemonapi.R
import com.lifetime.kotlin_pokemonapi.model.Stat
import com.lifetime.kotlin_pokemonapi.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.stat_fragment.*

@Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
class StatFragment: Fragment() {
    private var searchKey: String = ""

    @get:JvmName("view") private var view: View? = null

    companion object{
        fun instance(searchKey: String): StatFragment{
            val statFragment = StatFragment()
            statFragment.searchKey = searchKey
            return statFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view == null) {
            view = inflater.inflate(R.layout.stat_fragment, container, false)

            val pokemonViewModel: PokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
            pokemonViewModel.init()

            pokemonViewModel.getStats(searchKey)

            pokemonViewModel.statsMutableLiveData.observe(this,
                Observer<List<Stat>> { stats -> updateViewStats(stats!!) })
        }

        return view
    }

    fun updateViewStats(stats: List<Stat>){
        hpProgressBar.progress = stats[5].baseStat*2/3
        hpProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)
        atkProgressBar.progress = stats[4].baseStat*2/3
        atkProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)
        spdProgressBar.progress = stats[0].baseStat
        spdProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)
        defProgressBar.progress = stats[3].baseStat
        defProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)
        satkProgressBar.progress = stats[2].baseStat
        satkProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)
        sdefProgressBar.progress = stats[1].baseStat
        sdefProgressBar.progressDrawable = resources.getDrawable(R.drawable.background_progress)

        statHp.text = stats[5].baseStat.toString()
        statAtk.text = stats[4].baseStat.toString()
        statDef.text = stats[3].baseStat.toString()
        statSpd.text = stats[0].baseStat.toString()
        statSatk.text = stats[2].baseStat.toString()
        statSdef.text = stats[1].baseStat.toString()
    }
}