package com.example.mystoreadmin.domain.models

import java.util.UUID

data class Product(
    val CATEGORY: String,
    val PRODUCT_ID: String = UUID.randomUUID().toString(),
    val PRODUCT_NAME: String,
    val PRODUCT_PRICE: Double,
    val PRODUCT_QUANTITY: Int,
    val PRODUCT_DESCRIPTION: String = ""
)
