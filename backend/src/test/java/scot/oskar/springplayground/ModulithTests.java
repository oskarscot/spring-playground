package scot.oskar.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithTests {

    ApplicationModules modules = ApplicationModules.of(SpringPlaygroundApplication.class);

    @Test
    void verifyStructure() {
        modules.verify();
    }

    @Test
    void writeDocumentation() {
        new Documenter(modules).writeDocumentation();
    }
}
