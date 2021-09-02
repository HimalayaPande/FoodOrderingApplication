package com.example.foodorderingapplication.Models

data class RestaurantDataModel(
    val restaurantimage: String,
    val name: String,
    val CityName: String,
    //val ratingimage: Int,
    val rating: String,
    val cost: String,
    var res_id: Int,
    val time: String,
    val email: String,
    val phonenumber: String,
    var status: Boolean
)