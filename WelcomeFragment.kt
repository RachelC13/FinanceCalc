package com.example.financecalc

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.financecalc.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var sharedPref: SharedPreferences

    private var salary: Float = 0f
    private var budget: Float = 0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null) {
            sharedPref.edit().clear().apply()
        }
        salary = 0f
        budget = 0f

        val savedSalary = sharedPref.getFloat("salary", 0f)
        val savedBudget = sharedPref.getFloat("budget", 0f)
        binding.editTextSalary.setText(savedSalary.toString())
        binding.editTextBudget.setText(savedBudget.toString())

        //Setting click listener for the next button
        binding.buttonNext.setOnClickListener{
            //Getting the entered salary and budget
            val salary = binding.editTextSalary.text.toString().toFloatOrNull()
            val budget = binding.editTextBudget.text.toString().toFloatOrNull()
            val expenses = binding.editTextBudget.text.toString().toFloatOrNull()

            //Checking if both salary and budget are valid
            if(salary != null && budget != null){
                with(sharedPref.edit()){
                    putFloat("salary", salary)
                    putFloat("budget",budget)
                    apply()
                }
                //Go to the expense fragment and pass the salary and budget data
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToExpenseFragment(salary.toFloat(), budget.toFloat())
                findNavController().navigate(action)
            } else {
                //displaying an error message
                print("Please enter valid salary and budget")
            }
        }
    }
}
