package com.example.ecanteen.data

sealed class Category(val category: String) {
    object Makanan:Category("Makanan")
    object Minuman:Category("Minuman")
    object Table:Category("Table")
    object Furniture:Category("Furniture")
    object Accessory:Category("Accessory")



    //Nanti diganti sesuai halaman

}