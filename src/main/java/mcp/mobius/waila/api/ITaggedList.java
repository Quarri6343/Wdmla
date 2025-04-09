package mcp.mobius.waila.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This class is the actual tooltip object in Legacy Waila.
 * It is directly used by some Waila addons like Steve's cart.
 * @deprecated Modern counterpart: {@link com.gtnewhorizons.wdmla.impl.ui.component.RootComponent}
 *
 * @param <E> usually String
 * @param <T> Tag object Type (String)
 */
@BackwardCompatibility
@SuppressWarnings("unused")
@Deprecated
public interface ITaggedList<E, T> extends List<E> {

    boolean add(E e, T tag);

    boolean add(E e, Collection<? extends T> taglst);

    Set<T> getTags(E e);

    Set<T> getTags(int index);

    void addTag(E e, T tag);

    void addTag(int index, T tag);

    void removeTag(E e, T tag);

    void removeTag(int index, T tag);

    Set<E> getEntries(T tag);

    void removeEntries(T tag);

    String getTagsAsString(E e);
}
