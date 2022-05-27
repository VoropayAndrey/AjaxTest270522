package com.ajax.ajaxtestassignment.ui.details

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ajax.ajaxtestassignment.R
import com.ajax.ajaxtestassignment.databinding.FragmentDetailsBinding
import com.ajax.ajaxtestassignment.di.GlobalFactory
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    private val detailsViewModel by viewModels<DetailsViewModel> { GlobalFactory }

    private var selectedContact: ContactEntity? = null
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var photoImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)

        var contactId: Int = arguments?.getString("id")?.toInt() ?: 0

        firstNameEditText = view.findViewById(R.id.contact_first_name)
        lastNameEditText = view.findViewById(R.id.contact_last_name)
        phoneNameEditText = view.findViewById(R.id.contact_phone_number)
        emailEditText = view.findViewById(R.id.contact_email)
        photoImageView = view.findViewById(R.id.contact_photo)

        var editButton = view.findViewById<Button>(R.id.buttonEdit)
        var cancelButton = view.findViewById<Button>(R.id.buttonCancel)
        var saveButton = view.findViewById<Button>(R.id.buttonSave)

        detailsViewModel.fetchedContact.observe(viewLifecycleOwner, Observer {
            selectedContact = it

            detailsViewModel.detailState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    DetailsViewModel.DetailState.EDITING -> {
                        editButton.visibility = View.GONE
                        cancelButton.visibility = View.VISIBLE
                        saveButton.visibility = View.VISIBLE

                        firstNameEditText.inputType = InputType.TYPE_CLASS_TEXT
                        firstNameEditText.isEnabled = true

                        lastNameEditText.inputType = InputType.TYPE_CLASS_TEXT
                        lastNameEditText.isEnabled = true

                        phoneNameEditText.inputType = InputType.TYPE_CLASS_TEXT
                        phoneNameEditText.isEnabled = true

                        emailEditText.inputType = InputType.TYPE_CLASS_TEXT
                        emailEditText.isEnabled = true
                    }
                    DetailsViewModel.DetailState.VIEWING -> {
                        editButton.visibility = View.VISIBLE
                        cancelButton.visibility = View.GONE
                        saveButton.visibility = View.GONE

                        firstNameEditText.inputType = InputType.TYPE_NULL
                        firstNameEditText.isEnabled = false

                        firstNameEditText.inputType = InputType.TYPE_NULL
                        firstNameEditText.isEnabled = false

                        lastNameEditText.inputType = InputType.TYPE_NULL
                        lastNameEditText.isEnabled = false

                        phoneNameEditText.inputType = InputType.TYPE_NULL
                        phoneNameEditText.isEnabled = false

                        emailEditText.inputType = InputType.TYPE_NULL
                        emailEditText.isEnabled = false

                        updateContactInfo()
                    }
                }
            })
        })

        detailsViewModel.fetchUserById(contactId)

        editButton.setOnClickListener {
            detailsViewModel.changeState(DetailsViewModel.DetailState.EDITING)
        }

        cancelButton.setOnClickListener {
            detailsViewModel.changeState(DetailsViewModel.DetailState.VIEWING)
        }

        saveButton.setOnClickListener {
            var newContactEntity = ContactEntity(id = contactId,
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                phone1 = phoneNameEditText.text.toString(),
                email = emailEditText.text.toString(),
                photoURL = selectedContact?.photoURL!!
            )
            detailsViewModel.updateContactInfo(newContactEntity)
        }

        detailsViewModel.changeState(DetailsViewModel.DetailState.VIEWING)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun updateContactInfo() {
        Glide.with(this)
            .load(selectedContact?.photoURL)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(photoImageView)

        firstNameEditText.setText(selectedContact?.firstName)
        lastNameEditText.setText(selectedContact?.lastName)
        phoneNameEditText.setText(selectedContact?.phone1)
        emailEditText.setText(selectedContact?.email)
    }
}