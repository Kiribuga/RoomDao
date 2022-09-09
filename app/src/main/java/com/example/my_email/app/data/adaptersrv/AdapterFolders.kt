package com.example.my_email.app.data.adaptersrv

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_email.R
import com.example.my_email.app.util.inflate
import com.example.my_email.db.folders.Folders

class AdapterFolders(
    private val onClickItem: (position: Int) -> Unit,
    private val longClickItem: (folder: Folders) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var folders: List<Folders> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoldersHolder(parent.inflate(R.layout.item_folder))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoldersHolder -> {
                val folder = folders[position]
                holder.bind(folder)
                holder.itemView.setOnClickListener { onClickItem.invoke(position) }
                holder.itemView.setOnLongClickListener {
                    longClickItem.invoke(folder)
                    true
                }
            }
            else -> error("Incorrect view holder = $holder")
        }
    }

    override fun getItemCount(): Int = folders.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListFolders(newList: List<Folders>) {
        folders = newList
        notifyDataSetChanged()
    }

    abstract class BaseHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        private val titleFolder: TextView = itemView.findViewById(R.id.nameFolder)

        protected fun bindMainInfo(
            title: String
        ) {
            titleFolder.text = title
        }
    }

    class FoldersHolder(
        containerView: View
    ) : BaseHolder(containerView) {
        fun bind(folder: Folders) {
            bindMainInfo(folder.title)
        }
    }
}