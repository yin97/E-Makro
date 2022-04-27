package uz.dsavdo.emakro.utills

class EAN13CodeBuilder(private val codeStringValue: String) {
    var code: String? = null
        private set
    private val fullCode: String
        private get() {
            var chetVal = 0
            var nechetVal = 0
            var codeToParse = codeStringValue
            for (index in 0..5) {
                chetVal += Integer.valueOf(
                    codeToParse.substring(index * 2 + 1, index * 2 + 2)
                )
                    .toInt()
                nechetVal += Integer.valueOf(
                    codeToParse.substring(index * 2, index * 2 + 1)
                ).toInt()
            }
            chetVal *= 3
            var controlNumber = 10 - (chetVal + nechetVal) % 10
            if (controlNumber == 10) controlNumber = 0
            codeToParse += controlNumber.toString()
            return codeToParse
        }

    private fun DigitToUpperCase(digit: String): String {
        val letters = "ABCDEFGHIJ"
        val position = Integer.valueOf(digit).toInt()
        return letters.substring(position, position + 1)
    }

    private fun DigitToLowerCase(digit: String): String {
        val letters = "abcdefghij"
        val position = Integer.valueOf(digit).toInt()
        return letters.substring(position, position + 1)
    }

    private fun createEAN13Code(rawCode: String): String {
        val firstFlag = Integer.valueOf(
            rawCode.substring(0, 1)
        ).toInt()
        val leftString = rawCode.substring(1, 7)
        val rightString = rawCode.substring(7)
        var rightCode = ""
        var leftCode = ""
        for (i in 0..5) {
            rightCode += DigitToLowerCase(rightString.substring(i, i + 1))
        }
        if (firstFlag == 0) {
            leftCode = ("#!" + leftString.substring(0, 1)
                    + leftString.substring(1, 2) + leftString.substring(2, 3)
                    + leftString.substring(3, 4) + leftString.substring(4, 5)
                    + leftString.substring(5))
        }
        if (firstFlag == 1) {
            // /System.out.println("leftString: "+leftString);
            leftCode = ("$!" + leftString.substring(0, 1)
                    + leftString.substring(1, 2)
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + leftString.substring(3, 4)
                    + DigitToUpperCase(leftString.substring(4, 5))
                    + DigitToUpperCase(leftString.substring(5)))
        }
        if (firstFlag == 2) {
            leftCode = ("%!" + leftString.substring(0, 1)
                    + leftString.substring(1, 2)
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + DigitToUpperCase(leftString.substring(3, 4))
                    + leftString.substring(4, 5)
                    + DigitToUpperCase(leftString.substring(5)))
        }
        if (firstFlag == 3) {
            leftCode = ("&!" + leftString.substring(0, 1)
                    + leftString.substring(1, 2)
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + DigitToUpperCase(leftString.substring(3, 4))
                    + DigitToUpperCase(leftString.substring(4, 5))
                    + leftString.substring(5))
        }
        if (firstFlag == 4) {
            leftCode = ("'!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + leftString.substring(2, 3) + leftString.substring(3, 4)
                    + DigitToUpperCase(leftString.substring(4, 5))
                    + DigitToUpperCase(leftString.substring(5)))
        }
        if (firstFlag == 5) {
            leftCode = ("(!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + leftString.substring(3, 4) + leftString.substring(4, 5)
                    + DigitToUpperCase(leftString.substring(5)))
        }
        if (firstFlag == 6) {
            leftCode = (")!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + DigitToUpperCase(leftString.substring(3, 4))
                    + leftString.substring(4, 5) + leftString.substring(5))
        }
        if (firstFlag == 7) {
            leftCode = ("*!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + leftString.substring(2, 3)
                    + DigitToUpperCase(leftString.substring(3, 4))
                    + leftString.substring(4, 5)
                    + DigitToUpperCase(leftString.substring(5)))
        }
        if (firstFlag == 8) {
            leftCode = ("+!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + leftString.substring(2, 3)
                    + DigitToUpperCase(leftString.substring(3, 4))
                    + DigitToUpperCase(leftString.substring(4, 5))
                    + leftString.substring(5))
        }
        if (firstFlag == 9) {
            leftCode = (",!" + leftString.substring(0, 1)
                    + DigitToUpperCase(leftString.substring(1, 2))
                    + DigitToUpperCase(leftString.substring(2, 3))
                    + leftString.substring(3, 4)
                    + DigitToUpperCase(leftString.substring(4, 5))
                    + leftString.substring(5))
        }
        return "$leftCode-$rightCode!"
    }

    private fun parse() {
        val fullString = fullCode
        println("Full code: $fullString")
        this.code = createEAN13Code(fullString)
        println("Generated code: " + this.code)
    }

    init {
        parse()
    }
}