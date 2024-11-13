package com.yrgo.advice;
import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice {

    public Object performTimingMeasurement (ProceedingJoinPoint method) throws Throwable {

        long startTime = System.nanoTime();

        try {
            return method.proceed();
        } finally {
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            double timeInMilliseconds = (double) timeTaken / 1000000;
            String methodName = method.getSignature().getName();
            String className = method.getTarget().getClass().getName();
            System.out.println("Time taken for the method " + methodName + " from the class\n" + className + " took " +
                    timeInMilliseconds + "ms\n");
        }
    }
}