package ru.netology.travel_in_russia_maps.dto

data class Place(
    val id: Long,
    val avatarPlace: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)