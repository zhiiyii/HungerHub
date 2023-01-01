package my.edu.tarc.hungerhub.ui.request

import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.hungerhub.R

class RequestAdapter() :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>(){

    //private val dataSet: List<Form>
    //TODO: Change to private var
    private var dataSet = emptyList<Request>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewReason: TextView = view.findViewById(R.id.textViewReason)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewApprovalStatus: TextView = view.findViewById(R.id.textViewApprovalStatus)

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener {
            }
        }
    }

    internal fun setForm(request: List<Request>){
        dataSet = request
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
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
    }
}

