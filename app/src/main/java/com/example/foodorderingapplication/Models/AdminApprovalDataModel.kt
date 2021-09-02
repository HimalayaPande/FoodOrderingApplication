package com.example.foodorderingapplication.Models

data class AdminApprovalDataModel(
    val restaurantimage: String,
    val name: String,
    val CityName: String,
    //val ratingimage: Int,
    val rating: String,
    val cost: String,
    var res_id: String,
    val time: String,
    val email: String,
    val phonenumber: String,
    var status: Boolean
)
