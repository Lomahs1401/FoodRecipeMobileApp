package com.example.foodrecipeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.constant.Constant
import com.example.foodrecipeapp.data.model.Equipment
import com.example.foodrecipeapp.databinding.ItemEquipmentBinding
import com.example.foodrecipeapp.utils.ext.loadImageWithUrl

class EquipmentAdapter : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    private var listEquipments: MutableList<Equipment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val binding =
            ItemEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listEquipments.size
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bindData(listEquipments[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listEquipments: MutableList<Equipment>) {
        this.listEquipments = listEquipments
        notifyDataSetChanged()
    }

    inner class EquipmentViewHolder(private val binding: ItemEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(equipment: Equipment) {
            binding.imgEquipment.loadImageWithUrl(Constant.BASE_URL_IMAGE_EQUIPMENT + equipment.image)
            binding.tvEquipmentName.text = equipment.name
        }
    }
}