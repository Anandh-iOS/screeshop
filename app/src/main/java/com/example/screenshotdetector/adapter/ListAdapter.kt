package com.example.screenshotdetector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screenshotdetector.R
import com.example.screenshotdetector.model.Product
import com.example.screenshotdetector.util.load
import kotlinx.android.synthetic.main.list_row.view.*

class ListAdapter(private val context: Context, private val data: List<Product?>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView = itemView.tv_category
        val ivPreview:ImageView = itemView.iv_product_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = data[position]
        if(product!=null){
            holder.tvTitle.text = product._source.Category
            holder.ivPreview.load(product._source.Image)


        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }
}