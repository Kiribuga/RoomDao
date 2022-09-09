package com.example.my_email.app.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.my_email.R
import com.example.my_email.app.ui.attachments.ViewModelAttachments
import com.example.my_email.app.ui.chains.ViewModelChains
import com.example.my_email.app.ui.letters.ViewModelLetters
import com.example.my_email.app.ui.signatures.ViewModelSignatures
import com.example.my_email.app.ui.statuses.ViewModelStatuses
import com.example.my_email.databinding.FormNewLetterBinding
import com.example.my_email.db.attachments.Attachments
import com.example.my_email.db.letters.Letters
import com.example.my_email.db.letters.TypeLetter
import com.example.my_email.db.lettersChains.LettersChains
import com.example.my_email.db.signatures.Signatures
import java.time.Instant

class NewLetterForm : Fragment(R.layout.form_new_letter) {

    private var vBinding: FormNewLetterBinding? = null
    private val vmSignature: ViewModelSignatures by activityViewModels()
    private val vmAttachments: ViewModelAttachments by activityViewModels()
    private val vmLetter: ViewModelLetters by activityViewModels()
    private val vmStatus: ViewModelStatuses by activityViewModels()
    private val vmLetterChain: ViewModelChains by activityViewModels()
    private val args: NewLetterFormArgs by navArgs()

    private var listSignatures: MutableList<String> = emptyList<String>().toMutableList()
    private var latest: Long = 0
    private val delay: Long = 2000
    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FormNewLetterBinding.bind(view)
        vBinding = binding
        val backgroundThread = HandlerThread("handler thread").apply {
            start()
        }
        handler = Handler(backgroundThread.looper)

        val resultLauncher = registerForActivityResult(GetContent()) { uri: Uri? ->
            uri?.let {
                vBinding?.titleAttachmentsTextView?.text =
                    it.lastPathSegment?.substringAfterLast("/")
                vmAttachments.insertAttachment(
                    Attachments(
                        id = 0,
                        title = it.lastPathSegment?.substringAfterLast("/").orEmpty(),
                        type = it.lastPathSegment?.substringAfterLast(".").orEmpty()
                    )
                )
                vmAttachments.getAllAttachments()
                vBinding?.addAttachButton?.isEnabled = false
            }
        }
        vBinding?.saveSignature?.isEnabled = false
        vBinding?.insertSignature?.isEnabled = false
        vmStatus.createStatus()
        vmStatus.getStatusById(1)
        addSignature()
        observerVM()
        val adapterDropdownMenu =
            ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu,
                listSignatures
            )
        vBinding?.autoComplete?.setAdapter(adapterDropdownMenu)
        vBinding?.autoComplete?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vBinding?.saveSignature?.isEnabled = s?.isNotEmpty() ?: false
                vBinding?.insertSignature?.isEnabled = s?.isNotEmpty() ?: false
            }
        })

        vBinding?.themeLetter?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                delay(s.toString())
            }
        })

        vBinding?.saveSignature?.setOnClickListener {
            vmSignature.insertSignature(
                Signatures(
                    id = 0,
                    text_signatures = vBinding?.autoComplete?.text.toString()
                )
            )
            Toast.makeText(context, "The signature was saved", Toast.LENGTH_SHORT).show()
        }

        vBinding?.insertSignature?.setOnClickListener {
            vBinding?.textLetter?.setText("${vBinding?.textLetter?.text}" + "\n\n\n${vBinding?.autoComplete?.text}")
            vBinding?.autoComplete?.text?.clear()
        }

        vBinding?.addAttachButton?.setOnClickListener {
            resultLauncher.launch("*/*")
        }

        vBinding?.deleteAttachButton?.setOnClickListener {
            vmAttachments.getAttachmentLD.value?.last()?.id?.let { idAttachment ->
                vmAttachments.deleteAttachment(idAttachment)
            }
            vBinding?.titleAttachmentsTextView?.text = ""
            vBinding?.addAttachButton?.isEnabled = true

        }

        vBinding?.cancelNewLetter?.setOnClickListener {
            findNavController().popBackStack()
        }

        vBinding?.sendNewLetter?.setOnClickListener {
            vmLetter.createLetter(
                Letters(
                    id = 0,
                    to_user_email = vBinding?.toUser?.text.toString(),
                    from_user_email = args.email!!,
                    attachments_title = vBinding?.titleAttachmentsTextView?.text.toString(),
                    title = vBinding?.themeLetter?.text.toString(),
                    time = Instant.now(),
                    type_letter = TypeLetter.OUTGOING,
                    chain_title = vBinding?.themeLetter?.text.toString(),
                    status_text = vmStatus.statusByIdLD.value?.text_statuses.toString(),
                    text_letter = vBinding?.textLetter?.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }

    private fun addSignature() {
        if (vmSignature.allSignaturesLD.value == emptyList<Signatures>()) {
            vmSignature.insertSignature(
                Signatures(
                    id = 0,
                    text_signatures = "No signatures"
                )
            )
            vmSignature.getAllSignatures()
        } else {
            vmSignature.getAllSignatures()
        }
    }

    private fun observerVM() {
        vmSignature.allSignaturesLD.observe(viewLifecycleOwner) { list ->
            list.forEach { listSignatures.add(it.text_signatures.toString()) }
        }
        vmAttachments.getAttachmentLD.observe(viewLifecycleOwner) {}
        vmStatus.statusByIdLD.observe(viewLifecycleOwner) {

        }
    }

    private fun delay(s: String) {
        latest = System.currentTimeMillis()
        handler.post {
            val run = Runnable {
                if (System.currentTimeMillis() - delay > latest) {
                    vmLetterChain.insertLettersChain(
                        LettersChains(
                            id = 0,
                            title = s
                        )
                    )
                    vmLetterChain.getAllLettersChains()
                }
            }
            mainHandler.postDelayed(run, delay + 50)
        }
    }

    override fun onResume() {
        super.onResume()
        listSignatures = emptyList<String>().toMutableList()
        val adapterDropdownMenu =
            ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu,
                listSignatures
            )
        vBinding?.autoComplete?.setAdapter(adapterDropdownMenu)
    }

    override fun onDestroy() {
        super.onDestroy()
        listSignatures = emptyList<String>().toMutableList()
        vBinding = null
    }
}