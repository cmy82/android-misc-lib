
import android.widget.EditText
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import TextInputAutoCompleteEditText
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding3.widget.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RxUtils {
    companion object {
        fun rxObserveTextView(editText: TextInputEditText) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( debounceCheck )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextView(editText: TextInputEditText, debounceTime : Long) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( DebounceCheck(debounceTime) )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextView(editText: TextInputAutoCompleteEditText) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( debounceCheck )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextView(editText: TextInputAutoCompleteEditText, debounceTime : Long) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( DebounceCheck(debounceTime) )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextView(editText: AppCompatAutoCompleteTextView) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( debounceCheck )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextView(editText: AppCompatAutoCompleteTextView, debounceTime : Long) : Observable<CharSequence> {
            return editText.textChanges()
                .observeOn(Schedulers.computation())
                .debounce( DebounceCheck(debounceTime) )
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextViewEditorAction(editText: TextInputEditText) : Observable<TextViewEditorActionEvent> {
            return editText.editorActionEvents()
                .observeOn(Schedulers.computation())
                .debounce(debounceAlways)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveTextViewEditorAction(editText: TextInputEditText, debounceTime : Long) : Observable<TextViewEditorActionEvent> {
            return editText.editorActionEvents()
                .observeOn(Schedulers.computation())
                .debounce(debounceTime, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveSpinner(spinner : Spinner) : Observable<Int> {
            return spinner.itemSelections()
                .observeOn(Schedulers.computation())
                .retry()
                .debounce(debounceAlways)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveSpinner(spinner : Spinner, debounceTime : Long) : Observable<Int> {
            return spinner.itemSelections()
                .observeOn(Schedulers.computation())
                .retry()
                .debounce(debounceTime, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        //Handles switches, toggle buttons, checkboxes, radio buttons
        fun rxObserveCompoundButton(button : CompoundButton) : Observable<Boolean> {
            return button.checkedChanges()
                .observeOn(Schedulers.computation())
                .debounce(debounceAlways)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        fun rxObserveCompoundButton(button : CompoundButton, debounceTime: Long) : Observable<Boolean> {
            return button.checkedChanges()
                .observeOn(Schedulers.computation())
                .debounce(debounceTime, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
        }

        internal class DebounceNonEmptyTxt(wait : Long = 800) : io.reactivex.functions.Function<CharSequence,ObservableSource<Long>> {
            private val timeout = wait
            override fun apply(cs: CharSequence): ObservableSource<Long> =
                //if (cs.isEmpty()) Observable.just(0L) else Observable.timer(timeout, TimeUnit.MILLISECONDS) //Returning .just() does not call subscription
                if (cs.isEmpty()) Observable.timer(10, TimeUnit.MILLISECONDS) else Observable.timer(timeout, TimeUnit.MILLISECONDS)
        }
        private val debounceNonEmpty : DebounceNonEmptyTxt = DebounceNonEmptyTxt()

        internal class DebounceAlways(wait : Long = 800) : io.reactivex.functions.Function<Any, ObservableSource<Long>> {
            private val timeout = wait
            override fun apply(cs: Any): ObservableSource<Long> = Observable.timer(timeout, TimeUnit.MILLISECONDS)
        }
        private val debounceAlways : DebounceAlways = DebounceAlways()
    }
}
