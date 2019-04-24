import java.io.File
import java.util.regex.Pattern

object LangSetKeyExtractor {
    private val p1 =
            Pattern.compile(Pattern.quote("langset.get(\"") + "(.*?)" + Pattern.quote("\")"), Pattern.CASE_INSENSITIVE)
    private val p2 = Pattern.compile(
            Pattern.quote("langset.getinstance().get(\"") + "(.*?)" + Pattern.quote("\")"),
            Pattern.CASE_INSENSITIVE
    )
    private val p3 =
            Pattern.compile(Pattern.quote("\"") + "(.*?)" + Pattern.quote("\".i18n()"), Pattern.CASE_INSENSITIVE)

    fun extract() {
        val result = mutableSetOf<String>()

        File("/Users/gom/Android/flitto_android/flitto-android/src").walk()
                .filter { it.isFile && (it.extension == "java" || it.extension == "kt") }
                .map { it.readText() }
                .forEach { str ->
                    listOf(p1, p2, p3).forEach { pattern ->
                        pattern.matcher(str)?.let {
                            while (it.find()) {
                                result.add(it.group(1))
                            }
                        }
                    }
                }

        result.forEach(System.out::println)
    }
}

fun main(args: Array<String>) {

}