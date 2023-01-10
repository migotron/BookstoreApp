package com.emonics.bookstoreapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.emonics.bookstoreapp.R
import com.emonics.bookstoreapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_tv.setOnClickListener {
            Toast.makeText(context, "Welcome to home fragment!", Toast.LENGTH_SHORT).show()
        }
        sign_up_btn.setOnClickListener {
            parentFragmentManager.popBackStack()
            // switch to sign up fragment
            val signUpFragment = SignUpFragment()
            // get the support fragment manager instance
            val manager = parentFragmentManager
            // begin fragment transaction using fragment manager
            val transaction = manager.beginTransaction()
            // replace fragment in the container and finish transaction
            transaction.replace(R.id.fragment_container, signUpFragment)
            transaction.addToBackStack("sign up fragment from home")
            transaction.commit()
        }
      login_btn_home.setOnClickListener {
          parentFragmentManager.popBackStack()
          val loginFragment = LoginFragment()
          val transaction = parentFragmentManager.beginTransaction()
          transaction.replace(R.id.fragment_container, loginFragment)
          transaction.addToBackStack("login from signup already have an account")
          transaction.commit()
      }
    }

}