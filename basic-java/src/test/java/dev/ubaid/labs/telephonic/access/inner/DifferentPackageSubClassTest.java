package dev.ubaid.labs.telephonic.access.inner;

import dev.ubaid.labs.telephonic.access.AccessTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DifferentPackageSubClassTest extends AccessTest {
    static {
        log.debug(AccessTest.publicField);
        log.debug(AccessTest.protectedField);
    }
}
