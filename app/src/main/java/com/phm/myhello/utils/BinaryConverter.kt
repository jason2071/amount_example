package com.phm.myhello.utils

object BinaryConverter {

    // convert text to binary
    fun strToBinary(str: String): String {
        val builder = StringBuilder()

        for (c in str.toCharArray()) {
            val toString = Integer.toString(c.toInt(), 2); // get char value in binary
            builder.append(String.format("%08d", Integer.parseInt(toString))); // we complete to have 8 digits
        }

        return builder.toString()
    }

    // convert binary to text
    fun binaryToString(binary: String): String {
        if (!isBinary(binary))
            return "Not a binary value";

        val chars = CharArray(binary.length / 8)
        var i = 0

        while (i < binary.length) {
            val str = binary.substring(i, i + 8)
            val nb = Integer.parseInt(str, 2)
            chars[i / 8] = nb.toChar()
            i += 8
        }

        return String(chars)
    }

    fun isBinary(txt: String?): Boolean {
        if (txt != null && txt.length % 8 == 0) {
            for (c in txt.toCharArray()) {
                if (c != '0' && c != '1')
                    return false
            }
            return true
        }
        return false
    }
}