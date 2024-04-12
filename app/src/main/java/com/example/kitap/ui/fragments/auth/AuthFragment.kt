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
import com.example.kitap.databinding.FragmentAuthBinding
import com.example.kitap.ui.activites.MainActivity
import com.example.kitap.ui.fragments.auth.AuthViewModel
import com.example.kitap.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        setupViews()
        authWithGoogle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        authViewModel.setGoogleSignInLauncher(registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                authViewModel.handleGoogleSignInResult(data)
            }
        })

     }
    private fun setupViews() {

        binding.googleSignIn.setOnClickListener{
            authViewModel.signInWithGoogle(requireActivity())
        }
    }
    private fun authWithGoogle() {

        authViewModel.status.observe(viewLifecycleOwner) {
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

