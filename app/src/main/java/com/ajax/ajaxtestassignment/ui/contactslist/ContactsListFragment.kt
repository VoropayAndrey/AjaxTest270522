package com.ajax.ajaxtestassignment.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajax.ajaxtestassignment.R
import com.ajax.ajaxtestassignment.databinding.FragmentContactsListBinding
import com.ajax.ajaxtestassignment.di.GlobalFactory
import com.ajax.ajaxtestassignment.domain.entities.ContactEntity
import com.ajax.ajaxtestassignment.ui.details.DetailsViewModel

open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }

    private fun onContactClicked(entity: ContactEntity) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(entity.id.toString()))
    }

    private var binding: FragmentContactsListBinding? = null

    private val contactsListViewModel by viewModels<ContactsListViewModel> { GlobalFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a vertical Layout Manager
        var view = FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter
            }
            .also {
                binding = it
            }
            .root

        view.findViewById<Button>(R.id.generateAgain).setOnClickListener {
            GlobalFactory.settingsRepo.setFirstRun(true)
            contactsListViewModel.fetchUsers()
        }

        contactsListViewModel.contactsLiveData.observe(viewLifecycleOwner, Observer {
            contactAdapter.items = it
            contactAdapter.notifyDataSetChanged()
        })

        contactsListViewModel.fetchUsers()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}