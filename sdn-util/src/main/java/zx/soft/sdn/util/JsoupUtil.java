package zx.soft.sdn.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jsoup工具类
 * 
 * @author xuran
 *
 */
public class JsoupUtil {

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

	/**
	 * 将String转换成Document
	 * @param html HTML字符串
	 * @return 成功返回文档对象，失败返回null
	 */
	public static Document parseHtmlFromString(String html) {
		Document doc = null;
		try {
			doc = Jsoup.parse(html);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception : {}", ExceptionUtil.exceptionToString(e));
		}
		return doc;
	}

	/**
	 * 注意：这是一个不安全的方法
	 * 将String转换成Html片段,注意防止跨站脚本攻击
	 * @param html HTML字符串
	 * @return 元素对象
	 */
	public static Element parseHtmlFragmentFromStringNotSafe(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		return body;
	}

	/**
	 * 这是一个安全的方法
	 * 将String转换成Html片段,注意防止跨站脚本攻击
	 * @param html HTML字符串
	 * @return 元素对象
	 */
	public static Element parseHtmlFragmentFromStringSafe(String html) {
		//白名单列表定义了哪些元素和属性可以通过清洁器，其他的元素和属性一律移除
		Whitelist wl = new Whitelist();
		//比较松散的过滤，包括
		//"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
		//"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
		//"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
		//"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
		//"ul"
		Whitelist.relaxed();
		//没有任何标签，只有文本
		Whitelist.none();
		//常规的过滤器
		//"a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
		//"i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub",
		//"sup", "u", "ul"
		Whitelist.basic();
		//常规的过滤器，多了一个img标签
		Whitelist.basicWithImages();
		//文本类型的标签
		//"b", "em", "i", "strong", "u"
		Whitelist.simpleText();
		//另外还可以自定义过滤规则,例如
		wl.addTags("a");
		//执行过滤
		Jsoup.clean(html, wl);
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		return body;
	}

	/**
	 * 从URL加载
	 * @param url 网页地址
	 * @return 文档对象
	 */
	public static Document parseDocumentFromUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			//data(key,value)是该URL要求的参数
			//userAgent制定用户使用的代理类型
			//cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
			//timeout超时时间
			//doc = Jsoup.connect("http://www.xxxxx.com/").data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 从文件加载
	 * @param filePath 网页文件路径
	 * @return 文档对象
	 */
	public static Document parseDocumentFromFile(String filePath) {
		File input = new File(filePath);
		Document doc = null;
		try {
			//从文件加载Document文档
			doc = Jsoup.parse(input, "UTF-8");
			System.out.println(doc.title());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 选择器操作示例
	 */
	public static void selector() {
		Document doc;
		try {
			//获取文档
			doc = Jsoup.connect("http://xxx.com/").get();
			/*****获取单一元素******/
			//与JS类似的根据ID选择的选择器<div id="content"></div>
			Element content = doc.getElementById("content");
			/*****一下方法的返回值都是Elements集合******/
			//获取所有的a标签<a href="#"></a>
			content.getElementsByTag("a");
			//类选择器<div></div>
			doc.getElementsByClass("divClass");
			//获取Document的所有元素
			doc.getAllElements();
			//根据属性获取元素<a href="#"></a>
			doc.getElementsByAttribute("href");
			//根据属性前缀获取元素 <li data-name="Peter Liu" data-city="ShangHai" data-lang="CSharp" data-food="apple">
			doc.getElementsByAttributeStarting("data-");
			//根据key-value选择如<a href="http://xdemo.org"></a>
			doc.getElementsByAttributeValue("href", "http://xdemo.org");
			//和上面的正好相反
			doc.getElementsByAttributeValueNot("href", "http://xdemo.org");
			//根据key-value,其中value可能是key对应属性的一个子字符串，选择如<a href="http://xdemo.org"></a>
			doc.getElementsByAttributeValueContaining("href", "xdemo");
			//根据key-value,其中key对应值的结尾是value，选择如<a href="http://xdemo.org"></a>
			doc.getElementsByAttributeValueEnding("href", "org");
			//和上面的正好相反
			doc.getElementsByAttributeValueStarting("href", "http://xdemo");
			//正则匹配，value需要满足正则表达式，<a href="http://xdemo.org"></a>,如href的值含有汉字
			doc.getElementsByAttributeValueMatching("href", Pattern.compile("[\u4e00-\u9fa5]"));
			//同上
			doc.getElementsByAttributeValueMatching("href", "[\u4e00-\u9fa5]");
			//根据元素所在的z-index获取元素
			doc.getElementsByIndexEquals(0);
			//获取z-index大于x的元素
			doc.getElementsByIndexGreaterThan(0);
			//和上面的正好相反
			doc.getElementsByIndexLessThan(10);
			//遍历标签
			//			for (Element link : content.getElementsByTag("a")) {
			//				String linkHref = link.attr("href");
			//				String linkText = link.text();
			//			}
			/**************一些其他常用的方法**************/
			//获取网页标题
			doc.title();
			//获取页面的所有文本
			doc.text();
			//为元素添加一个css class
			content.addClass("newClass");
			//根据属性获取值
			content.attr("id");
			//获取所有子元素
			content.children();
			//获取元素内的所有文本
			content.text();
			//获取同级元素
			content.siblingElements();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Document document = parseDocumentFromUrl("http://www.baidu.com");
		System.out.println(document.title());
		System.out.println(document.text());
	}

}
