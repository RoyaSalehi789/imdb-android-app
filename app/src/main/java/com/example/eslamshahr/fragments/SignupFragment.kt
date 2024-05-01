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
import com.example.eslamshahr.databinding.FragmentArtistsBinding
import com.example.eslamshahr.databinding.FragmentLoginBinding
import com.example.eslamshahr.databinding.FragmentSignupBinding
import com.example.eslamshahr.repositories.RegisterRepository
import com.example.eslamshahr.viewModel.LoginViewModel
import com.example.eslamshahr.viewModel.SignupViewModel
import com.example.eslamshahr.viewModel.SignupViewModelFactory
import kotlinx.android.synthetic.main.fragment_filter.*

class SignupFragment : Fragment() {
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignupBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_signup, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = HebDatabase.getInstance(application).iDao

        val repository = RegisterRepository(dao)

        val factory = SignupViewModelFactory(repository, application)

        signupViewModel = ViewModelProvider(this, factory).get(SignupViewModel::class.java)

        binding.mySignupViewModel = signupViewModel

        binding.lifecycleOwner = this

        signupViewModel.navigateTo.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                signupViewModel.doneNavigating()
            }
        })

        signupViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                NavHostFragment.findNavController(this).navigate(action)
            }
        })
        signupViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                signupViewModel.donetoast()
            }
        })

        signupViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT).show()
                signupViewModel.donetoastUserName()
            }
        })
        return  binding.root
    }
}