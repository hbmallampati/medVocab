package com.example.medvocab.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.medvocab.MainViewModel
import com.example.medvocab.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding : FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Contact us
        binding.contactUsBt.setOnClickListener {
            val recipient = "helper@medvocab.com"
            val subject = "MedVocab helper"
            val message = "Select an app to email MedVocab:"
            sendEmail(recipient, subject, message, requireContext())
        }

        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.google.com")
        )

        //Learn more
        binding.learnMoreBt.setOnClickListener {
            startActivity(browserIntent)
        }


    }

    private fun sendEmail(recipient: String, subject: String, message: String, context : Context) {

        val mIntent = Intent(Intent.ACTION_SENDTO)
        mIntent.data = Uri.parse("mailto:$recipient")
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){

            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}