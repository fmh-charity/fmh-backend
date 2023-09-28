package ru.iteco.fmh.dto.role;

import lombok.Builder;

@Builder
public record RoleDto(int id, String name) {
}
