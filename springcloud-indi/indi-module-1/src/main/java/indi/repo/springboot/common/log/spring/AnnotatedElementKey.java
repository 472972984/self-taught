package indi.repo.springboot.common.log.spring;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Represent an {@link AnnotatedElement} on a particular {@link Class}
 * and is suitable as a key.
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @since 4.2
 * @see
 */
public final class AnnotatedElementKey implements Comparable<AnnotatedElementKey> {

    private final AnnotatedElement element;

    private final Class<?> targetClass;


    /**
     * Create a new instance with the specified {@link AnnotatedElement} and
     * optional target {@link Class}.
     */
    public AnnotatedElementKey(AnnotatedElement element, Class<?> targetClass) {
        Assert.notNull(element, "AnnotatedElement must not be null");
        this.element = element;
        this.targetClass = targetClass;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnotatedElementKey)) {
            return false;
        }
        AnnotatedElementKey otherKey = (AnnotatedElementKey) other;
        return (this.element.equals(otherKey.element) &&
                ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass));
    }

    @Override
    public int hashCode() {
        return this.element.hashCode() + (this.targetClass != null ? this.targetClass.hashCode() * 29 : 0);
    }

    @Override
    public String toString() {
        return this.element + (this.targetClass != null ? " on " + this.targetClass : "");
    }

    @Override
    public int compareTo(AnnotatedElementKey other) {
        int result = this.element.toString().compareTo(other.element.toString());
        if (result == 0 && this.targetClass != null) {
            if (other.targetClass == null) {
                return 1;
            }
            result = this.targetClass.getName().compareTo(other.targetClass.getName());
        }
        return result;
    }

}