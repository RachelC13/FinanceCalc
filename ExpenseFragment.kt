package com.example.financecalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.financecalc.databinding.FragmentExpenseBinding
import com.example.financecalc.ExpenseFragmentDirections

class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private var salary: Double = 0.0
    private var budget: Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense, container, false)
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        salary = arguments?.getDouble("salary") ?: 0.0
        budget = arguments?.getDouble("budget") ?: 0.0

        //Set click listner to the calculate button
        binding.buttonCalculate.setOnClickListener {
            //get the expenses
            val expenses =binding.editTextExpenses.text.toString().toDoubleOrNull()

            if(expenses != null) {

                val action = ExpenseFragmentDirections.actionExpenseFragmentToResultFragment(salary.toFloat(),
                    budget.toFloat(), expenses.toFloat()
                )
                findNavController().navigate(action)
            }
            else {
                print("Please enter valid expenses")
            }
        }
    }
}
