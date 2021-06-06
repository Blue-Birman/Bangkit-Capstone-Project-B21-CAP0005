package com.overheat.capstoneproject.ui.profile

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.overheat.capstoneproject.MainActivity
import com.overheat.capstoneproject.core.ui.ProfileHistoryAdapter
import com.overheat.capstoneproject.core.utils.HashAlgorithm
import com.overheat.capstoneproject.databinding.FragmentProfileBinding
import io.reactivex.Observable
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (MainActivity.profileType != 0) {
            updateProfileFragment(MainActivity.profileType)
        } else {
            if (viewModel.isLogin()) {
                updateProfileFragment(1)
            } else {
                updateProfileFragment(2)
            }
        }
    }

    private fun updateProfileFragment(layout: Int) {
        when (layout) {
            1 -> { showProfileLayout() }
            2 -> { showSignInLayout() }
            3 -> { showRegisterLayout() }
        }
    }

    private fun showProfileLayout() {
        binding.containerProfile.parent.visibility = View.VISIBLE
        binding.containerSignIn.parent.visibility = View.GONE
        binding.containerRegister.parent.visibility = View.GONE

        with(binding.containerProfile) {
            tvEmail.text = viewModel.getUserEmail()
            tvFullName.text = viewModel.getUserName()

            val adapter = ProfileHistoryAdapter()
            viewModel.getUserHistory()
            subtitleRvHistory.visibility = View.GONE
            viewModel.listDiagnose.observe(viewLifecycleOwner, { data ->
                if (data != null) {
                    adapter.setHistory(data)
                    if (data.isNotEmpty()) {
                        subtitleRvHistory.visibility = View.VISIBLE
                    }
                }
            })

            rvHistory.layoutManager = LinearLayoutManager(context)
            rvHistory.setHasFixedSize(true)
            rvHistory.adapter = adapter
        }
    }

    private fun showSignInLayout() {
        binding.containerProfile.parent.visibility = View.GONE
        binding.containerSignIn.parent.visibility = View.VISIBLE
        binding.containerRegister.parent.visibility = View.GONE

        with(binding.containerSignIn) {
            val emailStream = RxTextView.textChanges(etEmail)
                .skipInitialValue()
                .map { email ->
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }

            emailStream.subscribe { isNotValid ->
                if (isNotValid) {
                    btnLogin.isEnabled = false
                    etEmail.error = "Email is not valid"
                } else {
                    btnLogin.isEnabled = true
                }
            }

            btnLogin.setOnClickListener {
                var isValid = true
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email == "") {
                    etEmail.error = "This field must be filled"
                    isValid = false
                }

                if (password == "") {
                    etPassword.error = "This field must be filled"
                    isValid = false
                }

                if (isValid) {
                    val passHash = HashAlgorithm.sha256(password)
                    viewModel.loginRemote(email, passHash)
                    viewModel.successLogin.observe(viewLifecycleOwner, { isLogin ->
                        if (isLogin != null) {
                            if (isLogin) {
                                Toast.makeText(
                                    context, "You have successfuly login", Toast.LENGTH_SHORT
                                ).show()
                                updateProfileFragment(1)
                            } else {
                                Toast.makeText(
                                    context, "Login unsuccessful", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }

            tvLinkRegister.setOnClickListener {
                updateProfileFragment(3)
            }
        }
    }

    private fun showRegisterLayout() {
        binding.containerProfile.parent.visibility = View.GONE
        binding.containerSignIn.parent.visibility = View.GONE
        binding.containerRegister.parent.visibility = View.VISIBLE

        with(binding.containerRegister) {
            val emailStream = RxTextView.textChanges(etEmail)
                .skipInitialValue()
                .map { email ->
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }

            emailStream.subscribe { isNotValid ->
                if (isNotValid) {
                    etEmail.error = "Email is not valid"
                }
            }

            val passwordStream = RxTextView.textChanges(etPassword)
                .skipInitialValue()
                .map { password ->
                    password.length < 6
                }

            passwordStream.subscribe { passwordShort ->
                if (passwordShort) {
                    etPassword.error = "Password must equals or more than 6 characters"
                }
            }

            val passwordConfirmationStream = Observable.merge(
                RxTextView.textChanges(etPassword)
                    .map { password ->
                        password.toString() != etConfirmPassword.text.toString()
                    },
                RxTextView.textChanges(etConfirmPassword)
                    .map { confirmPassword ->
                        confirmPassword.toString() != etPassword.text.toString()
                    }
            )

            passwordConfirmationStream.subscribe { isNotSame ->
                if (isNotSame) {
                    etConfirmPassword.error = "Confirm password is not the same with password above"
                }
            }

            val invalidFieldsStream = Observable.combineLatest(
                emailStream,
                passwordStream,
                passwordConfirmationStream,
                { emailInvalid: Boolean, shortPassword: Boolean, passwordNotSame: Boolean ->
                    !emailInvalid && !shortPassword && !passwordNotSame
                }
            )

            invalidFieldsStream.subscribe { isValid ->
                btnLogin.isEnabled = isValid
            }

            btnLogin.setOnClickListener {
                var isValid = true
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (name == "") {
                    etName.error = "This field must be filled"
                    isValid = false
                }

                if (email == "") {
                    etEmail.error = "This field must be filled"
                    isValid = false
                }

                if (password == "") {
                    etPassword.error = "This field must be filled"
                    isValid = false
                }

                if (confirmPassword == "") {
                    etConfirmPassword.error = "This field must be filled"
                    isValid = false
                }

                if (isValid) {
                    val passHash = HashAlgorithm.sha256(password)
                    viewModel.addNewUserRemote(name, email, passHash)
                    viewModel.added.observe(viewLifecycleOwner, { isAccepted ->
                        if (isAccepted != null) {
                            if (isAccepted) {
                                Toast.makeText(
                                    context, "You have successfully registered!", Toast.LENGTH_SHORT
                                ).show()
                                updateProfileFragment(1)
                            } else {
                                Toast.makeText(
                                    context, "Register failed, please try again.", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }

            tvLinkLogin.setOnClickListener {
                updateProfileFragment(2)
            }
        }
    }
}