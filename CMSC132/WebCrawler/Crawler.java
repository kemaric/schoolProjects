import java.net.*;
import java.io.*;

public class Crawler {

	public static void main(String[] args) {

		MyQueue<URL> linkQueue = new MyQueue<URL>();
		MyQueue<URL> picQueue = new MyQueue<URL>();
		MySet<URL> beenThere = new MySet<URL>();
		MySet<URL> doneThat = new MySet<URL>();

		final int MAX_NUM_EXTRACTORS = 5;  // Change this to whatever you want

		ExtractorThread[] extractors = new ExtractorThread[MAX_NUM_EXTRACTORS];

		new SlideShowGUI(picQueue);
		new CrawlerGUI(linkQueue, picQueue, beenThere, doneThat, extractors);



		while(true) {
			for(int index = 0; index < extractors.length; index++){
				if(extractors[index] == null || !extractors[index].isAlive()){
					synchronized(linkQueue){
						if(linkQueue.size() == 0){
							try {
								linkQueue.wait();
							} catch (InterruptedException e) {
							}
						}
					}
				}
				synchronized(extractors){
					URL u = (URL) linkQueue.dequeue();
					URLConnection urlco;
					try {
						urlco = u.openConnection();
						if(urlco.getContentType() == null || !urlco.getContentType().startsWith("text/html")){
							u = (URL) linkQueue.dequeue();
						}else{
							ExtractorThread thread = new ExtractorThread(u, linkQueue,picQueue, beenThere, doneThat);
							thread.start();
						}
					} catch (IOException e) {
					}

				}
			}

		}
	}

}

