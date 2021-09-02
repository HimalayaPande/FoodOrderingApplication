package com.example.foodorderingapplication.Models

data class RestaurantFeedbackModel(
        val restid: String,
        val restname: String,
        val customerrating: String,
        val totalcost: String,
        val comment: String,
        val item_list: String
)
