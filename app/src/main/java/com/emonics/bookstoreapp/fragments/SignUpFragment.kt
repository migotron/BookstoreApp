package com.emonics.bookstoreapp.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.emonics.bookstoreapp.R
import com.emonics.bookstoreapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    // view binding
    private lateinit var binding: FragmentSignUpBinding

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    // progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // init progress dialog, will show while creating account | Register user
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        // handle back button click, go to previous screen
        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // handle click, begin register
        registerBtn.setOnClickListener {
            /* Steps
            1. Input data
            2. Validate Data
            3. Create account - Firebase auth
            4. save user info - firebase realtime database */
            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        // 1) Input Data
        name = nameEt.text.toString().trim()
        email = emailEt.text.toString().trim()
        password = passwordEt.text.toString().trim()
        val cPassword = cPasswordEt.text.toString().trim()

        // 2) Validate Data
        if (name.isEmpty()) {
            // empty name...
            Toast.makeText(context, "Enter your name...", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email pattern...
            Toast.makeText(context, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            // empty password...
            Toast.makeText(context, "Enter Password...", Toast.LENGTH_SHORT).show()
        } else if (cPassword.isEmpty()) {
            // empty confirm password...
            Toast.makeText(context, "Confirm Password...", Toast.LENGTH_SHORT).show()
        } else if (password != cPassword) {
            // password doesn't match...
            Toast.makeText(context, "Password doesn't match...", Toast.LENGTH_SHORT).show()
        } else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        // 3) Create account - Firebase Auth
        // show progress
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        // create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // account created, now add user info in db
                updateUserInfo()
            }
            .addOnFailureListener { e ->
                // failed creating account
                progressDialog.dismiss()
                Toast.makeText(
                    context,
                    "Failed creating account due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateUserInfo() {
        // 4) Save user info - Firebase Realtime Database
        progressDialog.setMessage("Saving user info...")

        // timestamp
        val timestamp = System.currentTimeMillis()

        // get current user uid, since user is registered so we can get it now
        val uid = firebaseAuth.uid

        // setup data to add in db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = "" // add empty, will do in profile edit
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        // set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                // user info saved, open user dashboard
                progressDialog.dismiss()
                Toast.makeText(context, "Account created...", Toast.LENGTH_LONG).show()
                val homeFragment = HomeFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            .addOnFailureListener { e ->
                // failed adding data to db
                progressDialog.dismiss()
                Toast.makeText(context, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}