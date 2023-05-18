package com.javalogist.presentation

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.javalogist.MainActivity
import com.javalogist.R
import com.javalogist.data.model.ResultWrapper
import com.javalogist.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel;
        setSpannableString()
        mBinding.btnLogin.setOnClickListener {
            if (validate()) {
                mainViewModel.loginUser(
                    mBinding.etEmail.text.toString().trim(),
                    mBinding.etPassword.text.toString().trim()
                ).observe(viewLifecycleOwner){
                    when (it) {
                        is ResultWrapper.Loading -> {

                        }
                        is ResultWrapper.Success -> {
                            Snackbar.make(requireView(),"User Logged in  successfully", Snackbar.LENGTH_SHORT).show()
                            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

                        }

                        is ResultWrapper.Error -> {

                            Snackbar.make(requireView(),"An error occurred while logging in  : ${it.errorMessage}", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true;
        mBinding.apply {
            if (TextUtils.isEmpty(this.etEmail.text) || TextUtils.isEmpty(this.etPassword.text)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private fun setSpannableString() {
        val spannableString = SpannableString("New to application ? Register yourself here")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(v: View) {
                v.findNavController()
                    .navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }

        }

        spannableString.setSpan(clickableSpan, 39, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.tvRegisterHere.also {
            it.text = spannableString
            it.movementMethod = LinkMovementMethod.getInstance()
        }

    }
}