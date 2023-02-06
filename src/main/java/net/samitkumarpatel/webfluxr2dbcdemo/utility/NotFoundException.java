package net.samitkumarpatel.webfluxr2dbcdemo.utility;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
}
