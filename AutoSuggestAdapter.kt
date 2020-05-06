
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

class AutoSuggestAdapter<T : Any>(context: Context, private val resource: Int, items : List<T> = ArrayList()) :
    ArrayAdapter<T>(context, resource, items) {

    private val items : ArrayList<T> = ArrayList()
    private val suggestions : ArrayList<T> = ArrayList()
    var customLayoutHandler : ((View, T) -> Unit)? = null

    companion object {
        private const val TAG = "AutoSuggestAdapter"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var v : View
        v = if(convertView == null){
            val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(resource, parent, false)
        } else convertView

        if(suggestions.size > 0 && position < suggestions.size) {
            val item: T = suggestions[position]
            if (v is TextView) v.text = item.toString()
            else customLayoutHandler?.invoke(v, item)
        }
        
        return v
    }

    override fun getFilter() : Filter = itemFilter

    override fun add(item: T?) {
        if(item != null) super.add(item)
        if(item != null && !items.contains(item)) items.add(item)
    }

    override fun addAll(vararg items: T) {
        super.addAll(*items)
        this.items.addAll(items)
    }

    override fun addAll(collection: MutableCollection<out T>) {
        super.addAll(collection)
        this.items.addAll(collection)
    }

    fun clearItems(){
        items.clear()
    }

    inner class ItemFilter() : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if(constraint != null){
                suggestions.clear()
                items.forEach { item ->
                    if(item.toString().toLowerCase().contains(constraint.toString().toLowerCase())) suggestions.add(item)
                }
                val fResults : FilterResults = FilterResults()
                fResults.values = suggestions
                fResults.count = suggestions.size
                return fResults
            }
            return FilterResults()
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if(results != null && results.count > 0){
                var filterList : ArrayList<T> = results?.values as ArrayList<T>
                clear()
                filterList.forEach { item -> add(item) }
                notifyDataSetChanged()
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultValue?.toString() ?: ""
        }
    }
    private val itemFilter : ItemFilter = ItemFilter()
}
