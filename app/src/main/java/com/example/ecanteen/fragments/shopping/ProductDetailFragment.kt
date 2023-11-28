package com.example.ecanteen.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecanteen.R
import com.example.ecanteen.activities.ShoppingActivity
import com.example.ecanteen.adapters.ColorAdapter
import com.example.ecanteen.adapters.SizesAdapter
import com.example.ecanteen.adapters.ViewPager2Images
import com.example.ecanteen.databinding.FragmentProductDetailsBinding
import com.example.ecanteen.databinding.SizeRvItemBinding
import com.example.ecanteen.util.hideBottomNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductDetailFragment: Fragment() {
    private val args by navArgs<ProductDetailFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizeAdapter by lazy { SizesAdapter() }
    private val colorAdapter by lazy { ColorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigation()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupColorRv()
        setupViewPager()

        binding.imageClose.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrices.text = "$ ${product.price}"
            tvProductDescription.text = product.description

            if(product.colors.isNullOrEmpty())
                tvProductColor.visibility = View.INVISIBLE
            if(product.sizes.isNullOrEmpty())
                tvProductSize.visibility = View.INVISIBLE
        }

        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorAdapter.differ.submitList(it) }
        product.sizes?.let { sizeAdapter.differ.submitList(it) }
    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

    private fun setupColorRv() {
        binding.rvColors.apply {
            adapter = colorAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setupSizesRv() {
        binding.rvSize.apply {
            adapter = sizeAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
}