package my.edu.tarc.hungerhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.model.Request

class RequestAdapter: RecyclerView.Adapter<RequestAdapter.ViewHolder>(){
    private val dataSet = ArrayList<Request>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewReason: TextView = view.findViewById(R.id.textViewReason)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
    }

    internal fun updateRequestList(requestList: List<Request>) {
        this.dataSet.clear()
        this.dataSet.addAll(requestList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.status_card, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRequest = dataSet[position]
        holder.textViewReason.text = currentRequest.reason
        holder.textViewDate.text = currentRequest.date
    }
}

