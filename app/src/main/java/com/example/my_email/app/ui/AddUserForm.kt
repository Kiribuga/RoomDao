package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.my_email.R
import com.example.my_email.app.data.DataInterface
import com.example.my_email.databinding.FormAddUserBinding
import com.example.my_email.db.folders.Folders
import com.example.my_email.db.users.Users

class AddUserForm(
    private val dataInterface: DataInterface
) : DialogFragment(R.layout.form_add_user) {

    private var vBinding: FormAddUserBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FormAddUserBinding.bind(view)
        vBinding = binding
        vBinding?.addButtonCreatedEmail?.setOnClickListener {
            dataInterface.saveUser(
                Users(
                    id = 0,
                    first_name = vBinding?.firstNameUserEditText?.text.toString(),
                    last_name = vBinding?.lastNameUserEditText?.text.toString(),
                    email = vBinding?.emailEditText?.text.toString(),
                    avatar = vBinding?.avatarUserEditText?.text.toString()
                )
            )
            dataInterface.standardFolders(
                listOf(
                    Folders(
                        id = 0,
                        title = "Incoming",
                        user_email = vBinding?.emailEditText?.text.toString()
                    ),
                    Folders(
                        id = 0,
                        title = "Outbound",
                        user_email = vBinding?.emailEditText?.text.toString()
                    ),
                    Folders(
                        id = 0,
                        title = "Unread",
                        user_email = vBinding?.emailEditText?.text.toString()
                    )
                )
            )
            dismiss()
        }
        vBinding?.cancelButtonCreatedEmail?.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        vBinding = null
    }
}