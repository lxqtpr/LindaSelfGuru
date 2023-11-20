package dev.lxqtpr.lindaSelfGuru.Domain.Libraries.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseLibraryDto implements Serializable {

    private Long id;

    private String title;

    private String subtitle;

    private String avatar;
}
