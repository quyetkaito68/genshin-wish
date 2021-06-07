package com.example.genshinwish.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.genshinwish.UserAdapter
import com.example.genshinwish.databinding.FragmentPublicBinding
import com.example.genshinwish.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_public.*


class PublicFragment : Fragment() {
    companion object{
        const val SIGN_IN_RESULT_CODE = 1001
        const val TAG = "PublicFragment"

        fun newInstance(): PublicFragment{
            return PublicFragment()
        }
    }

    private lateinit var binding: FragmentPublicBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicBinding.inflate(inflater)
        binding.btnLogin.setOnClickListener{
            var email = binding.edtUsername.text.toString()
            var password = binding.edtPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Authentication OK.",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
        binding.btnRegister.setOnClickListener{
            var email = binding.edtUsername.text.toString()
            var password = binding.edtPassword.text.toString()
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(context, "Authentication success.",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return binding.root
    }




}