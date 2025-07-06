package com.example.mystoreadmin.domain.models

import java.util.UUID


data class Product(
    val id: String  = UUID.randomUUID().toString(), // Unique product ID
    val name: String,                   // Product name/title
    val description: String,            // Full description
    val price: Double,                  // Current price
    val originalPrice: Double? = null,         // Price before discount
    val discountPercent: Int? = null,          // % off, if applicable
    val stockQuantity: Int,             // How many in stock
    val isAvailable: Boolean,           // True if in stock and sellable
    val category: String,               // E.g., "Shoes", "Electronics"
    val brand: String,                 // E.g., "Samsung", "Nike"
    val imageUrls: List<String>?,        // List of image URLs (carousel)
    val createdAt: String? = System.currentTimeMillis().toString(), // Timestamp              // For sorting/new arrivals
         // For sync/update checking
)
