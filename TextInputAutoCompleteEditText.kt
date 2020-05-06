import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout


class TextInputAutoCompleteEditText(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatAutoCompleteTextView(context, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null, R.attr.autoCompleteTextViewStyle)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, R.attr.autoCompleteTextViewStyle)

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(outAttrs)
        if (ic != null && outAttrs.hintText == null) {
            // If we don't have a hint and our parent is a TextInputLayout, use it's hint for the
            // EditorInfo. This allows us to display a hint in 'extract mode'.
            val parent = parent
            if (parent is TextInputLayout) {
                outAttrs.hintText = parent.hint
            }
            // TextInputLayout can use an internal layout for it's children so may not be a direct child
            // and have another layout between EditText
            val gParent = parent.parent
            if (gParent is TextInputLayout) {
                outAttrs.hintText = gParent.hint
            }
        }
        return ic
    }

    override fun getHint(): CharSequence {
        val parent = parent
        if(parent is TextInputLayout){
            val hn : CharSequence? = parent.hint
            return hn!!
        }
        val gParent = parent.parent
        if(gParent is TextInputLayout){
            val hn : CharSequence? = gParent.hint
            return hn!!
        }
        return ""
    }
}
