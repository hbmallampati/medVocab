package com.example.medvocab.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.medvocab.MainViewModel
import com.example.medvocab.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Sign out account
        binding.accountSignOutBt.setOnClickListener {

            if(FirebaseAuth.getInstance().currentUser != null)
            {
                viewModel.signOut(requireContext())
            }
            else
            {
                Toast.makeText(requireContext(), "You are not signed in !!!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.observeSignOutOperationStatus().observe(viewLifecycleOwner, Observer {
            if(it == 1)
            {
                if(viewModel.getSignInStatus2() == 0)
                    Toast.makeText(requireContext(), "You are already signed out !!!", Toast.LENGTH_SHORT).show()
                else
                {
                    Toast.makeText(requireContext(), "Signed out successfully", Toast.LENGTH_SHORT).show()
                    viewModel.setSIgnInStatus(0)
                }
                viewModel.setSIgnInStatus(0)
            }
            else if(it == 2)
            {
                Toast.makeText(requireContext(), "Sign out failed !!!", Toast.LENGTH_SHORT).show()
            }

        })


        //Reset all decks
        binding.resetProgressBt.setOnClickListener {

            if(FirebaseAuth.getInstance().currentUser != null)
            {
                binding.resetProgressBt.text = "Resetting"
                viewModel.resetAllDecks()
            }
            else
                Toast.makeText(requireContext(), "You are not signed in !!!", Toast.LENGTH_LONG).show()
        }

        viewModel.observeResetDone().observe(viewLifecycleOwner, Observer {
            if(it)
            {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle("Reset progress")
                alertDialogBuilder.setMessage("All decks are reset")
                alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
                    Toast.makeText(requireContext(),
                        "All decks are reset", Toast.LENGTH_SHORT).show()
                    //alertDialogBuilder.
                }

                alertDialogBuilder.show()
                binding.resetProgressBt.text = "Reset all decks"
            }
        })

    }

    override fun onDestroyView() {
        viewModel.resetResetDone()
        viewModel.resetSignOutOperationStatus()
        super.onDestroyView()
        _binding = null
    }

}