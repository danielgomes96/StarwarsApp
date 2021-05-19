package com.daniel.character_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.domain.entity.Film

class CharacterFilmsAdapter : RecyclerView.Adapter<CharacterFilmsAdapter.FilmViewHolder>() {

    private var filmsList = emptyList<Film>()

    fun setupList(filmsList: List<Film>) {
        this.filmsList = filmsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int = filmsList.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(filmsList[position])
    }

    inner class FilmViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.filmTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.filmDescription)

        fun bind(film: Film) {
            tvTitle.text = film.title
            tvDescription.text = film.description
        }
    }
}
