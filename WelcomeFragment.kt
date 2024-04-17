package com.example.financecalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financecalc.databinding.FragmentWelcomeBinding
import com.example.financecalc.WelcomeFragmentDirections


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setting click listener for the next button
        binding.buttonNext.setOnClickListener{
            //Getting the entered salary and budget
            val salary = binding.editTextSalary.text.toString().toDoubleOrNull()
            val budget = binding.editTextBudget.text.toString().toDoubleOrNull()

            //Checking if both salary and budget are valid
            if(salary != null && budget != null){
                //Go to the expense fargement and pass the salary and budget data
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToExpenseFragment(salary.toFloat(),
                    budget.toFloat()
                )
                findNavController().navigate(action)
            } else {
                //displaying an erorr message
                print("Please enter valid salary and budget")
            }
        }
    }
}
