package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.my_email.R
import com.example.my_email.app.data.DataInterface
import com.example.my_email.databinding.FormDeletedUserBinding

class DeleteUserForm(
    private val dataInterface: DataInterface
) : DialogFragment(R.layout.form_deleted_user) {

    private var vBinding: FormDeletedUserBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FormDeletedUserBinding.bind(view)
        vBinding = binding
        vBinding?.buttonDelEmail?.setOnClickListener {
            dataInterface.delUser(
                userEmail = vBinding?.nameEmailForDelEditText?.text.toString()
            )
            dismiss()
        }
        vBinding?.cancelButtonDelEmail?.setOnClickListener { dismiss() }
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