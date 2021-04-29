package cl.enriquedev.springbootbackend.models.services.impl;

import cl.enriquedev.springbootbackend.models.services.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    final static String UPLOAD_PATH = "src/main/resources/static/images";
    final static String DEFAULT_IMAGE = "no-user.png";

    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);
        Resource recurso = new UrlResource(rutaArchivo.toUri());
        if(!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = getPath(DEFAULT_IMAGE);
            recurso = new UrlResource(rutaArchivo.toUri());
        }
        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace(" ","");
        Path rutaArchivo = getPath(nombreArchivo);
        Files.copy(archivo.getInputStream(),rutaArchivo);
        log.info(rutaArchivo.toString());
        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreFoto) {
        if(nombreFoto != null && nombreFoto.length() > 0){
            Path rutaFotoAnterior = getPath(nombreFoto);
            File archivoAnterior = rutaFotoAnterior.toFile();
            if(archivoAnterior.exists() && archivoAnterior.canRead()){
                archivoAnterior.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return  Paths.get(UPLOAD_PATH).resolve(nombreFoto).toAbsolutePath();
    }
}
