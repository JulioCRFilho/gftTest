package com.example.desafio_android_julio_cesar.utils

import java.math.BigInteger
import java.security.MessageDigest

fun generateHash(ts: String, apikey: String): String {
    val key = "ffc0525a3a562bc331c6ca316644920a906ca20e"
    val getBytes: ByteArray = (ts + key + apikey).toByteArray()
    val generator = MessageDigest.getInstance("MD5")
    return BigInteger(1, generator.digest(getBytes)).toString(16).padStart(32, '0')
}