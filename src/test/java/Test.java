import cn.sxy.ResponsibilityFeedbackChain.IChainMember;
import cn.sxy.ResponsibilityFeedbackChain.ResponsibilityFeedbackChain;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private static final Map<Integer, Integer> cache0 = Collections.synchronizedMap(new HashMap<>());
    private static final Map<Integer, Integer> cache1 = Collections.synchronizedMap(new HashMap<Integer, Integer>() {{
        put(1, 100);
        put(2, 200);
    }});
    private static final Map<Integer, Integer> cache2 = Collections.synchronizedMap(new HashMap<Integer, Integer>() {{
        put(-1, -100);
        put(-2, -200);
    }});

    public static void main(String[] args) throws SQLException {
        ResponsibilityFeedbackChain<Integer, Integer> chain = new ResponsibilityFeedbackChain<>();
        chain.addMember(new IChainMember<Integer, Integer>() {
            @Override
            public Integer handle(Integer param) {
                return cache0.get(param);
            }

            @Override
            public void feedback(Integer param, Integer result) {
                cache0.put(param, result);
            }
        });
        chain.addMember(new IChainMember<Integer, Integer>() {
            @Override
            public Integer handle(Integer param) {
                return cache1.get(param);
            }

            @Override
            public void feedback(Integer param, Integer result) {
                cache1.put(param, result);
            }
        });
        chain.addMember(new IChainMember<Integer, Integer>() {
            @Override
            public Integer handle(Integer param) {
                return cache2.get(param);
            }

            @Override
            public void feedback(Integer param, Integer result) {
                cache2.put(param, result);
            }
        });

        assert chain.runChain(0, true) == null;
        assert chain.runChain(1, true) == 100;
        assert chain.runChain(2, true) == 200;
        assert chain.runChain(-1, true) == -100;
        assert chain.runChain(-2, true) == -200;

        assert cache0.size() == 4 : cache0.size();
        assert cache1.size() == 4 : cache1.size();
        assert cache2.size() == 2 : cache2.size();

        assert false;
    }

}
