package com.example.mystoreadmin.common

import com.example.mystoreadmin.domain.models.Product

fun Product.isValidProduct(): Boolean {

    return this.name.isNotEmpty() &&
            this.stockQuantity >=0 &&
            this.category.isNotEmpty() &&
            this.price >=0 &&
            this.description.isNotEmpty() &&
            this.brand.isNotEmpty()
}