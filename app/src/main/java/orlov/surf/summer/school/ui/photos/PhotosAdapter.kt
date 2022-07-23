package orlov.surf.summer.school.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.ItemPhotoLargeBinding
import orlov.surf.summer.school.databinding.ItemPhotoSmallBinding
import orlov.surf.summer.school.domain.model.Photo

class PhotosAdapter(private val viewHolderType: ViewHolderType, private val clickListener: OnItemClickListener) :
    ListAdapter<Photo, RecyclerView.ViewHolder>(DiffUtilCallback) {

    interface OnItemClickListener {
        fun onPhotoClick(photo: Photo)
        fun onLikeClick(photo: Photo)
    }

    inner class PhotosViewHolderSmall(
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

    inner class PhotosViewHolderLarge(
        private val binding: ItemPhotoLargeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.ivPhoto.load(photo.photoUrl)
            binding.tvTitle.text = photo.title
            binding.root.setOnClickListener { clickListener.onPhotoClick(photo) }
            binding.btnLike.setOnClickListener { clickListener.onLikeClick(photo) }
            binding.tvDescription.text = photo.content
            binding.tvDate.text = photo.publicationDate.toString()
            if (photo.isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_liked)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_not_liked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewHolderType) {
           ViewHolderType.SMALL -> PhotosViewHolderSmall(
                ItemPhotoSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewHolderType.LARGE -> PhotosViewHolderLarge(
                ItemPhotoLargeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = getItem(position)
        when(holder) {
            is PhotosViewHolderSmall -> holder.bind(photo)
            is PhotosViewHolderLarge -> holder.bind(photo)
        }
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

enum class ViewHolderType {
    SMALL,
    LARGE
}