package mcp.mobius.waila.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a dummy annotation.<br>
 * If you found an unused or redundant class marked with this annotation, do not remove it.<br>
 * It is probably required by some Waila addons.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BackwardCompatibility {}
