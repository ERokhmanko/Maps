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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.awaitAnimateCamera
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                    //реализация добавления маркеров
//                    val markerManager = MarkerManager(googleMap)
//                    val collection: MarkerManager.Collection = markerManager.newCollection().apply {
//                        addMarker {
//                            position(LatLng(target.latitude, target.longitude))
//                            icon(getDrawable(requireContext(), R.drawable.ic_baseline_location_on_24)!!)
//                            title("The Moscow Kremlin")
//                        }
//                    }

                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    //TODO сообщение, что без разрешения работаь не будет
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            googleMap.setOnMapClickListener { point ->
                val latitude = point.latitude
                val longitude = point.longitude

            }

        }
    }


}