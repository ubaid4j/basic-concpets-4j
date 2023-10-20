package dev.ubaid.labs.telephonic.access.inner;

import dev.ubaid.labs.telephonic.access.AccessTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DifferentPackageClass {
    static {
        log.debug("only access to public field in a class having different package and not inheriting");
        log.debug(AccessTest.publicField);
    }
}
