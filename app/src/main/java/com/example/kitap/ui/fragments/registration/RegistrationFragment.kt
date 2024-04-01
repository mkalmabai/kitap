package com.example.kitap.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kitap.databinding.FragmentRegistrationBinding
import com.example.kitap.ui.activites.MainActivity
import com.example.kitap.ui.fragments.registration.RegistrationViewModel
import com.example.kitap.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val registrationViewModel: RegistrationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        setupViews()
        registrationWithGoogle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        registrationViewModel.setGoogleSignInLauncher(registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                registrationViewModel.handleGoogleSignInResult(data)
            }
        })

     }
    private fun setupViews() {
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.googleSignIn.setOnClickListener{
            registrationViewModel.signInWithGoogle(requireActivity())
        }
    }
    private fun registrationWithGoogle() {

        registrationViewModel.status.observe(viewLifecycleOwner) {
                status ->
            when (status) {
                is Resource.Loading -> {
                    showToast("Loading...")
                }
                is Resource.Success -> {
                    showToast("Authentication successful!")
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Error -> {
                    showToast("Authentication failed: ${status.message ?: "Unknown error"}")
                }
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

