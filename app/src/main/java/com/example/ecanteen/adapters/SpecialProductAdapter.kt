package com.example.ecanteen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecanteen.data.Product
import com.example.ecanteen.databinding.SpecialRvItemBinding

class SpecialProductAdapter:RecyclerView.Adapter<SpecialProductAdapter.SpecialProductsViewHolder>() {

    inner class SpecialProductsViewHolder(private val binding: SpecialRvItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(product: Product){
                binding.apply {
                    Glide.with(itemView).load(product.images[0]).into(imageSpecialRvItem)
                    tvSpecialProductName.text = product.name
                    tvSpecialProductPrice.text = product.price.toString()
                }
            }
        }

    val diffCallback = object :DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductsViewHolder {
        return SpecialProductsViewHolder(
            SpecialRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener{
            onClick?.invoke(product)
        }
    }
    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    var onClick: ((Product)-> Unit)? = null

}