package ru.netology.travel_in_russia_maps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.travel_in_russia_maps.R
import ru.netology.travel_in_russia_maps.databinding.CardMarkedPlacesBinding
import ru.netology.travel_in_russia_maps.dto.Place

interface PlaceCallback {
    fun remove(place: Place)
    fun edit(place: Place)
}

class PlacesAdapter(private val placeCallback: PlaceCallback) :
    ListAdapter<Place, PlaceViewHolder>(PlacesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = CardMarkedPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, placeCallback)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PlaceViewHolder(
    private val binding: CardMarkedPlacesBinding,
    private val placeCallback:PlaceCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(place: Place) {

        with(binding) {
//            Glide.with(avatarPlace)//TODO
            namePlace.text = place.name
            description.text = place.description

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.place_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.place_remove -> {
                                placeCallback.remove(place)
                                true
                            }
                            R.id.place_edit -> {
                                placeCallback.edit(place)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }
}

class PlacesDiffCallback : DiffUtil.ItemCallback<Place>() {

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

}