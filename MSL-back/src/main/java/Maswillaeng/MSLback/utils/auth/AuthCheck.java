package Maswillaeng.MSLback.utils.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {
    Role role() default Role.ALL;

    enum Role {
        ALL(0),
        USER(1),
        ADMIN(2);

        private final int level;

        Role(int level) {
            this.level = level;
        }

        // this < role
        public static boolean greaterThan(Role left, String target){
            return false;
        }

    }
}
