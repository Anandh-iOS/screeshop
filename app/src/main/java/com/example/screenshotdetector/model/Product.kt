package com.example.screenshotdetector.model

data class Product(
    val _id: String = "",
    val _index: String = "",
    val _score: Double = 0.0,
    val _source: Source = Source()
)