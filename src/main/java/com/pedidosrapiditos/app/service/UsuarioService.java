package com.pedidosrapiditos.app.service;

import com.pedidosrapiditos.app.domain.Usuario;
import com.pedidosrapiditos.app.repository.UsuarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Usuario}.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Save a usuario.
     *
     * @param usuario the entity to save.
     * @return the persisted entity.
     */
    public Usuario save(Usuario usuario) {
        log.debug("Request to save Usuario : {}", usuario);
        return usuarioRepository.save(usuario);
    }

    /**
     * Partially update a usuario.
     *
     * @param usuario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Usuario> partialUpdate(Usuario usuario) {
        log.debug("Request to partially update Usuario : {}", usuario);

        return usuarioRepository
            .findById(usuario.getId())
            .map(
                existingUsuario -> {
                    if (usuario.getUsername() != null) {
                        existingUsuario.setUsername(usuario.getUsername());
                    }
                    if (usuario.getPassword() != null) {
                        existingUsuario.setPassword(usuario.getPassword());
                    }
                    if (usuario.getRol() != null) {
                        existingUsuario.setRol(usuario.getRol());
                    }
                    if (usuario.getTelefono() != null) {
                        existingUsuario.setTelefono(usuario.getTelefono());
                    }
                    if (usuario.getDireccion1() != null) {
                        existingUsuario.setDireccion1(usuario.getDireccion1());
                    }
                    if (usuario.getCiudad() != null) {
                        existingUsuario.setCiudad(usuario.getCiudad());
                    }
                    if (usuario.getPais() != null) {
                        existingUsuario.setPais(usuario.getPais());
                    }
                    if (usuario.getLatitud() != null) {
                        existingUsuario.setLatitud(usuario.getLatitud());
                    }
                    if (usuario.getLongitud() != null) {
                        existingUsuario.setLongitud(usuario.getLongitud());
                    }

                    return existingUsuario;
                }
            )
            .map(usuarioRepository::save);
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable);
    }

    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
