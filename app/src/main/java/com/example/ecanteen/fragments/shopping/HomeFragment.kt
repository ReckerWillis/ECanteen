package com.example.ecanteen.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecanteen.R
import com.example.ecanteen.adapters.HomeViewpagerAdapter
import com.example.ecanteen.databinding.FragmentHomeBinding
import com.example.ecanteen.fragments.categories.AccessoryFragment
import com.example.ecanteen.fragments.categories.ChairFragment
import com.example.ecanteen.fragments.categories.CupboardFragment
import com.example.ecanteen.fragments.categories.FurnitureFragment
import com.example.ecanteen.fragments.categories.MainCategoryFragment
import com.example.ecanteen.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment:Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater:LayoutInflater,
        container: ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val catergoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter = HomeViewpagerAdapter(catergoriesFragment,childFragmentManager,lifecycle)
        binding.viewpagerHome.adapter =viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){tab,position ->
            when(position){
                0 -> tab.text = "Main"
                1 -> tab.text = "Makanan"
                2 -> tab.text = "Minuman"
                3 -> tab.text = "Table"
                4 -> tab.text = "Accessory"
                5 -> tab.text = "Furniture"
            }
        }.attach()
    }

}