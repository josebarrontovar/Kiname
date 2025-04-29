package com.example.kiname.data.remote

data class PostResponse
    (
    val rows: List<List<String>>,
    val sheet: String
)