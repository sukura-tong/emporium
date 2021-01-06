package com.swust.emporium.dto;

import lombok.*;

import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageHolder {
    private String imageName;
    private InputStream image;
}
