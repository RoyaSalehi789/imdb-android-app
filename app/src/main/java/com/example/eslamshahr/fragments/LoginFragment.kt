package com.example.eslamshahr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.eslamshahr.HebDatabase
import com.example.eslamshahr.R
import com.example.eslamshahr.activities.MainActivity
import com.example.eslamshahr.databinding.FragmentLoginBinding
import com.example.eslamshahr.mymovies.MovieDetailsActivity
import com.example.eslamshahr.repositories.RegisterRepository
import com.example.eslamshahr.viewModel.LoginViewModel
import com.example.eslamshahr.viewModel.LoginViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login
        ,container, false)

            val application = requireNotNull(this.activity).application

            val dao = HebDatabase.getInstance(application).iDao

            val repository = RegisterRepository(dao)

            val factory = LoginViewModelFactory(repository, application)

            loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

            binding.myLoginViewModel = loginViewModel

            binding.lifecycleOwner = this

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                NavHostFragment.findNavController(this).navigate(action)
            }
        })

        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "User does not exist. Please Register! ", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please check your password ", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {hasFinished->
            if (hasFinished == true) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        })

        return binding.root
    }
}