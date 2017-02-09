package cn.sxy.ResponsibilityFeedbackChain;

public interface IChainMember<P, R> {

    // return a not-null value when handle done
    // return null when handle failed
    R handle(P param);

    default void handleExceptionHandler(P param, int position, Exception e) {
    }

    // feed the result back (not null) when handle done
    void feedback(P param, R result);

    default void feedbackExceptionHandler(P param, R result, int position, Exception e) {
    }

}
