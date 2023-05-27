package com.example.contentproviderex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderex.databinding.ViewHolderMainBinding

class MyAdapter(private val list:List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val viewDataBinding: ViewHolderMainBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val binding =
            ViewHolderMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val binding = holder.viewDataBinding
        binding.text.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}