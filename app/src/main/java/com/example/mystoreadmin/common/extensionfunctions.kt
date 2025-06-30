package com.example.mystoreadmin.common

import com.example.mystoreadmin.domain.models.Product

fun Product.isValidProduct(): Boolean {

    return this.PRODUCT_NAME.isNotEmpty() &&
            this.PRODUCT_QUANTITY >=0 &&
            this.CATEGORY.isNotEmpty() &&
            this.PRODUCT_PRICE >=0
}