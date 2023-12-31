package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import com.company.books.backend.services.ILibroService;

@RestController
@RequestMapping("/v1")
public class LibroRestController {

	@Autowired
	private ILibroService service;
	
	@GetMapping("/libros")
	public ResponseEntity<LibroResponseRest> consultaLibro() {
		
		ResponseEntity<LibroResponseRest> response = service.consultaLibro();
		
		return response;
	}
	
	@GetMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> consultaPorId(@PathVariable Long id){
		ResponseEntity<LibroResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	@PostMapping("/libros")
	public ResponseEntity<LibroResponseRest> crear(@RequestBody Libro request){
		ResponseEntity<LibroResponseRest> response = service.crear(request);
		return response;
	}
	
}
