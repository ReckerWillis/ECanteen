package com.example.ecanteen.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteen.R
import com.example.ecanteen.adapters.BestProductAdapter
import com.example.ecanteen.databinding.FragmentBaseCategoryBinding
import com.example.ecanteen.databinding.ProductRvItemBinding
import com.example.ecanteen.util.showBottomNavigation

open class BaseCategoryFragment:Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductAdapter by lazy { BestProductAdapter() }
    protected val bestProductAdapter: BestProductAdapter by lazy { BestProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRv()
        setupBestProductRv()

        bestProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment,b)
        }

        offerAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment,b)
        }

        binding.rvOfferProduct.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dx != 0){
                    onOfferPagingRequest()
                }
            }
        })

        binding.nestedScrollBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY){
                onBestProductPagingRequest()
            }
        })
    }
    fun showOfferLoading(){
        binding.offerProductProgressBar.visibility = View.VISIBLE
    }

    fun hideOfferLoading(){
        binding.offerProductProgressBar.visibility = View.GONE
    }

    fun showBestProductLoading(){
        binding.bestProductProgressBar.visibility = View.VISIBLE
    }

    fun hideBestProductLoading(){
        binding.offerProductProgressBar.visibility = View.GONE
    }

    open fun onOfferPagingRequest(){

    }

    open fun onBestProductPagingRequest(){

    }


    private fun setupBestProductRv() {
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            adapter = bestProductAdapter
        }
    }

    private fun setupOfferRv() {
        binding.rvOfferProduct.apply{
            layoutManager=
                LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter= offerAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigation()
    }
}