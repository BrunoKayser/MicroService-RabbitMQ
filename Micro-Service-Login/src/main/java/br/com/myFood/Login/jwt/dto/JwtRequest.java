package br.com.myFood.Login.jwt.dto;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;


}
