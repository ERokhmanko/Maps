package ru.netology.travel_in_russia_maps

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.travel_in_russia_maps.databinding.FragmentNewPlaceBinding

import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class NewPlaceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewPlaceBinding.inflate(inflater, container, false)

        val viewModel: PlaceViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val description = arguments?.getString("description")
        val name = arguments?.getString("name")
        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")
        val local =
            if (latitude == null || longitude == null) {
                ""
            } else {
                "$latitude;$longitude"
            }


        binding.nameFieldEdit.setText(name)
        binding.descriptionFieldEdit.setText(description)
        binding.locationCoordinatesEdit.setText(local)

        viewModel.getDraftName()?.let(binding.nameFieldEdit::setText)
        viewModel.getDraftDescription()?.let(binding.descriptionFieldEdit::setText)



        binding.nameFieldEdit.requestFocus()

        binding.selectMap.setOnClickListener {
            viewModel.saveDraft(
                binding.nameFieldEdit.text.toString(),
                binding.descriptionFieldEdit.text.toString()
            )
            findNavController().navigate(R.id.action_newPlaceFragment_to_mapsFragment)
        }


        binding.save.setOnClickListener {

            val name = binding.nameFieldEdit.text.toString()
            val local = binding.locationCoordinatesEdit.text.toString()
            val description = binding.descriptionFieldEdit.text.toString()

            when {
                (name.isEmpty()) -> {
                    Snackbar.make(
                        binding.root,
                        R.string.error_empty_name,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                (local.isEmpty()) -> {
                    Snackbar.make(
                        binding.root,
                        R.string.error_empty_coordinates,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else -> {
                    viewModel.changeContent(name, description, local)
                    viewModel.save()
                    findNavController().navigate(R.id.action_newPlaceFragment_to_listOfPointsFragment)
                }
            }
        }

        return binding.root
    }
}


