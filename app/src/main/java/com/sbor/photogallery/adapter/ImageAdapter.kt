package com.sbor.photogallery.adapter
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sbor.photogallery.databinding.ItemImgGalleryBinding
import com.sbor.photogallery.loadImage
import com.sbor.photogallery.model.Gallery

class ImageAdapter(var listener: Listener): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private val list = arrayListOf<Gallery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImgGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(list[position], listener, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItem(images : ArrayList<Gallery>){
        list.addAll(images)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(private var binding: ItemImgGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gallery: Gallery, listener: Listener, position: Int) {
            binding.imgGallery.loadImage(gallery.image)
            binding.selectedImage.isVisible = gallery.isSelected
            itemView.setOnClickListener {
                listener.onClick(gallery, position)
            }
        }
    }

    interface Listener {
        fun onClick(gallery: Gallery, position: Int)
    }

}