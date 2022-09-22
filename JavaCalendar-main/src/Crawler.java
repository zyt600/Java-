import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Random;
import java.net.URL;
class Crawler {
	/**
	 * getNews
	 * - get the News today, using Jsoup.
	 * @return a String of the news information
	 */
	public static String[] getNews() {
		try {
			String url = "https://www.thepaper.cn/index.jsp";
			Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

			Elements elements = document.select("[class=swiper-slide]");
			elements = elements.select("[class=swiper-slide]");

			int random = new Random().nextInt(elements.size());
			Element href = elements.get(random);

			String finURL = "https://m.thepaper.cn/";
			finURL += href.select("a").attr("href");

			// parse the document
			document = Jsoup.connect(finURL).get();
			String newsTittle = document.select("[class=title]").text();
			String newsAuthor = document.select("[class=author]").text();
			String newsDate = document.select("[class=date]").text();
			String newsText = document.select("[class=newsdetail_content]").text();

			// generate the result
			String[] finNews = new String[5];
			finNews[0] = newsTittle;
			finNews[1] = newsAuthor;
			finNews[2] = newsDate;
			finNews[3] = newsText;
			finNews[4] = finURL;
			return finNews;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	/**
	 * getWeather
	 * - get the weather today, using Jsoup.
	 * @return a String of the weather information
	 */
	public static String[] getWeather() {
		try {
			String url = "https://beijing.tianqi.com/";
			Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

			// parse the document and generate the result
			Elements elements = document.select("[class=weather]");
			String[] weather = new String[2];
			Elements weatherElements = elements.select("span");
			weather[0] = weatherElements.select("b").get(0).text();
			weather[1] = weatherElements.get(0).text().substring(weather[0].length());

			return weather;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}