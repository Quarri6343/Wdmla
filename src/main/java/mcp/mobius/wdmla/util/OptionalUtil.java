package mcp.mobius.wdmla.util;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Add optional methods in Java9 or above to avoid compile Error
 */
public class OptionalUtil {

    // since java9
    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<? super T> action, Runnable emptyAction) {
        if (optional.isPresent()) {
            action.accept(optional.get());
        } else {
            emptyAction.run();
        }
    }

    // since java11
    public static <T> boolean isEmpty(Optional<T> optional) {
        return !optional.isPresent();
    }
}
