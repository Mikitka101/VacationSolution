package com.yasiulevichnikita.VacationSolution.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "size")
    private Long size = 0L;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "bytes", columnDefinition = "longblob")
    private byte[] bytes;

    @OneToOne(mappedBy = "avatar")
    private User user;


}
