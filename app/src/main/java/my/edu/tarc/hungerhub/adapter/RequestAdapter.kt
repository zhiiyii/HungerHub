package my.edu.tarc.hungerhub.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.model.Request

class RequestAdapter(): RecyclerView.Adapter<RequestAdapter.ViewHolder>(){
    private var dataSet = emptyList<Request>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewReason: TextView = view.findViewById(R.id.textViewReason)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewApprovalStatus: TextView = view.findViewById(R.id.textViewApprovalStatus)
    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setForm(request: List<Request>){
        dataSet = request
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.status_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = dataSet[position]
        holder.textViewReason.text = request.reason
        holder.textViewDate.text = request.date
        holder.textViewApprovalStatus.text = request.approvalStatus
    }
}

