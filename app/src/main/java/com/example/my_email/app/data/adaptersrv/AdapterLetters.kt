package com.example.my_email.app.data.adaptersrv

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.my_email.R
import com.example.my_email.app.util.inflate
import com.example.my_email.db.letters.Letters
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AdapterLetters(
    private val onClickItem: (letter: Letters) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var letters: List<Letters> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LettersHolder(parent.inflate(R.layout.item_letter))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LettersHolder -> {
                val letter = letters[position]
                holder.bind(letter)
                holder.itemView.setOnClickListener {
                    onClickItem.invoke(letter)
                }
            }
            else -> error("Incorrect view holder = $holder")
        }
    }

    override fun getItemCount(): Int = letters.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListLetters(newList: List<Letters>) {
        letters = newList
        notifyDataSetChanged()
    }

    abstract class BaseHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        private val titleLetter: TextView = itemView.findViewById(R.id.titleLetter)
        private val fromUser: TextView = itemView.findViewById(R.id.fromUser)
        private val timeReceipt: TextView = itemView.findViewById(R.id.timeReceipt)
        private val statusLetter: TextView = itemView.findViewById(R.id.statusLetter)

        @RequiresApi(Build.VERSION_CODES.O)
        private val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
            .withZone(ZoneId.systemDefault())

        @RequiresApi(Build.VERSION_CODES.O)
        protected fun bindMainInfo(
            title: String,
            userEmail: String,
            timeLetter: Instant,
            status: String
        ) {
            titleLetter.text = title
            fromUser.text = userEmail
            timeReceipt.text = formatter.format(timeLetter)
            statusLetter.text = status
        }
    }

    class LettersHolder(
        containerView: View
    ) : BaseHolder(containerView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(letter: Letters) {
            bindMainInfo(letter.title, letter.to_user_email, letter.time, letter.status_text)
        }
    }
}