package com.basicbug.core.util.ktx

import androidx.databinding.ObservableField

/**
 * @author bsgreentea
 */
fun ObservableField<String>.isNullOrBlank(): Boolean {
    return this.get().isNullOrBlank()
}