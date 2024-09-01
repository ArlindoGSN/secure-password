package dev.arlindo.securepassword.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class PasswordService {
    // Mapeamento de códigos de erro para funções de verificação de senha
    private final Map<Integer, Function<String, Integer>> verificationStrategies = Map.of(
            1, this::verifyPasswordCount,
            2, this::verifyPasswordUppercase,
            3, this::verifyPasswordLowercase,
            4, this::verifyPasswordDigit,
            5, this::verifyPasswordSpecial
    );

    // Mapeamento de códigos de erro para mensagens de erro
    private final Map<Integer, String> errorMessages = Map.of(
            1, "Password must have at least 8 characters",
            2, "Password must have at least one uppercase letter",
            3, "Password must have at least one lowercase letter",
            4, "Password must have at least one digit",
            5, "Password must have at least one special character"
    );

    public int verifyPasswordCount(String password) {
        return password.length() < 8 ? 1 : 0;
    }

    public int verifyPasswordUppercase(String password) {
        return password.matches(".*[A-Z].*") ? 0 : 2;
    }

    public int verifyPasswordLowercase(String password) {
        return password.matches(".*[a-z].*") ? 0 : 3;
    }

    public int verifyPasswordDigit(String password) {
        return password.matches(".*[0-9].*") ? 0 : 4;
    }

    public int verifyPasswordSpecial(String password) {
        return password.matches(".*[^a-zA-Z0-9].*") ? 0 : 5;
    }

    // Verifica a senha utilizando os Map e retorna todas as mensagens de erro
    public String verifyPassword(String password) {
        List<String> listerro = new ArrayList<>();
        for (Map.Entry<Integer, Function<String, Integer>> entry : verificationStrategies.entrySet()) {
            int errorCode = entry.getValue().apply(password);
            if (errorCode != 0) {
                listerro.add(errorMessages.get(errorCode));
            }
        }

        // Retorna todas as mensagens de erro ou "Password is valid" se não houver erros
        if (listerro.isEmpty()) {
            return "Password is valid";
        } else {
            return String.join(", ", listerro);
        }
    }
}
