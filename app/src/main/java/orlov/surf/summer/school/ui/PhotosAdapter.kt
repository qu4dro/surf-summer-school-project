package orlov.surf.summer.school.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import orlov.surf.summer.school.databinding.ItemPhotoSmallBinding
import orlov.surf.summer.school.domain.model.Photo

class PhotosAdapter(val onClickPhoto: (Photo) -> Unit) :
    ListAdapter<Photo, PhotosAdapter.PhotosViewHolder>(DiffUtilCallback) {

    inner class PhotosViewHolder(
        private val binding: ItemPhotoSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.ivPhoto.load(photo.photoUrl)
            binding.tvTitle.text = photo.title
            binding.root.setOnClickListener { onClickPhoto }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            ItemPhotoSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }


}