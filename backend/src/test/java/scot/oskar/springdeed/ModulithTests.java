package scot.oskar.springdeed;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithTests {

    ApplicationModules modules = ApplicationModules.of(SpringdeedApplication.class);

    @Test
    void verifyStructure() {
        modules.verify();
    }

    @Test
    void writeDocumentation() {
        new Documenter(modules).writeDocumentation();
    }
}
