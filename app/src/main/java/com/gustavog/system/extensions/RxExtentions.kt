package com.gustavog.system.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

fun <T> Flowable<T>.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this.subscribe({
        // EMPTY
    }, {
        // EMPTY
    }))
}

fun <T> Single<T>.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this.subscribe({
        // EMPTY
    }, {
        // EMPTY
    }))
}

fun <T> Observable<T>.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this.subscribe({
        // EMPTY
    }, {
        // EMPTY
    }))
}

fun Completable.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this.subscribe({
        // EMPTY
    }, {
        // EMPTY
    }))
}
