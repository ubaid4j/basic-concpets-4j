package com.ubaid.forj;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.junit.jupiter.api.Test;

// ITEM 59: KNOW AND USE THE LIBRARIES 
public class Item59Test {
    
    @Test
    void printContentsOfURLUsingTransferTo() throws IOException {
        String url = "https://catfact.ninja/fact";
        try (InputStream in = new URL(url).openStream()) {
            in.transferTo(System.out);
        }
    }
    
}
