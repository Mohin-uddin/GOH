package com.ewnbd.goh.data.model.response_all_class.mapSearch

data class MapSearchResponse(
    val predictions: List<Prediction>,
    val status: String
)