package com.example.kitap.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kitap.databinding.FragmentChangeProfileBinding


class ChangeProfileFragment : Fragment() {
    private lateinit var binding: FragmentChangeProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}