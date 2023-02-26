package com.github.aptemkov.firebasetest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.github.aptemkov.firebasetest.databinding.FragmentSecondBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    private lateinit var firebaseStore: FirebaseFirestore
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        firebaseStore = FirebaseFirestore.getInstance()
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveUser.setOnClickListener {
            saveUser(
                name = binding.editTextName.text.toString(),
                lastName = binding.editTextLastName.text.toString(),
                age = binding.editTextAge.text.toString().toInt(),
                sex = binding.spinnerSex.selectedItem.toString(),
            )
        }
    }

    private fun saveUser(name: String, lastName: String, age: Int, sex: String) {
        if (name.isNotBlank() && lastName.isNotBlank() && sex.isNotBlank()) {
            firebaseStore.collection("users").add(
                User(name, lastName, age, sex)
            )
                .addOnSuccessListener {
                    findNavController().popBackStack()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error: " + it.message, Toast.LENGTH_SHORT).show();
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}