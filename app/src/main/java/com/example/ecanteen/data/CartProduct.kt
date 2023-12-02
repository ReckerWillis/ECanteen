package com.example.ecanteen.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val product: Product,
    val quantity: Int,
    val selectedColor: Int? = null,
    val SelectedSize: String? = null
): Parcelable{
    constructor(): this(Product(), 1, null,null)
}
