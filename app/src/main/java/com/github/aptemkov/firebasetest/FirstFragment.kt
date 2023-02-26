package com.github.aptemkov.firebasetest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aptemkov.firebasetest.databinding.FragmentFirstBinding
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseStore: FirebaseFirestore
    private var adapter: UsersAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        firebaseStore = FirebaseFirestore.getInstance()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UsersAdapter {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }


        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this.context)
            this.adapter = adapter
        }

        firebaseStore.collection("users").addSnapshotListener { value, error ->
            if (value != null) {
                val users = value.toObjects(User::class.java)
                adapter.submitList(users)
                Toast.makeText(context, "${users.size}", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()
            }
        }


        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}