package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.exception.FluentParseException;
import org.apache.commons.text.translate.CharSequenceTranslator;

import java.io.IOException;
import java.io.Writer;

/**
 * Translates escaped Unicode values of the form \\u\d\d\d\d or
 * \\U\d\d\d\d\d\d back to Unicode.
 */
public class FluentUnicodeTranslator extends CharSequenceTranslator {
    // Code changed from https://bit.ly/3xkQEpq.

    @Override
    public int translate(final CharSequence input, final int index, final Writer writer) throws IOException {
        if (!(input.charAt(index) == '\\' && index + 1 < input.length())) {
            return 0;
        }

        boolean bigUnicode = true;
        if (input.charAt(index + 1) == 'u')
            bigUnicode = false;
        if (bigUnicode && input.charAt(index + 1) != 'U')
            return 0;

        int i = 2;

        if (index + i + (bigUnicode ? 6 : 4) <= input.length()) {
            // Get 4 or 6 hex digits
            final CharSequence unicode = input.subSequence(index + i, index + i + (bigUnicode ? 6 : 4));

            try {
                final int value = Integer.parseInt(unicode.toString(), 16);
                writer.write(Character.toChars(value));
            } catch (final NumberFormatException nfe) {
                throw new FluentParseException("Unable to parse unicode value: " + unicode, nfe);
            }
            return i + (bigUnicode ? 6 : 4);
        }
        throw new FluentParseException(
                "Less than " + (bigUnicode ? 6 : 4) + " hex digits in unicode value: '"
                        + input.subSequence(index, input.length())
                        + "' due to end of CharSequence");
    }
}
