package com.tlcsdm.text;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cn.hutool.core.text.StrMatcher;

public class StrMatcherTest {

	@Test
	public void matcherTest() {
		final StrMatcher strMatcher = new StrMatcher(
				"${name}-${age}-${gender}-${country}-${province}-${city}-${status}");
		final Map<String, String> match = strMatcher.match("小明-19-男-中国-河南-郑州-已婚");
		Assert.assertEquals("小明", match.get("name"));
		Assert.assertEquals("19", match.get("age"));
		Assert.assertEquals("男", match.get("gender"));
		Assert.assertEquals("中国", match.get("country"));
		Assert.assertEquals("河南", match.get("province"));
		Assert.assertEquals("郑州", match.get("city"));
		Assert.assertEquals("已婚", match.get("status"));
	}

	@Test
	public void matcherTest2() {
		// 当有无匹配项的时候，按照全不匹配对待
		final StrMatcher strMatcher = new StrMatcher(
				"${name}-${age}-${gender}-${country}-${province}-${city}-${status}");
		final Map<String, String> match = strMatcher.match("小明-19-男-中国-河南-郑州");
		Assert.assertEquals(0, match.size());
	}

	@Test
	public void matcherTest3() {
		// 当有无匹配项的时候，按照全不匹配对待
		final StrMatcher strMatcher = new StrMatcher("${name}经过${year}年");
		final Map<String, String> match = strMatcher.match("小明经过20年，成长为一个大人。");
		// Console.log(match);
		Assert.assertEquals("小明", match.get("name"));
		Assert.assertEquals("20", match.get("year"));
	}

	@Test
	public void optionalTest() {
		Map<String, Set<Integer>> map = Map.of("1", Set.of(1, 2, 3), "3", Set.of(5, 6, 7));
		System.out.println(Optional.ofNullable(map).map((s) -> s.get("2")).map((n) -> n.contains(4)).orElse(false));
	}
}
