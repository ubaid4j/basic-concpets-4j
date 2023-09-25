package dev.ubaid.labs.newfeatures;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnnamedVarsTest {
    
    @Test
    void testUnnamedVariables() {
        var _ = "hello";

        //  log.debug(_);
        //  java: as of release 21, the underscore keyword '_' is only allowed to declare
        //  unnamed patterns, local variables, exception parameters or lambda parameters
    }
}
