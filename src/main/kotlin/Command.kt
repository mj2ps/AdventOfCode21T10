class Command(val opener: Char, val closer: Char, val cost: Int, val autoCompleteCost: ULong) {

    fun getCmd(): String {
        return opener.toString().plus(closer)
    }
    companion object {
        val closers = arrayOf(")", "}", "]", ">")
        const val AUTOCOMPLETE : ULong = 5u
    }

}