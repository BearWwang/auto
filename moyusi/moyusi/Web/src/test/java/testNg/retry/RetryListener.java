package testNg.retry;

import java.lang.reflect.Constructor; 
import java.lang.reflect.Method; 
import org.apache.tools.ant.taskdefs.Retry; 
import org.testng.IAnnotationTransformer; 
import org.testng.IRetryAnalyzer; 
import org.testng.annotations.ITestAnnotation; 
    public class RetryListener implements IAnnotationTransformer { 
        @Override 
        public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass,@SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) {
            IRetryAnalyzer retry = annotation.getRetryAnalyzer();
            if (retry == null) {
                annotation.setRetryAnalyzer(OverrideIReTry.class);
            }
        }

}
