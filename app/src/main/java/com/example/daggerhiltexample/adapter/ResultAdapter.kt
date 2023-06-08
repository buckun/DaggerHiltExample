package com.example.daggerhiltexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhiltexample.databinding.LayoutItemContainerBinding
import com.example.daggerhiltexample.model.ProductsItem
import com.example.daggerhiltexample.model.Response
import com.example.daggerhiltexample.utils.DiffUtil

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.SearchViewHolder>() {

    private var searches = emptyList<ProductsItem>()

    class SearchViewHolder(private val binding: LayoutItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productsItem : ProductsItem) {
            binding.result = productsItem
            binding.layout.setOnClickListener {}
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemContainerBinding.inflate(layoutInflater, parent, false)
                return SearchViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentRecipe = searches[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return searches.size
    }

    fun setData(newData: Response) {
        val searchDiffUtil = DiffUtil(searches, newData.products)
        val diffUtilResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(searchDiffUtil)
        searches = newData.products
        diffUtilResult.dispatchUpdatesTo(this)
    }

}