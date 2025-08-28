package MiniJava.scanner.token;

import MiniJava.scanner.type.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for Token-related operations
 * Moved from Token class to separate concerns
 */
public class TokenUtility {
    
    /**
     * Determines the Type of a given string token
     * @param tokenString the string representation of the token
     * @return the corresponding Type enum value
     * @throws IllegalArgumentException if no matching type is found
     */
    public static Type getTypeFromString(String tokenString) {
        // First check for exact matches with Type enum names
        for (Type type : Type.values()) {
            if (type.toString().equals(tokenString)) {
                return type;
            }
        }
        
        // Then check for pattern matches
        for (Type type : Type.values()) {
            if (matchesPattern(tokenString, type.pattern)) {
                return type;
            }
        }
        
        throw new IllegalArgumentException("No matching type found for: " + tokenString);
    }
    
    /**
     * Checks if a string matches a given pattern
     * @param input the input string to check
     * @param pattern the regex pattern to match against
     * @return true if the input matches the pattern
     */
    private static boolean matchesPattern(String input, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);
        return matcher.matches();
    }
    
    /**
     * Validates if a token string represents a valid identifier
     * @param tokenString the string to validate
     * @return true if it's a valid identifier
     */
    public static boolean isValidIdentifier(String tokenString) {
        return matchesPattern(tokenString, Type.ID.pattern);
    }
    
    /**
     * Validates if a token string represents a valid number
     * @param tokenString the string to validate
     * @return true if it's a valid number
     */
    public static boolean isValidNumber(String tokenString) {
        return matchesPattern(tokenString, Type.NUM.pattern);
    }
}
