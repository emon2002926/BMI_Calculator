package com.example.bmicalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bmicalculator.databinding.ItemFoodArticlePreviewBinding
import com.example.bmicalculator.models.Food

class FoodApter(private val item:List<Food>): RecyclerView.Adapter<FoodApter.TodoViewHolder>() {
    inner class TodoViewHolder( var binding : ItemFoodArticlePreviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemFoodArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int =item.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = item[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDescription.text = item.description
        holder.binding.ivFoodImage.load(item.imageUrl)
    }
}