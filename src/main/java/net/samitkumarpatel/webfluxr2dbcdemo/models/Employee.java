package net.samitkumarpatel.webfluxr2dbcdemo.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Employee(
        @Id Long id,
        String name,
        String address,
        String designation,
        float salary,
        /*@JsonFormat(pattern = "dd-MM-yyyy") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/ LocalDate doj,
        String department) {

}
