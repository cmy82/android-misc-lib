package com.backroomsoftwarellc.app.lib.ui.util

import android.widget.EditText
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.backroomsoftwarellc.app.lib.ui.TextInputAutoCompleteEditText
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding3.widget.textChanges
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

        internal class DebounceCheck(wait : Long = 800) : io.reactivex.functions.Function<CharSequence,ObservableSource<Long>> {
            private val timeout = wait
            override fun apply(cs: CharSequence): ObservableSource<Long> =
                //if (cs.isEmpty()) Observable.just(0L) else Observable.timer(timeout, TimeUnit.MILLISECONDS) //Returning .just() does not call subscription
                if (cs.isEmpty()) Observable.timer(5, TimeUnit.MILLISECONDS) else Observable.timer(timeout, TimeUnit.MILLISECONDS)
        }
        private val debounceCheck : DebounceCheck = DebounceCheck()
    }
}