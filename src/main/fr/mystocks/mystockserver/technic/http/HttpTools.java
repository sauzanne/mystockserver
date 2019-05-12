package fr.mystocks.mystockserver.technic.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;

public final class HttpTools {

	public final static String COOKIE_HEADER = "cookie";
	public final static String REFERER_HEADER = "Referer";
	public final static String USER_AGENT_HEADER = "User-agent";
	public final static String USER_AGENT_CHROME = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

	/**
	 * Default constructor
	 */
	private HttpTools() {
		super();
	}

	/**
	 * Poste un formulaire sur une URL particulière
	 * 
	 * @param url          l'url à appeler
	 * @param paramToPosts les paramètres à poster
	 * @param newTimeOut   le timeout pour la requête
	 * @return la réponse
	 * @throws IOException
	 */
	public static String postURL(String url, String paramToPosts, Integer... newTimeOut) throws IOException {

		HttpTools httpTools = new HttpTools();

		List<NameValuePair> listNameValuePair = httpTools.createParam(paramToPosts);

		StringBuilder sb = httpTools.apacheHttpPost(url, listNameValuePair);

		return sb.toString();
	}

	/**
	 * @author sauzanne
	 * 
	 * @param url    l'url a appelé
	 * @param cookie le contenu du cookie
	 * @return le résultat de l'appel
	 * @throws IOException
	 */
	public static String getURLWithHeaders(String url, DoubleReturnValue<String, String>[] headers) throws IOException {

		return HttpTools.apacheHttpGetEncapsulation(url, new HttpTools().new HttpResponseRunnable(), headers)
				.toString();

	}

	/**
	 * Poste un formulaire sur une URL particulière
	 * 
	 * @param url          l'url à appeler
	 * @param paramToPosts les paramètres à poster
	 * @param newTimeOut   le timeout pour la requête
	 * @return la réponse
	 * @throws IOException
	 */
	public static String postURL(String url, List<NameValuePair> post) throws IOException {

		HttpTools httpTools = new HttpTools();

		StringBuilder sb = httpTools.apacheHttpPost(url, post);

		return sb.toString();
	}

	/**
	 * Appel de type post http
	 * 
	 * @param url   l'url où l'on veut poster
	 * @param param les paramètres à poster
	 * 
	 * @return le résultat de la requête
	 * @throws IOException
	 * 
	 */
	private StringBuilder apacheHttpPost(String url, List<NameValuePair> param) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(new UrlEncodedFormEntity(param, TechnicalConstant.ENCODING));
		HttpResponse response2 = null;
		response2 = httpClient.execute(httpPost);

		StringBuilder sb = new StringBuilder();

		try {

			HttpEntity entity2 = response2.getEntity();

			InputStream is = entity2.getContent();

			BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, TechnicalConstant.ENCODING));

			String line;

			while ((line = bufReader.readLine()) != null) {
				sb.append(line);
				sb.append(TechnicalConstant.LINE_SEPARATOR);
			}

			if (is != null) {
				is.close();
			}
		} finally {
			httpPost.releaseConnection();
		}
		return sb;
	}

	public interface HttpToolsRunnable {
		StringBuilder myRun(HttpResponse response) throws IOException;
	}

	public final class HttpResponseRunnable implements HttpToolsRunnable {

		@Override
		public StringBuilder myRun(HttpResponse response2) throws IOException {
			StringBuilder sb = new StringBuilder();

			HttpEntity entity2 = response2.getEntity();

			InputStream is = null;

			try {

				is = entity2.getContent();

				BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, TechnicalConstant.ENCODING));

				String line;

				while ((line = bufReader.readLine()) != null) {
					sb.append(line);
					sb.append(TechnicalConstant.LINE_SEPARATOR);
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
			return sb;
		}

	}

	@SafeVarargs
	public static StringBuilder apacheHttpGetEncapsulation(String url, HttpToolsRunnable codeToRun,
			DoubleReturnValue<String, String>... headers) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		try {

			if (headers != null && headers.length > 0) {

				for (DoubleReturnValue<String, String> header : headers) {
					httpGet.addHeader(header.getValue1(), header.getValue2());
				}
			}

			HttpResponse response = httpClient.execute(httpGet);

			return codeToRun.myRun(response);
		} finally {
			httpGet.releaseConnection();
		}

	}

	/**
	 * Créer une liste de paramètre pour un appel http
	 * 
	 * @param paramToPosts le post
	 * @return la liste des paramètres apache
	 */
	private List<NameValuePair> createParam(String paramToPosts) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String loop1 : paramToPosts.split(TechnicalConstant.AMPERSAND_SEPARATOR)) {
			String[] loopIn = loop1.split(TechnicalConstant.EQUALS);
			if (loopIn != null && loopIn.length == 2) {
				nvps.add(new BasicNameValuePair(loopIn[0], loopIn[1]));
			}
		}
		return nvps;
	}

}
