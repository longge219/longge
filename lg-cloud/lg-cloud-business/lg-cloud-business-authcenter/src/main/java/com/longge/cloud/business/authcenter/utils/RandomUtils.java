package com.longge.cloud.business.authcenter.utils;
import org.apache.commons.lang.RandomStringUtils;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
/**
 * @author: jianglong
 * @description: 随机数生成
 * @date: 2019-01-25
 * */
public class RandomUtils {

	/** 获取强随机字符串，size为随机字符串长度，偶数*/
	public final static String strongRandom() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**获取9位随机数字*/
	public final static int randomNum() {
		Random random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return random.nextInt(999999999) % (999999999 - 100000000 + 1) + 100000000;
	}

	private static char[] LETTERANDNUM = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static char[] ALLLETTERANDNUM = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' };
	
	private static char[] ALLLETTER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	private static char[] NUM = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static char[] LETTER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static String getRandomLetter(int size) {
		return RandomStringUtils.random(size, LETTER);
	}
	
	public static String getRandomAllLetter(int size) {
		return RandomStringUtils.random(size, ALLLETTER);
	}

	public static String getRandomNum(int size) {
		return RandomStringUtils.random(size, NUM);
	}

	public static String getRandomLetterAndNum(int size) {
		return RandomStringUtils.random(size, LETTERANDNUM);
	}

	public static String getRandomAllLetterAndNum(int size) {
		return RandomStringUtils.random(size, ALLLETTERANDNUM);
	}
}
