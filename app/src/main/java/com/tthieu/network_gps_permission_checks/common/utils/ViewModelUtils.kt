package com.tthieu.network_gps_permission_checks.common.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * MediatorLiveData combining three different sources of LiveData.
 * Sets the value to the result of a function that is called when all three LiveData have claim
 * or when they receive updates after that.
 */
fun <T, A, B, C> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    onChange: (A?, B?, C?) -> T
): MediatorLiveData<T> {

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source1Value: A? = this.value
        val source2Value: B? = other1.value
        val source3Value: C? = other2.value

        result.value = onChange.invoke(source1Value, source2Value, source3Value)
    }

    result.addSource(this) { mergeF.invoke() }
    result.addSource(other1) { mergeF.invoke() }
    result.addSource(other2) { mergeF.invoke() }

    return result
}

/**
 * MediatorLiveData combining three different sources of LiveData.
 * Sets the value to the result of a function that is called when all three LiveData have claim
 * or when they receive updates after that.
 */
fun <T, A, B, C, D> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    other3: LiveData<D>,
    onChange: (A?, B?, C?, D?) -> T
): MediatorLiveData<T> {

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source1Value: A? = this.value
        val source2Value: B? = other1.value
        val source3Value: C? = other2.value
        val source4Value: D? = other3.value

        result.value = onChange.invoke(source1Value, source2Value, source3Value, source4Value)
    }

    result.addSource(this) { mergeF.invoke() }
    result.addSource(other1) { mergeF.invoke() }
    result.addSource(other2) { mergeF.invoke() }
    result.addSource(other3) { mergeF.invoke() }
    return result
}

/**
 * MediatorLiveData combining three different sources of LiveData.
 * Sets the value to the result of a function that is called when all three LiveData have claim
 * or when they receive updates after that.
 */
fun <T, A, B, C, D, E> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    other3: LiveData<D>,
    other4: LiveData<E>,
    onChange: (A?, B?, C?, D?, E?) -> T
): MediatorLiveData<T> {

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source1Value: A? = this.value
        val source2Value: B? = other1.value
        val source3Value: C? = other2.value
        val source4Value: D? = other3.value
        val source5Value: E? = other4.value

        result.value = onChange.invoke(source1Value, source2Value, source3Value, source4Value, source5Value)
    }

    result.addSource(this) { mergeF.invoke() }
    result.addSource(other1) { mergeF.invoke() }
    result.addSource(other2) { mergeF.invoke() }
    result.addSource(other3) { mergeF.invoke() }
    result.addSource(other4) { mergeF.invoke() }
    return result
}

fun <T, A, B, C, D, E, F> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    other3: LiveData<D>,
    other4: LiveData<E>,
    other5: LiveData<F>,
    onChange: (A?, B?, C?, D?, E?, F?) -> T
): MediatorLiveData<T> {

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source1Value: A? = this.value
        val source2Value: B? = other1.value
        val source3Value: C? = other2.value
        val source4Value: D? = other3.value
        val source5Value: E? = other4.value
        val source6Value: F? = other5.value

        result.value = onChange.invoke(source1Value, source2Value, source3Value, source4Value, source5Value, source6Value)
    }

    result.addSource(this) { mergeF.invoke() }
    result.addSource(other1) { mergeF.invoke() }
    result.addSource(other2) { mergeF.invoke() }
    result.addSource(other3) { mergeF.invoke() }
    result.addSource(other4) { mergeF.invoke() }
    result.addSource(other5) { mergeF.invoke() }
    return result
}


/**
 * MediatorLiveData combining two other sources of LiveData.
 * Sets the value to the result of a function that is called when at least one LiveData has claim
 * or when at least one receives updates after that.
 */
fun <T, A, B> LiveData<A>.combine(other: LiveData<B>, onChange: (A?, B?) -> T): MediatorLiveData<T> {

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source1Value: A? = this.value
        val source2Value: B? = other.value

        result.value = onChange.invoke(source1Value, source2Value)
    }

    result.addSource(this) { mergeF.invoke() }
    result.addSource(other) { mergeF.invoke() }

    return result
}


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }