package com.daniel.character_search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.domain.entity.Character

class CharacterSearchAdapter(
    private val itemClickListener: (Character) -> Unit
) : RecyclerView.Adapter<CharacterSearchAdapter.CharacterViewHolder>() {

    private var characterList = emptyList<Character>()

    fun setupList(characterList: List<Character>) {
        this.characterList = characterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character_search, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position], itemClickListener)
    }

    inner class CharacterViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvName = itemView.findViewById<TextView>(R.id.characterSearchListItem_Name)

        fun bind(character: Character, itemClickListener: (Character) -> Unit) {
            tvName.text = character.name
            itemView.setOnClickListener {
                itemClickListener(character)
            }
        }
    }
}
