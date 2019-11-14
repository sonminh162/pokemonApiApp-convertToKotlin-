package com.lifetime.kotlin_pokemonapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifetime.kotlin_pokemonapi.R
import com.lifetime.kotlin_pokemonapi.model.MoveInfo

class MoveAdapter(moveInfos: ArrayList<MoveInfo>) : RecyclerView.Adapter<MoveAdapter.ViewHolder>() {

    private var moveInfors: ArrayList<MoveInfo> = moveInfos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.move_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moveInfors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moveInfors[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var moveName: TextView = itemView.findViewById(R.id.moveName)

        fun bind(moveInfo: MoveInfo) {
            moveName.text = moveInfo.name
        }
    }


}