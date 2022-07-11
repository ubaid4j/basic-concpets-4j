package com.ubaid.forj;

import org.junit.ClassRule;
import org.testcontainers.containers.PostgreSQLContainer;

// TODO: 5/29/22  simple jdbc connection
// TODO: 5/29/22 basic data source using dbcp2
// TODO: 5/29/22 session factory 

public class SessionFactoryTest {

    @ClassRule
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    
}
