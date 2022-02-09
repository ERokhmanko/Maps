package ru.netology.travel_in_russia_maps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.travel_in_russia_maps.databinding.FragmentMapBinding
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.utils.icon
import ru.netology.travel_in_russia_maps.viewModel.PlaceViewModel

class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                googleMap.apply {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
            } else {
                // TODO: show sorry dialog
            }
        }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMapBinding.inflate(inflater, container, false)

        binding.list.setOnClickListener {
            findNavController().navigate(R.id.action_mapsFragment_to_listOfPointsFragment)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: PlaceViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment



        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap().apply {
                isTrafficEnabled = true
                isBuildingsEnabled = true

                uiSettings.apply {
                    isZoomControlsEnabled = true
                    setAllGesturesEnabled(true)
                }
            }


            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    googleMap.apply {
                        isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }

                    fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(requireActivity())

                    val id = arguments?.getLong("id")
                    var showPlace: Place? = null

                    viewModel.data.observe(viewLifecycleOwner) { feedModel ->
                        feedModel.places.map { place ->
                            if (place.id == id) {
                                showPlace = place
                            }
                        }
                    }

                    if (showPlace?.id == null) {
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            lifecycle.coroutineScope.launchWhenCreated {
                                googleMap.awaitAnimateCamera(
                                    CameraUpdateFactory.newCameraPosition(
                                        cameraPosition {
                                            target(LatLng(it.latitude, it.longitude))
                                            zoom(30F)
                                        }
                                    ))
                            }

                        }
                    } else{
                        googleMap.awaitAnimateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                cameraPosition {
                                    target(LatLng(showPlace!!.latitude, showPlace!!.longitude))
                                    zoom(30F)
                                }
                            ))
                    }

                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    // TODO: show rationale dialog
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    //TODO должен сделать так, чтобы после разрешения заново прошло when
                }
            }

            googleMap.setOnMapLongClickListener { point ->
                val latitude = point.latitude
                val longitude = point.longitude

                bundle.putDouble("latitude", latitude)
                bundle.putDouble("longitude", longitude)

                //реализация добавления маркеров
                val markerManager = MarkerManager(googleMap)
                markerManager.newCollection().apply {
                    addMarker {
                        position(LatLng(latitude, longitude))
                        icon(getDrawable(requireContext(), R.drawable.ic_baseline_location_on_24)!!)

                    }
                }
                findNavController().navigate(R.id.action_mapsFragment_to_newPlaceFragment, bundle)

            }

        }
    }


}