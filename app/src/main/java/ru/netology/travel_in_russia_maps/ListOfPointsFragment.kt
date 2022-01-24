package ru.netology.travel_in_russia_maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.travel_in_russia_maps.adapter.PlaceCallback
import ru.netology.travel_in_russia_maps.adapter.PlacesAdapter
import ru.netology.travel_in_russia_maps.databinding.FragmentListOfPointsBinding
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class ListOfPointsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListOfPointsBinding.inflate(inflater, container, false)


        val viewModel: PlaceViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )


        val adapter = PlacesAdapter(object : PlaceCallback {
            override fun remove(place: Place) {
              viewModel.removeById(place.id)
            }

            override fun edit(place: Place) {
               viewModel.edit(place)
            }
        })

        binding.list.adapter = adapter

        //TODO переделать
        viewModel.data.observe(viewLifecycleOwner, { state ->
            val listComparison = adapter.itemCount < state.places.size
            adapter.submitList(state.places) {
                if (listComparison) binding.list.scrollToPosition(0)
            }

            binding.emptyText.isVisible = state.empty
        })

        binding.newPoint.setOnClickListener {
//            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment) //TODO
        }

        return binding.root
    }
}