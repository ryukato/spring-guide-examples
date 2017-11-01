package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println(new LdapShaPasswordEncoder().encodePassword("test", null));
		SpringApplication.run(DemoApplication.class, args);
	}
}


@RestController
class HomeController {
	@GetMapping("/")
	public String index() {
		return "Welcome to home page";
	}
}

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin();
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
				.userDnPatterns("uid={0},ou=Users")
				.groupSearchBase("ou=Groups")
				.contextSource(contextSource())
				.passwordCompare()
				.passwordEncoder(new LdapShaPasswordEncoder())
				.passwordAttribute("userpassword");
	}

	@Value("${spring.ldap.urls}") private String ldapUrl;
	@Value("${spring.ldap.base}") private String baseDn;

	@Bean
	public DefaultSpringSecurityContextSource contextSource()
	{
		return new DefaultSpringSecurityContextSource(
				Arrays.asList(ldapUrl),
				baseDn
		);
	}
}


class Person {

}
