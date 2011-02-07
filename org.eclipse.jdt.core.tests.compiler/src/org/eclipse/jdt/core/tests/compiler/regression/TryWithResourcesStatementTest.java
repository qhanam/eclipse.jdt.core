/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core.tests.compiler.regression;

import junit.framework.Test;
public class TryWithResourcesStatementTest extends AbstractRegressionTest {

static {
//	TESTS_NAMES = new String[] { "test000" };
//	TESTS_NUMBERS = new int[] { 40, 41, 43, 45, 63, 64 };
//	TESTS_RANGE = new int[] { 11, -1 };
}
public TryWithResourcesStatementTest(String name) {
	super(name);
}
public static Test suite() {
	return buildMinimalComplianceTestSuite(testClass(), F_1_7);
}
public void test001() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public void method1(){\n" +
			"		try (int i = 0) {\n" +
			"			System.out.println();\n" +
			"		}\n" +
			"	}\n" +
			"}\n",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 3)\n" + 
		"	try (int i = 0) {\n" + 
		"	     ^^^\n" + 
		"The resource type int has to be a subclass of java.lang.AutoCloseable \n" + 
		"----------\n");
}
public void test002() {
	this.runNegativeTest(
			new String[] {
				"X.java",
				"public class X {\n" +
				"	public void method1(){\n" +
				"		try (int[] tab = {}) {\n" +
				"			System.out.println();\n" +
				"		}\n" +
				"	}\n" +
				"}\n",
			},
			"----------\n" + 
			"1. ERROR in X.java (at line 3)\n" + 
			"	try (int[] tab = {}) {\n" + 
			"	     ^^^^^\n" + 
			"The resource type int[] has to be a subclass of java.lang.AutoCloseable \n" + 
			"----------\n");
}
public void test003() {
	this.runNegativeTest(
			new String[] {
				"X.java",
				"import java.io.*;\n" + 
				"public class X {\n" + 
				"	public static void main(String[] args) throws IOException {\n" + 
				"		int i = 0;\n" + 
				"		try (LineNumberReader reader = new LineNumberReader(new BufferedReader(new FileReader(args[0])))) {\n" + 
				"			String s;\n" + 
				"			int i = 0;\n" + 
				"			while ((s = reader.readLine()) != null) {\n" + 
				"				System.out.println(s);\n" + 
				"				i++;\n" + 
				"			}\n" + 
				"			System.out.println(\"\" + i + \" lines\");\n" + 
				"		}\n" + 
				"	}\n" + 
				"}",
			},
			"----------\n" + 
			"1. ERROR in X.java (at line 7)\n" + 
			"	int i = 0;\n" + 
			"	    ^\n" + 
			"Duplicate local variable i\n" + 
			"----------\n");
}
public void test004() {
	this.runNegativeTest(
			new String[] {
				"X.java",
				"import java.io.*;\n" + 
				"public class X {\n" + 
				"	public static void main(String[] args) throws IOException {\n" + 
				"		try (LineNumberReader r = new LineNumberReader(new BufferedReader(new FileReader(args[0])))) {\n" + 
				"			String s;\n" + 
				"			int r = 0;\n" + 
				"			while ((s = r.readLine()) != null) {\n" + 
				"				System.out.println(s);\n" + 
				"				r++;\n" + 
				"			}\n" + 
				"			System.out.println(\"\" + r + \" lines\");\n" + 
				"		}\n" + 
				"	}\n" + 
				"}",
			},
			"----------\n" + 
			"1. ERROR in X.java (at line 6)\n" + 
			"	int r = 0;\n" + 
			"	    ^\n" + 
			"Duplicate local variable r\n" + 
			"----------\n" + 
			"2. ERROR in X.java (at line 7)\n" + 
			"	while ((s = r.readLine()) != null) {\n" + 
			"	            ^^^^^^^^^^^^\n" + 
			"Cannot invoke readLine() on the primitive type int\n" + 
			"----------\n");
}
public static Class testClass() {
	return TryWithResourcesStatementTest.class;
}
}