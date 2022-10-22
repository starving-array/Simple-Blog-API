package com.BlogApp;

import java.util.HashSet;
import java.util.Set;

public class test {

	
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("h");
		System.out.println(set);
		set.remove("h");
		System.out.println(set);
	}
}
