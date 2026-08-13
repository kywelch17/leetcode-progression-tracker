package com.kylewelch.leetcode_progression_tracker.helper;

import org.modelmapper.ModelMapper;

public class MapperUtil {
    private final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = new ModelMapper();

    }

    public <T> T mapToDto (Object src, Class<T> targetClass) {
        return modelMapper.map(src, targetClass);
    }
}
