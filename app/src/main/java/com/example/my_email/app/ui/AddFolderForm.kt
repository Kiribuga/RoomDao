package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.my_email.R
import com.example.my_email.app.data.DataInterface
import com.example.my_email.app.ui.folders.ViewModelFolders
import com.example.my_email.databinding.FormAddFolderBinding
import com.example.my_email.db.folders.Folders

class AddFolderForm(
    private val dataInterface: DataInterface
) : DialogFragment(R.layout.form_add_folder) {

    private val vmFolders: ViewModelFolders by activityViewModels()
    private var vBinding: FormAddFolderBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FormAddFolderBinding.bind(view)
        vBinding = binding
        vBinding?.addButtonCreatedFolder?.setOnClickListener {
            dataInterface.addFolder(
                Folders(
                    id = 0,
                    title = vBinding?.inputNameFolderEditText?.text.toString(),
                    user_email = vmFolders.foldersByEmailLD.value!!.run {
                        first().user_email
                    }
                )
            )
            dismiss()
        }
        vBinding?.cancelButtonCreatedFolder?.setOnClickListener { dismiss() }
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