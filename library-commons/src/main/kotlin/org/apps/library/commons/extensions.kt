package org.apps.library.commons

import java.util.Optional

fun <T> Optional<T>.unwrap(): T? = orElse(null)
