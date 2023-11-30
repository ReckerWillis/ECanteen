package com.example.ecanteen.firebase

import com.example.ecanteen.data.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val userCollection = "user"
    private val cartCollectionName = "cart"

    private val cartCollection = firestore.collection(userCollection)
        .document(auth.uid ?: "")
        .collection(cartCollectionName)

    fun addProductToCart(cartProduct: CartProduct, onResult: (Result<CartProduct>) -> Unit) {
        cartCollection.document().set(cartProduct)
            .addOnSuccessListener {
                onResult(Result.success(cartProduct))
            }.addOnFailureListener {
                onResult(Result.failure(it))
            }
    }

    fun increaseQuantity(documentId: String, onResult: (Result<String>) -> Unit) {
        firestore.runTransaction { transition ->
            val documentRef = cartCollection.document(documentId)
            val document = transition.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)

            productObject?.let { cartProduct ->
                val newQuantity = cartProduct.quantity + 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transition.set(documentRef, newProductObject)
            }
        }.addOnSuccessListener {
            onResult(Result.success(documentId))
        }.addOnFailureListener {
            onResult(Result.failure(it))
        }
    }

    enum class QuantityChanging {
        INCREASE, DECREASE
    }
}
