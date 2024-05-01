package com.example.eslamshahr.model

data class Actor(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)