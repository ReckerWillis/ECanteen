package com.example.ecanteen.data

data class CartProduct(
    val product: Product,
    val quantity: Int,
    val selectedColor: Int? = null,
    val SelectedSize: String? = null
){
    constructor(): this(Product(), 1, null,null)
}
