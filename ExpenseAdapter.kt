package com.example.financecalc
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenseList: List<ExpenseItem>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentItem = expenseList[position]
        holder.textViewExpenseName.text = currentItem.name
        holder.textViewExpenseAmount.text = currentItem.amount.toString()
    }

    override fun getItemCount() = expenseList.size

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewExpenseName: TextView = itemView.findViewById(R.id.textViewExpenseName)
        val textViewExpenseAmount: TextView = itemView.findViewById(R.id.editTextExpenseAmount)
    }
}
