package com.lifetime.kotlin_pokemonapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lifetime.kotlin_pokemonapi.R
import com.lifetime.kotlin_pokemonapi.adapter.MoveAdapter
import com.lifetime.kotlin_pokemonapi.model.MoveInfo
import com.lifetime.kotlin_pokemonapi.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.move_fragment.*

@Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
class MoveFragment : Fragment() {

    private var searchKey: String = ""

    @get:JvmName("view") private var view: View? = null

    companion object{
        fun instance(searchKey: String): MoveFragment{
            val moveFragment= MoveFragment()
            moveFragment.searchKey = searchKey
            return moveFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(view == null) {
            view = inflater.inflate(R.layout.move_fragment,container,false)

            var pokemonViewModel: PokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)

            pokemonViewModel.getMoves(searchKey)

            pokemonViewModel.movesMutableLiveData.observe(this, object : Observer<List<String>> {
                override fun onChanged(strings: List<String>?) {
                    initView(strings!!)
                }
            })
        }
        return view
    }

    fun initView(strings: List<String>){
        recyclerViewMove.hasFixedSize()

        val layoutManager = LinearLayoutManager(activity)
        recyclerViewMove.layoutManager = layoutManager

        val moveInfors: ArrayList<MoveInfo> = ArrayList()
        for(string: String in strings){
            moveInfors.add(MoveInfo(string))
        }

        val adapter = MoveAdapter(moveInfors)
        recyclerViewMove.adapter = adapter
    }
}