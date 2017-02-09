package cn.sxy.ResponsibilityFeedbackChain;

import java.util.ArrayList;
import java.util.List;

public class ResponsibilityFeedbackChain<P, R> {

    private List<IChainMember<P, R>> memberList = new ArrayList<>(8);

    public int addMember(IChainMember<P, R> member) {
        if (member == null) throw new NullPointerException();

        memberList.add(member);
        return memberList.size();
    }

    public R runChain(P param, boolean feedback) {
        R result = null;

        int i = 0;
        for (; i < memberList.size(); i++) {
            final IChainMember<P, R> member = memberList.get(i);

            R r = null;
            try {
                r = member.handle(param);
            } catch (Exception e) {
                member.handleExceptionHandler(param, i, e);
            }

            if (r != null) {
                result = r;
                break;
            }
        }

        if (result == null) {
            return null;
        }

        for (int j = i - 1; feedback && j >= 0; j--) {
            final IChainMember<P, R> member = memberList.get(j);

            try {
                member.feedback(param, result);
            } catch (Exception e) {
                member.feedbackExceptionHandler(param, result, j, e);
            }
        }

        return result;
    }

}
