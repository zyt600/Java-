import java.net.URLEncoder;
import java.net.URL;
import java.net.URI;
import java.io.*;
import java.awt.Desktop;
import java.nio.charset.*;

public class Suggestion {
    /**
     * getSuggestion
     * - generate suggestion words from Baidu for the given keyword
     */
    public static String[] getSuggestion(String keyword) {
        try {
            String url = "http://suggestion.baidu.com/su?wd="
                    + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            url += "&rnd=" + Math.random();
            String content = download(new URL(url));
            String[] suggestion = content.replaceAll(".*,s:\\[([^]]*)].*", "$1")
				                        .replaceAll("\"", "").split(",");

            // if there's no suggestion
            if (suggestion[0].equals(""))
                suggestion = new String[0];

            return suggestion;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * searchSuggestion
     * - given a suggestion string, open the browser and search the string.
     */
    public static void searchSuggestion(String suggestion) {
        suggestion = suggestion.replaceAll(" ", "%20");
        Desktop desktop = Desktop.getDesktop(); // open the browser
        try {
            URI uri = new URI("https://www.baidu.com/s?wd=" +
                        URLEncoder.encode(suggestion, StandardCharsets.UTF_8));
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * download
     * - get content from the given url
     * - this method comes from code examples by Mr. Tang
     */
    static String download(URL url)
		throws Exception
	{
		try(InputStream input 
				= url.openStream();
			ByteArrayOutputStream output 
				= new ByteArrayOutputStream())
		{
			byte[] data = new byte[1024];
			int length;
			while((length=input.read(data))!=-1){
				output.write(data,0,length);
			}
            return output.toString(Charset.forName("gb2312"));
		}
	}
}
