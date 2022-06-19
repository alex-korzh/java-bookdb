package name.dezalator.bookdb.dto;

import lombok.Data;

@Data
public class UserSignupDto {
    private String email;
    private String password;
    private String repeatedPassword;
}
