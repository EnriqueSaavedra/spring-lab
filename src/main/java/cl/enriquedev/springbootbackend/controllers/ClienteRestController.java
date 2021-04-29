package cl.enriquedev.springbootbackend.controllers;

import cl.enriquedev.springbootbackend.models.entity.Cliente;
import cl.enriquedev.springbootbackend.models.entity.Region;
import cl.enriquedev.springbootbackend.models.services.IClienteService;
import cl.enriquedev.springbootbackend.models.services.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page,4);
        return clienteService.findAll(pageable);
    }

    @GetMapping("/clientes/{id}")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Cliente cliente = null;
        try{
            cliente = clienteService.findById(id);
        }catch (DataAccessException ex){
            response.put("mensaje","Error al realizar consulta en la base de datos!.");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){
            response.put("mensaje","El cliente ID ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(cliente,HttpStatus.OK);
    }

    @PostMapping("/clientes")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Map<String,Object> response = new HashMap<>();
        Cliente clienteCreado = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map((error) -> "El campo '"+error.getField()+"': "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            /*for(FieldError error : result.getFieldErrors()){
                errors.add("El campo '"+error.getField()+"': "+error.getDefaultMessage());
            }*/
            response.put("errors",errors);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            clienteCreado = clienteService.save(cliente);
        }catch (DataIntegrityViolationException ex){
            response.put("mensaje","Cliente duplicado");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (DataAccessException ex){
            response.put("mensaje","Error al realizar consulta en la base de datos!.");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con exito");
        response.put("cliente",clienteCreado);
        return new ResponseEntity(response,HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result , @PathVariable Long id){
        Cliente clienteActual = null;
        Map<String,Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map((error) -> "El campo '"+error.getField()+"': "+error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }

        try{
            clienteActual = clienteService.findById(id);
            if(clienteActual == null){
                response.put("mensaje","No se encuentra el cliente ID: "+cliente.getId().toString()+" en la base de datos");
                return new ResponseEntity(response,HttpStatus.NOT_FOUND);
            }
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteActual.setRegion(cliente.getRegion());
            clienteActual = clienteService.save(clienteActual);
            response.put("mensaje","Cliente actualizado con exito!.");
            response.put("cliente",clienteActual);
            return new ResponseEntity(response,HttpStatus.CREATED);
        }catch (DataIntegrityViolationException ex){
            response.put("mensaje","Problemas al actualizar cliente!");
            return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (DataAccessException ex){
            response.put("mensaje","Problemas al obtener cliente de la base de datos");
            return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/clientes/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try{
            Cliente cliente = clienteService.findById(id);
            String nombreFotoAnterior = cliente.getFoto();
            uploadFileService.eliminar(nombreFotoAnterior);
            clienteService.delete(id);
            response.put("mensaje","El cliente fue eliminado correctamente.");
            return new ResponseEntity(response,HttpStatus.OK);
        }catch (DataAccessException ex){
            response.put("mensaje","No se encontró el cliente para elminar");
            return new ResponseEntity(response,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clientes/upload")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
        Map<String,Object> response = new HashMap<>();
        Cliente cliente = clienteService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = null;
            try {
                nombreArchivo = uploadFileService.copiar(archivo);
            } catch (IOException e) {
                log.error("Error al subir archivo: "+e.getMessage());
                response.put("mensaje","Error al subir archivo");
                return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnterior = cliente.getFoto();
            uploadFileService.eliminar(nombreFotoAnterior);
            cliente.setFoto(nombreArchivo);
            clienteService.save(cliente);
            response.put("cliente",cliente);
            response.put("mensaje","Ha subido correctamente el archivo");
        }
        return new ResponseEntity(response,HttpStatus.CREATED);
    }

    @GetMapping("/clientes/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Resource recurso = null;
        try {
            recurso = uploadFileService.cargar(nombreFoto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(!recurso.exists() && !recurso.isReadable()){
            throw new RuntimeException("Error al cargar la imagen");
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"");
        return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
    }

    @GetMapping("/clientes/regiones")
    @Secured("ROLE_ADMIN")
    public List<Region> listarRegiones(){
        return clienteService.findAllRegiones();
    }


}
