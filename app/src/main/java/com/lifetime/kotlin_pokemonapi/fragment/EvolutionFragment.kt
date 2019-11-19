package com.lifetime.kotlin_pokemonapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lifetime.kotlin_pokemonapi.R

@Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
class EvolutionFragment: Fragment() {

    @get:JvmName("view") private var view: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(view == null){
            view = inflater.inflate(R.layout.evolution_fragment,container,false)
        }
        return view
    }

}