package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_email.R
import com.example.my_email.app.data.DataInterface
import com.example.my_email.app.data.adaptersrv.AdapterFolders
import com.example.my_email.app.data.adaptersrv.AdapterLetters
import com.example.my_email.app.data.adaptersrv.AdapterUsers
import com.example.my_email.app.ui.attachments.ViewModelAttachments
import com.example.my_email.app.ui.chains.ViewModelChains
import com.example.my_email.app.ui.folders.ViewModelFolders
import com.example.my_email.app.ui.letters.ViewModelLetters
import com.example.my_email.app.ui.users.ViewModelUsers
import com.example.my_email.app.util.autoCleared
import com.example.my_email.databinding.FragmentMainBinding
import com.example.my_email.db.folders.Folders
import com.example.my_email.db.users.Users

class MainFragment : Fragment(R.layout.fragment_main), DataInterface {

    private var vBinding: FragmentMainBinding? = null
    private var usersAdapter: AdapterUsers by autoCleared()
    private var foldersAdapter: AdapterFolders by autoCleared()
    private var lettersAdapter: AdapterLetters by autoCleared()
    private val vmUsers: ViewModelUsers by activityViewModels()
    private val vmFolders: ViewModelFolders by activityViewModels()
    private val vmLetters: ViewModelLetters by activityViewModels()
    private val vmLetterChain: ViewModelChains by activityViewModels()
    private val vmAttachments: ViewModelAttachments by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        vBinding = binding
        vBinding?.createLetter?.isEnabled = false
        vBinding?.createFolder?.isEnabled = false
        vBinding?.delEmailAddress?.isEnabled = false
        initUsers()
        initFolders()
        initLetters()
        vmUsers.getAllUsers()
        observeViewModel()
        vBinding?.addEmailAddress?.setOnClickListener {
            AddUserForm(this).show(parentFragmentManager, "result")
        }
        vBinding?.createFolder?.setOnClickListener {
            AddFolderForm(this).show(parentFragmentManager, "result")
        }
        vBinding?.delEmailAddress?.setOnClickListener {
            DeleteUserForm(this).show(parentFragmentManager, "result")
        }
        vBinding?.createLetter?.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToNewLetterForm(
                    vmUsers.userByIdLiveData.value?.email
                )
            )
        }
    }

    override fun saveUser(user: Users) {
        vmUsers.saveUser(user)
    }

    override fun standardFolders(folders: List<Folders>) {
        vmFolders.insertFolder(folders)
    }

    override fun addFolder(folder: Folders) {
        vmFolders.insertFolder(listOf(folder)).run {
            vmFolders.getFolderByEmailUser(folder.user_email)
        }
    }

    override fun delUser(userEmail: String) {
        vmFolders.removeFoldersByEmailUser(userEmail).apply {
            vmUsers.removeUserByEmail(userEmail)
        }
    }

    override fun updateUser(user: Users) {
        vmUsers.updateUser(user)
    }

    private fun observeViewModel() {
        vmUsers.allUsersLiveData.observe(viewLifecycleOwner) {
            usersAdapter.updateListUsers(it)
        }
        vmFolders.foldersByEmailLD.observe(viewLifecycleOwner) {
            foldersAdapter.updateListFolders(it)
        }
        vmLetters.allLettersLD.observe(viewLifecycleOwner) {
            lettersAdapter.updateListLetters(it)
        }
        vmLetters.letterByToEmailLD.observe(viewLifecycleOwner) {
            lettersAdapter.updateListLetters(it)
        }
        vmLetters.letterByFromEmailLD.observe(viewLifecycleOwner) {
            lettersAdapter.updateListLetters(it)
        }
        vmLetterChain.loadLD.observe(viewLifecycleOwner) {
            loadVisible(it)
        }
        vmAttachments.loadLD.observe(viewLifecycleOwner) {
            loadVisible(it)
        }
        vmUsers.loadLD.observe(viewLifecycleOwner) {
            loadVisible(it)
        }
        vmFolders.loadLD.observe(viewLifecycleOwner) {
            loadVisible(it)
        }
        vmLetters.loadLD.observe(viewLifecycleOwner) {
            loadVisible(it)
        }
    }

    private fun loadVisible(result: Boolean) {
        vBinding?.loadPB?.isVisible = result
        if (!result) vmUsers.getAllUsers()
    }

    private fun initUsers() {
        usersAdapter = AdapterUsers({
            vmFolders.getFolderByEmailUser(it.email)
            vmUsers.getUserById(it.id)
            vmLetters.getAllLetters()
            vBinding?.createLetter?.isEnabled = true
            vBinding?.createFolder?.isEnabled = true
            vBinding?.delEmailAddress?.isEnabled = true
        },
            {
                vmUsers.getUserById(it.id)
                InfoUserFragment(this).show(parentFragmentManager, "result")
            }
        )
        with(vBinding?.listEmailsAddresses) {
            this?.adapter = usersAdapter
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.setHasFixedSize(true)
        }
    }

    private fun initFolders() {
        foldersAdapter = AdapterFolders({ position ->
            when (position) {
                0 -> {
                    vmLetters.getLettersByToEmail(vmUsers.userByIdLiveData.value?.email.orEmpty())
                }
                1 -> {
                    vmLetters.getLettersByFromEmail(vmUsers.userByIdLiveData.value?.email.orEmpty())
                }
                2 -> {
                    vmLetters.getLettersByToEmail(vmUsers.userByIdLiveData.value?.email.orEmpty())
                }
                else -> vmLetters.allLettersLD.value?.let { lettersAdapter.updateListLetters(it) }
            }
        }, { folder ->
            vmFolders.removeFolderByTitle(folder.id)
            vmFolders.getFolderByEmailUser(folder.user_email)
        })
        with(vBinding?.listFolders) {
            this?.adapter = foldersAdapter
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.setHasFixedSize(true)
        }
    }

    private fun initLetters() {
        lettersAdapter = AdapterLetters { letter ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToFragmentLetterWithChain(letter.id)
            )
        }
        with(vBinding?.listLetters) {
            this?.adapter = lettersAdapter
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        vmUsers.getAllUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        vBinding = null
    }
}