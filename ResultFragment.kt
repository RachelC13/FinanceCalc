package com.example.financecalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.financecalc.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private var salary: Double = 0.0
    private var budget: Double = 0.0
    private var expenses: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get the data from Expense Fragment
        salary = arguments?.getDouble("salary")?: 0.0
        budget = arguments?.getDouble("budget")?: 0.0
        expenses = arguments?.getDouble("expenses")?: 0.0

        //Calculate
        val remainingBudget = salary - expenses
        val resultMessage = if(remainingBudget >= 0) {
            "You stayed within your budget. Remaining budget: $remainingBudget"
        } else {
            "You went over your budget by ${-remainingBudget}"
        }

        // Show the result message in TextView
        binding.textViewResult.text = resultMessage

        binding.buttonRestart.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

    }


}
