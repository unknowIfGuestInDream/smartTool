package com.tlcsdm.psdcd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.net.UserPassAuthenticator;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * Girret数据读取
 */
public class GirretTest {

	// cookie GerritAccount
	private final String gerritAccountValue = "aQIiprqRsF8g0Y10wLs8xgHhOmuNZuuKBW";
	// cookie XSRF_TOKEN
	private final String tokenValue = "aQIiprt-AbdNE069k01HfzMWbVQk0bMm.G";
	// girret userName
	private final String userName = "liang.tang.yk";
	// girret password
	private final String password = "Psdcd123..";
	// 请求传参
	private int paramS = 0;
	private int paramN = 25;
	private final String paramO = "81";
	private final String paramQ = "is:closed -is:ignored (-is:wip OR owner:self) (owner:self OR reviewer:self OR assignee:self OR cc:self)";
	// cookie 作用域
	private final String cookieAddrString = "http://172.29.44.217/";

	// 待初始化对象
	// 请求路径
	private String url;
	private HttpClient client;

	@Before
	public void init() throws UnsupportedEncodingException {
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		HttpCookie gerritAccount = new HttpCookie("GerritAccount", gerritAccountValue);
		HttpCookie token = new HttpCookie("XSRF_TOKEN", tokenValue);
		manager.getCookieStore().add(URI.create(cookieAddrString), gerritAccount);
		manager.getCookieStore().add(URI.create(cookieAddrString), token);
		Authenticator authenticator = new UserPassAuthenticator(userName, password.toCharArray());
		client = HttpClient.newBuilder().version(Version.HTTP_1_1).followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofMillis(5000)).authenticator(authenticator).cookieHandler(manager).build();
		url = String.format("http://172.29.44.217/changes/?O=%s&S=%s&n=%s&q=%s", URLEncoder.encode(paramO, "UTF-8"),
				paramS, paramN, URLEncoder.encode(paramQ, "UTF-8"));
	}

	/**
	 * girret 提交信息读取
	 */
	@Test
	public void GirretTest1() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().headers("Content-Type", "application/json",
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.50")
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			String result = response.body();
			if (result.startsWith(")]}'")) {
				result = result.substring(4);
			}
			JSONArray array = JSONUtil.parseArray(result);
			System.out.println(array.size());
		} else {
			Console.error("http://172.29.44.217/changes request call failed", response.body());
		}

		// http://172.29.44.217/changes/e2-studio-eclipse%2Ftools-smartconfigurator~43292/comments
	}

}
