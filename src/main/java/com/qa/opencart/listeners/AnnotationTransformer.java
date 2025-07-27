package com.qa.opencart.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {
	
	// Naveen has not written this code. He has copy pasted.
	// This is for retry logic. This is not a listener. 
	
	@Override
	public void transform(ITestAnnotation annotation, 
			Class testClass, 
			Constructor testConstructor, 
			Method testMethod) {
		annotation.setRetryAnalyzer(Retry.class);
	}
}