package com.example.my_email.app.data.adaptersrv

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_email.R
import com.example.my_email.app.util.inflate
import com.example.my_email.db.users.Users

class AdapterUsers(
    private val onClickItem: (user: Users) -> Unit,
    private val longClickItem: (user: Users) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users: List<Users> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserHolder(parent.inflate(R.layout.item_user))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserHolder -> {
                val user = users[position]
                holder.bind(user)
                holder.itemView.setOnClickListener { onClickItem.invoke(user) }
                holder.itemView.setOnLongClickListener {
                    longClickItem.invoke(user)
                    true
                }
            }
            else -> error("Incorrect view holder = $holder")
        }
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListUsers(newList: List<Users>) {
        users = newList
        notifyDataSetChanged()
    }

    abstract class BaseHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        private val avatarUser: ImageView = itemView.findViewById(R.id.avatarButton)

        protected fun bindMainInfo(
            avatar: String
        ) {
            Glide.with(itemView)
                .load(avatar)
                .placeholder(R.drawable.ic_baseline_portrait_24)
                .into(avatarUser)
        }
    }

    class UserHolder(
        containerView: View
    ) : BaseHolder(containerView) {
        fun bind(user: Users) {
            bindMainInfo(user.avatar)
        }
    }
}