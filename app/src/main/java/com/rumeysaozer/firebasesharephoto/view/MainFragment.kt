package com.rumeysaozer.firebasesharephoto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rumeysaozer.firebasesharephoto.R
import com.rumeysaozer.firebasesharephoto.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUp.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if(email.equals("") || password.equals("")){
              Toast.makeText(requireContext(),"Email and password needed", Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                   val action = MainFragmentDirections.actionMainFragmentToFeedFragment2()
                   findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.SignIn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if(email.equals("") || password.equals("")){
                Toast.makeText(requireContext(),"Email and password needed",Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    val action = MainFragmentDirections.actionMainFragmentToFeedFragment2()
                    findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
        val currentUser = auth.currentUser
        if(currentUser != null){
            val action = MainFragmentDirections.actionMainFragmentToFeedFragment2()
            findNavController().navigate(action)
        }
    }


}