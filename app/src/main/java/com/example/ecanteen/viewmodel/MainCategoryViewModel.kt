package com.example.ecanteen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecanteen.data.Product
import com.example.ecanteen.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
):ViewModel() {

    private val _specialProducts = MutableStateFlow<com.example.ecanteen.util.Resource<List<Product>>>(com.example.ecanteen.util.Resource.unspecified())
    val specialProduct : StateFlow<com.example.ecanteen.util.Resource<List<Product>>> = _specialProducts

    private val _bestDealProducts = MutableStateFlow<com.example.ecanteen.util.Resource<List<Product>>>(com.example.ecanteen.util.Resource.unspecified())
    val bestDealProducts : StateFlow<com.example.ecanteen.util.Resource<List<Product>>> = _bestDealProducts

    private val _bestProduct = MutableStateFlow<com.example.ecanteen.util.Resource<List<Product>>>(com.example.ecanteen.util.Resource.unspecified())
    val bestProduct : StateFlow<com.example.ecanteen.util.Resource<List<Product>>> = _bestProduct

    private val pagingInfo = PagingInfo()
    init {
        fetchSpecialProduct()
        fetchBestDeals()
        fetchBestProduct()
    }
    fun fetchSpecialProduct(){
        viewModelScope.launch {
            _specialProducts.emit(com.example.ecanteen.util.Resource.Loading())
        }
        firestore.collection("Product")
            .whereEqualTo("category","Special Product").get().addOnSuccessListener {result ->
            val specialProductsList = result.toObjects(Product::class.java)
            viewModelScope.launch {
                _specialProducts.emit(com.example.ecanteen.util.Resource.Success(specialProductsList))
            }
        }.addOnFailureListener{
            viewModelScope.launch {
                _specialProducts.emit(com.example.ecanteen.util.Resource.Error(it.message.toString()))
            }
        }
    }
    fun fetchBestDeals(){
        viewModelScope.launch {
            _bestDealProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category","Best Deals").get()
            .addOnSuccessListener { result->
                val bestDealProducts = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealProducts.emit(com.example.ecanteen.util.Resource.Success(bestDealProducts))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealProducts.emit(com.example.ecanteen.util.Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProduct(){
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _bestProduct.emit(Resource.Loading())
            firestore.collection("Products")
                .whereEqualTo("category", "Best Deals").limit(pagingInfo.bestProductPage * 10).get()
                .addOnSuccessListener { result ->
                    val bestProducts = result.toObjects(Product::class.java)
                    pagingInfo.isPagingEnd = bestProducts == pagingInfo.oldBestProducts
                    pagingInfo.oldBestProducts = bestProducts
                    viewModelScope.launch {
                        _bestProduct.emit(com.example.ecanteen.util.Resource.Success(bestProducts))
                    }
                    pagingInfo.bestProductPage++
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _bestProduct.emit(com.example.ecanteen.util.Resource.Error(it.message.toString()))
                    }
                }
            }
        }
    }
    internal data class  PagingInfo(
        var bestProductPage: Long = 1,
        var oldBestProducts: List<Product> = emptyList(),
        var isPagingEnd:Boolean = false
    )



}