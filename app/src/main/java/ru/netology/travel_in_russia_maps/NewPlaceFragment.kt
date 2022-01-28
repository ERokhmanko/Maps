package ru.netology.travel_in_russia_maps

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.travel_in_russia_maps.databinding.FragmentNewPlaceBinding

import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class NewPlaceFragment : Fragment(){

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

binding.nameFieldEdit.setText(name)
        binding.nameFieldEdit.setText(description)

        binding.nameField.requestFocus()

//        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            viewModel.saveDraft(binding.edit.text.toString())
//            remove()
//            requireActivity().onBackPressed()
//        }


        binding.save.setOnClickListener {

//            val content = binding.edit.text.toString()
//            viewModel.changeContent(content)
            viewModel.save()
            findNavController().navigate(R.id.action_newPlaceFragment_to_listOfPointsFragment)
        }

        return binding.root
    }

}