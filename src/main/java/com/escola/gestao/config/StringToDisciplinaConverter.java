package com.escola.gestao.config;

import com.escola.gestao.model.Disciplina;
import com.escola.gestao.repository.DisciplinaRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDisciplinaConverter implements Converter<String, Disciplina> {

    private final DisciplinaRepository disciplinaRepository;

    public StringToDisciplinaConverter(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public Disciplina convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            Long id = Long.parseLong(source);
            return disciplinaRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
