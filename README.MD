# Практическое задание - Spring security - JWT
Реализуйте приложение с авторизацией через JWT. Ниже приведена часть кода - допишите его.

```java
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.11.5</version>
</dependency>
```

```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);

        if (token != null && validateToken(token)) {
            Authentication authentication = createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Логика извлечения токена из запроса (например, из заголовка Authorization)
    }

    private boolean validateToken(String token) {
        // Логика верификации токена
    }

    private Authentication createAuthentication(String token) {
        // Логика создания объекта Authentication на основе токена
    }
}
```
```java
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Логика аутентификации с использованием токена
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(new JwtAuthenticationProvider())
        .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .logoutUrl("/logout")
            .permitAll();
}
```
```java
public class JwtUtil {

    public static String generateToken(UserDetails userDetails) {
        // Логика генерации JWT
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        // Логика проверки JWT
    }
}
```
```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // ...

    private Authentication createAuthentication(String token) {
        // Получение информации о пользователе из токена
        UserDetails userDetails = extractUserDetailsFromToken(token);

        // Создание объекта Authentication
        return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private UserDetails extractUserDetailsFromToken(String token) {
        // Логика извлечения информации о пользователе из токена
    }
}
```

## Технологический стек:
- Java 17
- Spring Boot
- Spring Security 6.3.1 + JWT 0.11.5
- Spring Data Jpa + H2
- Spring Web