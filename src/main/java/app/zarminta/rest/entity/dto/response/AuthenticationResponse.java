package app.zarminta.rest.entity.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private int id;
    private String firstname;
    private String role;
    private String token;
}
