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
import com.javalogist.data.model.User
import com.javalogist.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var mBinding: FragmentRegistrationBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRegistrationBinding.bind(
            inflater.inflate(
                R.layout.fragment_registration,
                container,
                false
            )
        )
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpannableString()
        mainViewModel = (activity as MainActivity).mainViewModel
        mBinding.progressCircular.visibility = View.GONE
        mBinding.btnContinue.setOnClickListener {
            if (validate()) {
                mBinding.apply {
                    val user = User(
                        null,
                        this.tvFname.text.toString(),
                        this.tvLname.text.toString(),
                        this.tvMobileNumber.text.toString(),
                        this.tvEmailAddress.text.toString(),
                        this.tvPassword.text.toString()
                    )
                    mainViewModel.registerUser(user).observe(viewLifecycleOwner) {
                        when (it) {
                            is ResultWrapper.Loading -> {
                                mBinding.progressCircular.visibility = View.VISIBLE
                            }

                            is ResultWrapper.Success -> {
                                mBinding.progressCircular.visibility = View.GONE
                                Snackbar.make(
                                    requireView(),
                                    "User registered successfully",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                view.findNavController()
                                    .navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())
                            }

                            is ResultWrapper.Error -> {
                                mBinding.progressCircular.visibility = View.GONE
                                Snackbar.make(
                                    requireView(),
                                    "An error occurred while registering : ${it.errorMessage}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            } else {
                Snackbar.make(view, "Field marked with asterisk are required", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setSpannableString() {
        val spannableString = SpannableString("Already have an account ? Log In")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(v: View) {
                v.findNavController()
                    .navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
            }

        }

        spannableString.setSpan(clickableSpan, 26, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.tvLoginHere.also {
            it.text = spannableString
            it.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun validate(): Boolean {
        var isValid = true
        mBinding.apply {
            if (TextUtils.isEmpty(this.tvFname.text)
                || TextUtils.isEmpty(this.tvLname.text)
                || TextUtils.isEmpty(this.tvMobileNumber.text)
                || TextUtils.isEmpty(this.tvEmailAddress.text)
                || TextUtils.isEmpty(this.tvPassword.text)
                || TextUtils.isEmpty(this.tvConfirmPassword.text)
                || !(TextUtils.equals(this.tvPassword.text, this.tvConfirmPassword.text))
            ) {
                isValid = false
            }
        }
        return isValid
    }
}