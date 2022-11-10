package com.hyvercode.springday.mail.utils;

import lombok.extern.log4j.Log4j2;
import org.hibernate.engine.jdbc.ClobProxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Log4j2
public class ClobUtils {
	
	private ClobUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getAsString(Clob clob) {
		try {
			return new BufferedReader(new InputStreamReader(clob.getAsciiStream())).lines().collect(Collectors.joining());
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}
	
	public static Clob getClob(String s) {
		return ClobProxy.generateProxy(s);
	}
}
