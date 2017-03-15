ResponsibilityFeedbackChain
============================
### Introduction
This is a tiny framework for acquiring a result from a Responsibility Chain.  
After acquiring the result without errors, it can feed the result back to previous Chain Members.

### Internal Logic 
![image](https://raw.githubusercontent.com/nuo9/ResponsibilityFeedbackChain.go/master/docs/rfchain.gif)

### Installation
    // just copy my code to your project!
    
### Usage
##### For more details, see [Test.java](https://github.com/nuo9/ResponsibilityFeedbackChain.java/blob/master/src/test/java/Test.java)
    // create a chain
    ResponsibilityFeedbackChain<Integer, Integer> chain = new ResponsibilityFeedbackChain<>();
    // create members and add members to chain
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
    // acquiring a result
    result = chain.runChain(0, true);
    
### Golang Version
[ResponsibilityFeedbackChain.go](https://github.com/nuo9/ResponsibilityFeedbackChain.go "daye come to play!")   
    

### MD Reference
    https://github.com/docker/docker/blob/master/README.md
