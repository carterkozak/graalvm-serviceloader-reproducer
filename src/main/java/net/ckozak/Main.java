package net.ckozak;

import java.util.Set;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public final class Main {

    private static final Set<String> EXPECTED = Set.of("ExampleConcreteImplementation", "ExampleAbstractImplementation");

    public static void main(String[] args) {
        Set<String> simpleNames = ServiceLoader.load(ExampleInterface.class).stream()
                .map(provider -> provider.type().getSimpleName())
                .collect(Collectors.toSet());
        if (EXPECTED.equals(simpleNames)) {
           System.out.println("success");
        } else {
            throw new IllegalStateException(String.format("Expected '%s' but was '%s'", EXPECTED, simpleNames));
        }
    }
}
