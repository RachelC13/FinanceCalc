package com.example.financecalc
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financecalc.databinding.FragmentExpenseBinding

class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var sharedPref: SharedPreferences
    private val expenseList = mutableListOf<ExpenseItem>()
    private var salary: Float = 0f
    private var budget: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        salary = sharedPref.getFloat("salary",0f)
        budget = sharedPref.getFloat("budget", 0f)
        val expenseSet = sharedPref.getStringSet("expenses", setOf()) ?: setOf()
        expenseSet.forEach {expense ->
            val parts = expense.split(",")
            if(parts.size==2) {
                val expenseItem = ExpenseItem(parts[0], parts[1].toDouble())
                expenseList.add(expenseItem)
            }
        }



        val adapter = ExpenseAdapter(expenseList)
        binding.recyclerViewExpenses.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewExpenses.adapter = adapter

        binding.buttonAddExpense.setOnClickListener {
            addExpense()
        }

        binding.buttonCalculate.setOnClickListener {
            calculateExpenses()
        }
    }

    private fun addExpense() {
        val expenseName = binding.editTextExpenseName.text.toString()
        val expenseAmount = binding.editTextExpenseAmount.text.toString().toDoubleOrNull()

        if (expenseName.isNotEmpty() && expenseAmount != null) {
            val expenseItem = ExpenseItem(expenseName, expenseAmount)
            expenseList.add(expenseItem)
            binding.recyclerViewExpenses.adapter?.notifyItemInserted(expenseList.size - 1)
            binding.editTextExpenseName.text.clear()
            binding.editTextExpenseAmount.text.clear()

            saveExpenseList()
        }
    }

    private fun calculateExpenses() {
        val totalExpenses = expenseList.sumByDouble { it.amount.toDouble() }
        val remainingBudget = budget - totalExpenses.toFloat()

        with(sharedPref.edit()) {
            putFloat("salary", salary)
            putFloat("budget",budget)
            putStringSet("expenses",expenseList.map {"${it.name}, ${it.amount}"}.toSet())
            apply()
        }
        val action = ExpenseFragmentDirections
            .actionExpenseFragmentToResultFragment(salary.toFloat(), budget.toFloat(), remainingBudget.toFloat())
        findNavController().navigate(action)
    }
    private fun saveExpenseList() {
        with(sharedPref.edit()) {
            putStringSet("expenses", expenseList.map { "${it.name},${it.amount}"}.toSet())
            apply()
        }
    }
}
