package com.mycompany.ProjetoNutricaoWeb;

import com.mycompany.ProjetoNutricaoWeb.data.UsuarioRepository;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioEntity criarUsuario(UsuarioEntity usuario) {
        usuario.setId(null);

        // Criptografar a senha com BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public boolean validarLogin(String email, String senhaDigitada) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            // Compara a senha digitada com o hash armazenado
            return encoder.matches(senhaDigitada, usuario.getSenha());
        }
        return false;
    }
}
