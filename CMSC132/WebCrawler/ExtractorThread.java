import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class ExtractorThread extends Thread {

	private URL url;
	private MyQueue<URL> linkQueue, picQueue;
	private MySet<URL> beenThere, doneThat;

	public ExtractorThread(URL url, MyQueue<URL> linkQueue, MyQueue<URL> picQueue, MySet<URL> beenThere, MySet<URL> doneThat) {
		this.url = url;
		this.linkQueue = linkQueue;
		this.picQueue = picQueue;
		this.beenThere = beenThere;
		this.doneThat = doneThat;
	}

	public String getCurrentURL() {
		return url.toString();
	}

	private static Pattern LINK_PATTERN = Pattern.compile("href *= *\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);
	private static Pattern IMAGE_PATTERN = Pattern.compile("<( )*(img|IMG)( )+([^<>])*(src|SRC)( )*=( )*\"([^\"]+)\"[^>]*>");

	private static Set<URL> extractLinks(Pattern toMatch, String s, URL currentURL, int group) {
		Matcher m = toMatch.matcher(s);
		Set<URL> links = new HashSet<URL>();
		while ( m != null && s!= null && m.find()) {
			String found = m.group(group);
			try {
				links.add(new URL(currentURL, found));
			} catch (MalformedURLException e) {
				// just ignore
			}
		}
		return links;
	}

	private static Set<URL> getLinks(String s, URL currentURL) {
		return extractLinks(LINK_PATTERN, s, currentURL, 1);
	}

	private static Set<URL> getPicURLs(String s, URL currentURL) {
		return extractLinks(IMAGE_PATTERN, s, currentURL, 8);
	}

	public void run() {
		BufferedReader reader; 
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = reader.readLine()) != null){
				Set<URL> link  = this.getLinks(inputLine, url);
				for(URL u: link){
					if(u != null && (u.getProtocol().equals("http") || u.getProtocol().equals("file"))){
						if(!beenThere.contains(u)){
							linkQueue.enqueue(u);
							beenThere.add(u);
						}
					}
					//reader.close();
				}
				Set<URL> picLink = this.getPicURLs(inputLine, url);
				for(URL upic: picLink){
					if(!doneThat.contains(upic)){
						doneThat.add(upic);
						picQueue.enqueue(upic);
					}
				}
			} 
		}catch (IOException e) {
		}

	}

}