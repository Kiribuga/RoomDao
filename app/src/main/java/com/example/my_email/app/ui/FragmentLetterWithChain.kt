package com.example.my_email.app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.my_email.R
import com.example.my_email.app.ui.letters.ViewModelLetters
import com.example.my_email.app.ui.statuses.ViewModelStatuses
import com.example.my_email.databinding.LetterWithChainBinding
import com.example.my_email.db.letterStatuses.LetterStatuses
import com.example.my_email.db.letterStatuses.Status
import com.example.my_email.db.letters.Letters
import com.example.my_email.db.letters.TypeLetter

class FragmentLetterWithChain : Fragment(R.layout.letter_with_chain) {

    private var vBinding: LetterWithChainBinding? = null
    private val args: FragmentLetterWithChainArgs by navArgs()
    private val vmLetter: ViewModelLetters by activityViewModels()
    private val vmStatus: ViewModelStatuses by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = LetterWithChainBinding.bind(view)
        vBinding = binding

        vmLetter.getLettersById(args.idLetter)
        observe()

        vBinding?.deleteButton?.setOnClickListener {
            vmLetter.removeLetterById(args.idLetter)
            findNavController().popBackStack()
        }

    }

    private fun observe() {
        vmLetter.letterByIdLD.observe(viewLifecycleOwner) {
            vBinding?.fromUserTextView?.text = it?.from_user_email
            vBinding?.textLetterTextView?.text = it?.text_letter
            vBinding?.themeLetterTextView?.text = it?.chain_title
            vBinding?.titleAttach?.text = it?.attachments_title
            updateLetter(it!!)
        }
    }

    private fun updateStatus(): String {
        vmStatus.updateStatus(
            LetterStatuses(
                id = 2,
                text_statuses = Status.READ
            )
        )
        return Status.READ.toString()
    }

    private fun updateLetter(letter: Letters) {
        vmLetter.updateLetter(
            Letters(
                id = letter.id,
                to_user_email = letter.to_user_email,
                from_user_email = letter.from_user_email,
                attachments_title = letter.attachments_title,
                title = letter.title,
                time = letter.time,
                type_letter = TypeLetter.INCOMING,
                chain_title = letter.chain_title,
                status_text = updateStatus(),
                text_letter = letter.text_letter
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        vBinding = null
    }
}