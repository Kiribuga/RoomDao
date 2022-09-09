package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.my_email.R
import com.example.my_email.app.data.DataInterface
import com.example.my_email.app.ui.users.ViewModelUsers
import com.example.my_email.databinding.InfoUserFragmentBinding
import com.example.my_email.db.users.Users

class InfoUserFragment(
    private val dataInterface: DataInterface
) : DialogFragment(R.layout.info_user_fragment) {

    private var vBinding: InfoUserFragmentBinding? = null
    private val vmUsers: ViewModelUsers by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = InfoUserFragmentBinding.bind(view)
        vBinding = binding
        observeViewModel()
        inputData()
        vBinding?.addButtonUpdateUser?.setOnClickListener {
            dataInterface.updateUser(
                Users(
                    id = vmUsers.userByIdLiveData.value!!.id,
                    first_name = vBinding?.firstNameUpdateUserEditText?.text.toString(),
                    last_name = vBinding?.lastNameUpdateUserEditText?.text.toString(),
                    avatar = vBinding?.avatarUpdateUserEditText?.text.toString(),
                    email = vBinding?.emailUpdateEditText?.text.toString()
                )
            )
            dismiss()
        }
        vBinding?.cancelButtonUpdateUser?.setOnClickListener { dismiss() }
    }

    private fun inputData() {
        val user = vmUsers.userByIdLiveData.value
        vBinding?.firstNameUpdateUserEditText?.setText(user?.first_name)
        vBinding?.lastNameUpdateUserEditText?.setText(user?.last_name)
        vBinding?.avatarUpdateUserEditText?.setText(user?.avatar)
        vBinding?.emailUpdateEditText?.setText(user?.email)
    }

    private fun observeViewModel() {
        vmUsers.userByIdLiveData.observe(viewLifecycleOwner) {
            inputData()
        }
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