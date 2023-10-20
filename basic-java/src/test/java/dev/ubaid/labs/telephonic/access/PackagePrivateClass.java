package dev.ubaid.labs.telephonic.access;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackagePrivateClass {
    static {
      log.debug(AccessTest.packagePrivateField);  
      log.debug(AccessTest.protectedField);  
    }
}
