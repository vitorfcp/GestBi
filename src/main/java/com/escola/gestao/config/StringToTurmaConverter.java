package com.escola.gestao.config;

import com.escola.gestao.model.Turma;
import com.escola.gestao.repository.TurmaRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTurmaConverter implements Converter<String, Turma> {

    private final TurmaRepository turmaRepository;

    public StringToTurmaConverter(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @Override
    public Turma convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            Long id = Long.parseLong(source);
            return turmaRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
