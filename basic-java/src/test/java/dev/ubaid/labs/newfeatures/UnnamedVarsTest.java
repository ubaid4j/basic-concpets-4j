package dev.ubaid.labs.newfeatures;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnnamedVarsTest {
    
    @Test
    void testUnnamedVariables() {
        //fixme BUG https://bugs.openjdk.org/browse/JDK-8313323
        //        var _ = "hello";

        //  log.debug(_);
        //  java: as of release 21, the underscore keyword '_' is only allowed to declare
        //  unnamed patterns, local variables, exception parameters or lambda parameters
    }
}
