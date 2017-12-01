package com.simplesys.common

object Numbers {
    def truncateAt(n: Double, p: Int): Double = {
        val s = math pow(10, p);
        (math floor n * s) / s
    }
}
