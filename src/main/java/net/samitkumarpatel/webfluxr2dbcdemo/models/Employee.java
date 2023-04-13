package net.samitkumarpatel.webfluxr2dbcdemo.models;

import lombok.Builder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Employee(
        @Id Long id,
        String name,
        String address,
        String designation,
        float salary,
        /*@JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
        LocalDate doj,
        String department,

        @CreatedBy
        @Column("created_by")
        String createdBy,
        @LastModifiedBy
        @Column("updated_by")
        String updatedBy,
        @CreatedDate
        @Column("created_at")
        LocalDate createdAt,
        @LastModifiedDate
        @Column("updated_at")
        LocalDate updatedAt) {
        @Builder
        public Employee {}

}
