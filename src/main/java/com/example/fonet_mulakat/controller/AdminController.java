package com.example.fonet_mulakat.controller;

import com.example.fonet_mulakat.model.Emlak;
import com.example.fonet_mulakat.repo.EmlakRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EmlakRepo emlakRepo;
    @CrossOrigin
    @GetMapping(value = "/test")
    public String testS(){return "test";}

    @CrossOrigin
    @PostMapping(value="/filtre")
    public List<Emlak> getKiralik(@RequestBody Emlak filtre){
        String isinma = filtre.getIsinma();
        String kiralik = filtre.getKiralik();
        String tur = filtre.getTur();
        return emlakRepo.filtreleEmlak(isinma,kiralik,tur);
    }
    @CrossOrigin
    @PostMapping(value = "/kaydet")
    public ResponseEntity<?> emlakOlustur(@RequestParam("mkare") String mkare,
                                          @RequestParam("osayi") String osayi,
                                          @RequestParam("bkati") String bkati,
                                          @RequestParam("bbkat") String bbkat,
                                          @RequestParam("isinma") String isinma,
                                          @RequestParam("ucret") String ucret,
                                          @RequestParam("tur") String tur,
                                          @RequestParam("kiralik") String kiralik,
                                          @RequestParam("foto") MultipartFile foto,
                                          @RequestParam("tam_adres") String tam_adres,
                                          @RequestParam("sehir") String sehir,
                                          @RequestParam("sokak") String sokak,
                                          @RequestParam("zip") String zip) {
        try {
            String contentType = foto.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/jpg")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Desteklenmeyen dosya türü.");
            }

            // Yüklenen dosyayı kaydetmek için gereken kodu yazın
            String fileName = StringUtils.cleanPath(foto.getOriginalFilename());
            Path path = Paths.get("C:\\safesite_documents\\" + fileName);
            Files.copy(foto.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Emlak emlak = new Emlak(mkare, osayi, bkati, bbkat, isinma, ucret, tur, kiralik, "C:\\\safesite_documents\\" +fileName, tam_adres, sehir, sokak, zip);
            emlakRepo.save(emlak);
            return ResponseEntity.ok().body("Başarılı");
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
