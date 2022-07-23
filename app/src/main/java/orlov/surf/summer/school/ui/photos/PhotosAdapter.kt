package orlov.surf.summer.school.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.ItemPhotoSmallBinding
import orlov.surf.summer.school.domain.model.Photo

class PhotosAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<Photo, PhotosAdapter.PhotosViewHolder>(DiffUtilCallback) {

    interface OnItemClickListener {
        fun onPhotoClick(photo: Photo)
        fun onLikeClick(photo: Photo)
    }

    inner class PhotosViewHolder(
        private val binding: ItemPhotoSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.ivPhoto.load(photo.photoUrl)
            binding.tvTitle.text = photo.title
            binding.root.setOnClickListener { clickListener.onPhotoClick(photo) }
            binding.btnLike.setOnClickListener { clickListener.onLikeClick(photo) }
            if (photo.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_liked)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_not_liked)
            }
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