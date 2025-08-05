package com.mycompany.ProjetoNutricaoWeb.service;

import com.mycompany.ProjetoNutricaoWeb.data.TmbRepository;
import com.mycompany.ProjetoNutricaoWeb.data.UsuarioRepository;
import com.mycompany.ProjetoNutricaoWeb.model.TmbEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TmbRepository tmbRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*public UsuarioEntity criarUsuario(UsuarioEntity usuario) {
        usuario.setId(null);

        // Criptografar a senha com BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
        return usuario;
    }*/

    public UsuarioEntity criarUsuario(UsuarioEntity usuario) {
        // Verifica se já existe um usuário com o mesmo e-mail
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já existe.");
        }
        usuario.setId(null);

        // Criptografar a senha com BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }


    public UsuarioEntity validarLogin(String email, String senha) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    public boolean atualizarSenhaPorEmail(String email, String novaSenha) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String novaSenhaHash = encoder.encode(novaSenha);
            usuario.setSenha(novaSenhaHash);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public TmbEntity salvarTmb(TmbEntity tmb) {
        return tmbRepository.save(tmb);
    }
}
