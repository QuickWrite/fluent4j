package net.quickwrite.fluent4j.ast;

import java.util.ArrayList;
import java.util.List;

public class FluentAttribute extends FluentElement {
    protected final String identifier;

    protected final List<FluentElement> fluentElements;
    protected final List<String> content;

    protected int index = 0;

    public FluentAttribute(String identifier, List<String> content) {
        this.identifier = identifier;

        this.content = content;

        this.fluentElements = parse();
    }

    private List<FluentElement> parse() {
        int whitespace = countWhitespace();

        List<FluentElement> elements = new ArrayList<>();

        return elements;
    }

    private int countWhitespace() {
        if (content.size() == 0)
            return 0;

        if (content.size() == 1) {
            int count = 0;

            for(int i = 0; content.get(0).length() > i; i++) {
                if (content.get(0).charAt(i) != ' ') {
                    break;
                }

                count++;
            }

            return count;
        }

        int maxCount = Integer.MAX_VALUE;

        for (int i = 1; content.size() >= i; i++) {
            int innerCount = 0;

            for(int j = 0; content.get(i).length() >= j; j++) {
                if (content.get(i).charAt(j) != ' ') {
                    break;
                }

                innerCount++;
            }
            if (innerCount < maxCount) {
                maxCount = innerCount;
            }
        }

        return maxCount;
    }

    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t}";
    }
}
