package com.ubaid.forj;

import com.ubaid.forj.Item36Test.Text.Style;
import java.util.EnumSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

// ITEM 36: USE ENUMSET INSTEAD OF BIT FIELDS
public class Item36Test {

    
    static class TextObsolete {
        static final int STYLE_BOLD = 1 << 0; //1
        static final int STYLE_ITALIC = 1 << 1; //2
        static final int STYLE_UNDERLINE = 1 << 2; //4
        static final int STYLE_STRIKETHROUGH = 1 << 3; //8
        
        void applyStyle(int styles) {
            System.out.println("Styles --> " + styles);
        }
    }
    
    @Test
    void testTextObsolete() {
        TextObsolete txt = new TextObsolete();
        txt.applyStyle(TextObsolete.STYLE_BOLD | TextObsolete.STYLE_STRIKETHROUGH);
    }
    
    static class Text {
        enum Style {
            BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
        }
        
        public void applyStyles(Set<Style> styles) {
            System.out.println("Styles ----> " + styles);
        }
    }
    
    @Test
    void testText() {
        Text text = new Text();
        text.applyStyles(EnumSet.of(Style.BOLD, Style.STRIKETHROUGH));
    }
}
