import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer
import kotlinx.html.attributesMapOf
import kotlinx.html.visit

@HtmlTagMarker
inline fun FlowContent.path(classes : String? = null, crossinline block : PATH.() -> Unit = {}) : Unit = PATH(
    attributesMapOf("class", classes), consumer).visit(block)

@Suppress("unused")
open class PATH(initialAttributes : Map<String, String>, override val consumer : TagConsumer<*>) : HTMLTag("path", consumer, initialAttributes, null, false, false), HtmlBlockTag {

}
