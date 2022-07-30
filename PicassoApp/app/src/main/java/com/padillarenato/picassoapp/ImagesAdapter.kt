package com.padillarenato.picassoapp

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ImagesAdapter(context: Context, images: List<String>, layout: Int) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private val context: Context
    private val images: List<String>
    private val layout: Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(images[position]).fit()
            .placeholder(R.drawable.spinner)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView

        init {
            image = itemView.findViewById(R.id.imageViewLayout) as ImageView
        }
    }

    init {
        this.context = context
        this.images = images
        this.layout = layout
    }
}
