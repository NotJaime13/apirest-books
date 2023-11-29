package com.company.books.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {

	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);
	
	@Autowired
	private ILibroDao libroDao; 
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> consultaLibro() {
		
		log.info("inicio metodo buscarLibros()");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			
			response.getLibroResponse().setLibro(libro);
			
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		}catch(Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Respuesta no exitosa");
			log.error("error al consultar libros: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500 si esta mal
		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200 si esta OK
	}

	@Override
	public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
		log.info("inicio metodo buscarPorId()");
		
		LibroResponseRest response = new LibroResponseRest();
		
		List<Libro> list = new ArrayList<>();
		
		try {
		
			Optional<Libro> libro = libroDao.findById(id);
			if(libro.isPresent()) {
				list.add(libro.get());
				response.getLibroResponse().setLibro(list);
			}else {
				log.error("Error en consultar el libro");
				response.setMetadata("Respuesta nok", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response,HttpStatus.NOT_FOUND); // 404
			}
		}catch(Exception e) {
			log.error("Error en consultar el libro");
			response.setMetadata("Respuesta nok", "-1", "Error al consultar el libro");
			return new ResponseEntity<LibroResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
		
		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<LibroResponseRest> crear(Libro request) {
		log.info("Inicio metodo crear libro");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			
			Libro libroInsertado = libroDao.save(request);
			
			if(libroInsertado!= null) {
				list.add(libroInsertado);
				response.getLibroResponse().setLibro(list);
			}else {
				log.error("Error en insertar el libro");
				response.setMetadata("Respuesta nok", "-1", "Libro no insertado");
				return new ResponseEntity<LibroResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
			
			
		}catch(Exception e) {
			log.error("Error en insertar el libro");
			response.setMetadata("Respuesta nok", "-1", "Error al insertar el libro");
			return new ResponseEntity<LibroResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
		
		response.setMetadata("Respuesta ok", "00", "Libro creado");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}
	
}
