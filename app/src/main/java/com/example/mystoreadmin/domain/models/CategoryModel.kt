package com.example.mystoreadmin.domain.models

data class CategoryModel(
    val id : String = "",
    val name : String = "",
    val imageUrl : String = "",
    val date : String = System.currentTimeMillis().toString()
)
