package ru.netology.travel_in_russia_maps.dto

import android.accounts.AuthenticatorDescription

data class Place(
    val id: Long,
    val avatarPlace: String,
    val name: String,
    val description: String,
    val latitude: Float,
    val longitude: Float
)