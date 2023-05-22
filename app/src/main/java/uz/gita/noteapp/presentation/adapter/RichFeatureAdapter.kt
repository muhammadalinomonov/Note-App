package uz.gita.noteapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ItemRichBinding
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel
import uz.gita.noteapp.presentation.adapter.data.RichFeatureType

class RichFeatureAdapter :
    ListAdapter<RichFeatureModel, RichFeatureAdapter.HomeViewHolder>(DIFF_CALL_BACK) {

    private var selectListener: ((RichFeatureType) -> Unit)? = null

    fun setSelectListener(block: (RichFeatureType) -> Unit) {
        selectListener = block
    }

    inner class HomeViewHolder(private val binding: ItemRichBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RichFeatureModel) {
            binding.root.setImageResource(data.image)

            val bgColor = if (data.isEnabled) R.color.gray_200 else R.color.white

            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    bgColor
                )
            )

            binding.root.setOnClickListener {
                data.isEnabled = !data.isEnabled
                selectListener?.invoke(data.type)
                notifyItemChanged(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemRichBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<RichFeatureModel>() {
            override fun areItemsTheSame(
                oldItem: RichFeatureModel,
                newItem: RichFeatureModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RichFeatureModel,
                newItem: RichFeatureModel
            ): Boolean {
                return oldItem.image == newItem.image
            }
        }
    }
}