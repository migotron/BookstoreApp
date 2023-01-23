package com.emonics.bookstoreapp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.emonics.bookstoreapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    // progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // init progress dialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        noAccountTv.setOnClickListener {
            parentFragmentManager.popBackStack()
            // switch to sign up fragment
            val signUpFragment = SignUpFragment()
            // get the support fragment manager instance
            val manager = parentFragmentManager
            // begin fragment transaction using fragment manager
            val transaction = manager.beginTransaction()
            // replace fragment in the container and finish transaction
            transaction.replace(R.id.fragment_container, signUpFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        //handle click, begin login
        loginBtn.setOnClickListener {
            /*Steps
            1) Input Data
            2) Validate Data
            3) Login - Firebase Auth
            4) Check user type - Firebase Auth
                If user - move to user dashboard
                IF admin - move to admin dashboard
             */
            validateData()
        }
    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        // 1) Input Data
        email = emailEtLogin.text.toString().trim()
        password = passwordEtLogin.text.toString().trim()

        // 2) Validate Data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email format...", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(context, "Enter password...", Toast.LENGTH_SHORT).show()
        } else {
            loginUser()
        }

    }

    private fun loginUser() {
        // 3) Login - Firebase Auth

        //show progress
        progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // login success
//                checkUser()
                // user info saved, open user dashboard
                progressDialog.dismiss()
                Toast.makeText(context, "Account authenticated...", Toast.LENGTH_LONG).show()
                val homeFragment = HomeFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            .addOnFailureListener { e ->
                // login failed
                progressDialog.dismiss()
                Toast.makeText(context, "Login failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    /*private fun checkUser() {
        *//*4) Check user type - Firebase Auth
                If user - move to user dashboard
                If admin - move to admin dashboard         *//*

        progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    // get user type e.g. user or admin
                    val userType = snapshot.child("userType").value
                    if (userType == "user") {
                        // it's a simple user, open use dashboard
                        startActivity(Intent(this@LoginActivity, DashboardUserActivity::class.java))
                        finish()
                    } else if (userType == "admin") {
                        // its admin, open admin dashboard
                        startActivity(Intent(this@LoginActivity, DashboardAdminActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }*/
}